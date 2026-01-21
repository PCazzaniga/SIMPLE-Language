/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     validateListener.java is part of SIMPLE.
 *
 *     SIMPLE is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     SIMPLE is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with SIMPLE.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class validateListener extends simpleBaseListener{

	private final Parser recognizer;

	private boolean validationOk = false;

	private final List<Error> errors = new ArrayList<>();
	
	private final Map<String, Information> localVars = new HashMap<>();
	
	private final Map<String, Information> pendingVars = new HashMap<>();
	
	private final Deque<Map<String, Information>> allVars = new ArrayDeque<>();

	private final Map<String, FuncInfo> functions = new HashMap<>();
	
	private final Map<String, Information> types = new HashMap<>();

	private final typeVisitor typeChecker = new typeVisitor(localVars, allVars, functions, types);
	
	private int activeLoops = 0;

	private String currentFun = null;
	
	private final Map<Integer, typeVisitor.dataType> runtimeInputs = new HashMap<>();

	private record Error (Token offender, String text){}
	
	public static class Information{
		private final typeVisitor.dataType type;
		private final Token reference;
		private boolean notUsed = true;
		private boolean notInit = true;

		public Information(typeVisitor.dataType type, Token reference){
			this.type = type;
			this.reference = reference;
		}

		public int getLine() {
			return reference.getLine();
		}

		public typeVisitor.dataType getType() {
			return type;
		}

		public boolean isNotInit() {
			return notInit;
		}

		public boolean isNotUsed(){
			return notUsed;
		}

		public void switchToInit(){
			notInit = false;
		}
		public void switchToUsed(){
			notUsed = false;
		}

		@Override
		public String toString() {
			return "{" + type + ", " + reference.getLine() + ", " + notUsed + '}';
		}
	}

	public static class FuncInfo{
		private final typeVisitor.dataType returnType;
		private final List<typeVisitor.dataType> paramTypes;
		private final Token reference;
		private boolean unused = true;

		public FuncInfo(typeVisitor.dataType returnType, List<typeVisitor.dataType> paramTypes, Token reference){
			this.returnType = returnType;
			this.paramTypes = paramTypes;
			this.reference = reference;
		}

		public int getLine() {
			return reference.getLine();
		}

		public List<typeVisitor.dataType> getParamTypes() {
			return paramTypes;
		}

		public typeVisitor.dataType getReturnType(){
			return returnType;
		}

		public boolean getUnused(){
			return unused;
		}

		public void switchToUsed(){
			unused = false;
		}

		@Override
		public String toString() {
			return "{" + returnType + ", " + paramTypes + ", " + reference.getLine() + ", " + unused + '}';
		}
	}

	public validateListener(Parser recognizer) {
		this.recognizer = recognizer;
	}

	@Override
	public void enterAssignment(simpleParser.AssignmentContext ctx) {
		simpleParser.ValueContext srcVal = ctx.value();
		if (ctx.OUTP() == null) {
			simpleParser.VariableContext target = ctx.variable();
			typeVisitor.dataType targetType = target.var_name() != null ?
												typeChecker.visitVar_name(target.var_name()) :
												typeChecker.visitStruct_access(target.struct_access());
			if (targetType != null && !targetType.isUnknown()) {
				if (ctx.INP() != null) {
					if (targetType.isSequence() || targetType.isList() || targetType.isKit()){
						errors.add(new Error(ctx.INP().getSymbol(), errorMsg.assignInputToStruct(targetType.toString())));
					}
					else runtimeInputs.put(ctx.start.getLine(), targetType);
				} else {
					typeVisitor.dataType srcType;
					if (ctx.reserved() != null) srcType = new typeVisitor.numberType();
					else {
						srcType = typeChecker.visitValue(srcVal);
						if (srcVal.direct_value() != null){
							simpleParser.VariableContext srcVar = srcVal.direct_value().variable();
							if (srcVar != null){
								varHasValueCheck(srcVar);
								if (srcVar.getText().equals(target.getText())){
									addLongError(srcVar, errorMsg.assignUseless(srcVar.getText(), "itself"));
								}
							}

						}
					}
					if (srcType.isUnknown()) addLongError(srcVal, errorMsg.assignTypeUnk(srcVal.getText()));
					else if (!targetType.equals(srcType)) {
						addLongError(srcVal, errorMsg.assignTypeWrong(targetType.toString(), srcType.toString()));
					}
				}
				if (target.var_name() != null) initiateVar(target);
			}
		} else {
			if (ctx.INP() != null){
				errors.add(new Error(ctx.INP().getSymbol(), errorMsg.assignUseless(ctx.INP().getText(), ctx.OUTP().getText())));
			} else if (srcVal != null && srcVal.direct_value() != null){
				if (srcVal.direct_value().variable() != null) varHasValueCheck(srcVal.direct_value().variable());
			}
		}
	}

	@Override
	public void enterCondLoop(simpleParser.CondLoopContext ctx) {
		condTypeCheck("loop", ctx.condition());
	}

	@Override
	public void enterDeclared_type(simpleParser.Declared_typeContext ctx) {
		String typeStr = ctx.TYPENAME().getSymbol().getText();
		if (!types.containsKey(typeStr)){
			List<String> options = typeStr.length() > 1 && !types.isEmpty() ?
									findSimilar(typeStr, new ArrayList<>(types.keySet())) :
									new ArrayList<>();
			errors.add(new Error(ctx.TYPENAME().getSymbol(), errorMsg.typeUndefined(typeStr, options)));
		}
		else if (types.get(typeStr).isNotUsed()) types.get(typeStr).switchToUsed();
	}
	
	@Override
	public void enterElif_cond(simpleParser.Elif_condContext ctx) {
		condTypeCheck("branch", ctx.condition());
	}
	
	@Override
	public void enterExpr(simpleParser.ExprContext ctx) {
		if (ctx.fun_call() != null){
			if (functions.containsKey(ctx.fun_call().fun_name().getText())){
				typeVisitor.dataType funType = functions.get(ctx.fun_call().fun_name().getText()).getReturnType();
				if (!funType.isNothing()) addLongError(ctx, errorMsg.funResNotVoid(funType.toString()));
			}
		}
	}
	
	@Override
	public void enterFile(simpleParser.FileContext ctx) {
		collectDeclarations(ctx);
	}

	@Override
	public void enterFun_body(simpleParser.Fun_bodyContext ctx) {
		enterScope_block(null);
	}

	@Override
	public void enterFun_call(simpleParser.Fun_callContext ctx) {
		String nameStr = ctx.fun_name().NAME().getSymbol().getText();
		if (!functions.containsKey(nameStr)){
			List<String> options = !functions.isEmpty() ?
									findSimilar(nameStr, new ArrayList<>(functions.keySet())) :
									new ArrayList<>();
			errors.add(new Error(ctx.fun_name().NAME().getSymbol(), errorMsg.funUndefined(nameStr, options)));
		} else {
			FuncInfo func = functions.get(nameStr);
			if(func.getUnused()) func.switchToUsed();
			boolean allOk = true;
			List<typeVisitor.dataType> args = new ArrayList<>();
			for (simpleParser.Direct_valueContext arg : ctx.direct_value()) {
				simpleParser.VariableContext argVar = arg.variable();
				if (argVar != null) varHasValueCheck(argVar);
				typeVisitor.dataType argType = typeChecker.visitDirect_value(arg);
				if (argType.isUnknown()){
					addLongError(arg, errorMsg.funArgTypeUnk(argType.toString(), arg.getText()));
					allOk = false;
				} else args.add(argType);
			}
			if (allOk) {
				List<typeVisitor.dataType> params = functions.get(nameStr).getParamTypes();
				if (params.size() != args.size()){
					addLongError(ctx, errorMsg.funArgsNumWrong(nameStr, args.size(), params.size()));
				} else {
					for (int i = 0; i < args.size(); i++){
						if(!args.get(i).equals(params.get(i))){
							List<String> paramStr = new ArrayList<>();
							for (typeVisitor.dataType p : params) paramStr.add(p.toString());
							List<String> argStr = new ArrayList<>();
							for (typeVisitor.dataType a : args) argStr.add(a.toString());
							addLongError(ctx, errorMsg.funArgsTypeWrong(nameStr, argStr, paramStr));
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public void enterFun_def(simpleParser.Fun_defContext ctx) {
		currentFun = ctx.name.getText();
		for (simpleParser.ParamContext p : ctx.param()) {
			Token name = p.NAME().getSymbol();
			String nameStr = name.getText();
			boolean alreadyExists = localVars.containsKey(nameStr);
			int alreadyLine = 0;
			if (alreadyExists) alreadyLine = localVars.get(nameStr).getLine();
			else {
				for (Map<String, Information> higherLocals : allVars) {
					if (higherLocals.containsKey(nameStr)) {
						alreadyLine = higherLocals.get(nameStr).getLine();
						alreadyExists = true;
						break;
					}
				}
			}
			if(alreadyExists) errors.add(new Error(name, errorMsg.funParAlreadyVar(nameStr, alreadyLine)));
			else if(pendingVars.containsKey(nameStr)) errors.add(new Error(name, errorMsg.funParAlreadyExist(currentFun, nameStr)));
			else {
				typeVisitor.dataType type = typeChecker.visitType(p.type());
				if (!type.isUnknown()) {
					Information varInfo = new Information(type, name);
					varInfo.switchToInit();
					pendingVars.put(nameStr, varInfo);
				}
			}
		}
	}

	@Override
	public void enterIf_cond(simpleParser.If_condContext ctx) {
		condTypeCheck("branch", ctx.condition());
	}

	@Override
	public void enterList(simpleParser.ListContext ctx) {
		if (!ctx.literal().isEmpty()) structLitTypesCheck(ctx.literal(), "list");
	}

	@Override
	public void enterList2Text(simpleParser.List2TextContext ctx) {
		varHasValueCheck(ctx.variable(0));
		varListTextCheck(ctx.variable(0), errorMsg::mergeNotFromTextList);
		simpleParser.VariableContext to = ctx.variable(1);
		varTextTypeCheck(to, errorMsg::mergeNotToText);
		if (to.struct_access() != null) structHasValueCheck(to.struct_access().var_name().NAME().getSymbol());
		initiateVar(to);
	}

	@Override
	public void enterListInsertion(simpleParser.ListInsertionContext ctx) {
		typeVisitor.dataType structType = typeChecker.visitVar_name(ctx.struct_access().var_name());
		if(!(structType.isUnknown())){
			if(!structType.isList()){
				errors.add(new Error(ctx.struct_access().var_name().start, errorMsg.listOpNotList(structType)));
			} else {
				typeVisitor.dataType gotType = typeChecker.visitDirect_value(ctx.direct_value());
				if(!gotType.isUnknown()){
					typeVisitor.dataType wantType = ((typeVisitor.listType) structType).getInnerType();
					if(!(wantType.equals(gotType))){
						addLongError(ctx.direct_value(), errorMsg.listInsertTypeWrong(gotType, wantType));
					}
				}
			}
		}
	}
	
	@Override
	public void enterListRemoval(simpleParser.ListRemovalContext ctx) {
		typeVisitor.dataType structType = typeChecker.visitVar_name(ctx.struct_access().var_name());
		if (!structType.isUnknown() && !structType.isList()) {
			errors.add(new Error(ctx.struct_access().var_name().start, errorMsg.listOpNotList(structType)));
		}
	}

	@Override
	public void enterLoop(simpleParser.LoopContext ctx) {
		activeLoops++;
	}

	@Override
	public void enterQuantLoop(simpleParser.QuantLoopContext ctx) {
		simpleParser.Direct_valueContext quantVal = ctx.direct_value();
		typeVisitor.dataType quantType = typeChecker.visitDirect_value(quantVal);
		if(quantVal.variable() != null) varHasValueCheck(quantVal.variable());
		if (quantType.isUnknown()) addLongError(quantVal, errorMsg.loopQuantTypeUnk(quantVal.getText()));
		else if (!quantType.isNumber()) addLongError(quantVal, errorMsg.loopQuantTypeWrong(quantType.toString()));
	}

	@Override
	public void enterReserved(simpleParser.ReservedContext ctx) {
		if (ctx.COUNTER() != null && activeLoops < 1) errors.add(new Error(ctx.COUNTER().getSymbol(), errorMsg.invalidCounterUse()));
		if (ctx.SIZEOF() != null){
			if (varStructTypeCheck(ctx.var_name(), errorMsg::invalidSizeOf)){
				Token varName = ctx.var_name().NAME().getSymbol();
				Information initInfo = getVariable(varName.getText());
				if (initInfo != null && initInfo.isNotInit()) errors.add(new Error(varName, errorMsg.invalidSizeOfEmpty()));
			}
		}
	}
	
	@Override
	public void enterReturn(simpleParser.ReturnContext ctx) {
		simpleParser.ValueContext retVal = ctx.value();
		if (retVal.direct_value() != null && retVal.direct_value().variable() != null) {
			varHasValueCheck(retVal.direct_value().variable());
		}
		if(functions.containsKey(currentFun)) {
			typeVisitor.dataType wantType = functions.get(currentFun).getReturnType();
			typeVisitor.dataType gotType = typeChecker.visitValue(retVal);
			if (gotType.isUnknown()) {
				addLongError(retVal, errorMsg.retTypeUnk(retVal.getText()));
			} else if (!wantType.equals(gotType)) {
				addLongError(retVal, errorMsg.retTypeWrong(gotType.toString(), currentFun, wantType.toString()));
			}
		}
	}

	@Override
	public void enterScope_block(simpleParser.Scope_blockContext ctx) {
		allVars.push(new HashMap<>(localVars));
		localVars.clear();
		if (!pendingVars.isEmpty()) {
			localVars.putAll(pendingVars);
			pendingVars.clear();
		}
	}

	@Override
	public void enterSequence(simpleParser.SequenceContext ctx) {
		structLitTypesCheck(ctx.literal(), "sequence");
	}

	@Override
	public void enterStruct_access(simpleParser.Struct_accessContext ctx) {
		if (varStructTypeCheck(ctx.var_name(), errorMsg::accNotStruct)) {
			structHasValueCheck(ctx.var_name().NAME().getSymbol());
			typeVisitor.dataType strType = typeChecker.visitVar_name(ctx.var_name());
			simpleParser.AccessContext acc = ctx.access();
			if (acc.var_name() != null) {
				typeVisitor.dataType varType = typeChecker.visitVar_name(acc.var_name());
				Token name = acc.var_name().NAME().getSymbol();
				if (!varType.isNumber()) errors.add(new Error(name, errorMsg.accNotNum(varType.toString())));
				else if (strType.isKit()) errors.add(new Error(name, errorMsg.accKitDynamic()));
				else {
					Information initInfo = getVariable(name.getText());
					if (initInfo != null && initInfo.isNotInit()) errors.add(new Error(name, errorMsg.varNoValue(name.getText())));
				}
			} else if (acc.reserved() != null){
				if(strType.isKit()) addLongError(acc.reserved(), errorMsg.accKitDynamic());
				else if (acc.reserved().RANDOM() != null) errors.add(new Error(acc.reserved().RANDOM().getSymbol(), errorMsg.accRandom()));
			} else if (acc.NUM() != null) {
				int position = Integer.parseInt(acc.NUM().getText());
				if (position < 1) errors.add(new Error(acc.NUM().getSymbol(), errorMsg.accUnderSize(position)));
				if (!strType.isList()){
					int size = strType.isSequence() ?
								Integer.parseInt(((typeVisitor.seqType) strType).getSize()):
								((typeVisitor.kitType) strType).getFieldTypes().size();
					if (position > size) errors.add(new Error(acc.NUM().getSymbol(), errorMsg.accOverSize(position, size)));
				}
			} else if (acc.NAME() != null) {
				Token fieldName = acc.NAME().getSymbol();
				String fieldStr = fieldName.getText();
				if (strType.isKit()) {
					List<String> kitFields = ((typeVisitor.kitType) strType).getFieldNames();
					if (!kitFields.contains(fieldStr)) {
						List<String> options = fieldStr.length() > 1 && !kitFields.isEmpty() ?
												findSimilar(fieldStr, kitFields) :
												new ArrayList<>();
						errors.add(new Error(fieldName, errorMsg.accKitNoField(fieldStr, ctx.var_name().NAME().getText(), options)));
					}
				}
				else if (!strType.isWildcard()) errors.add(new Error(fieldName, errorMsg.accNotKit(strType.toString())));
			}
		}
	}
	
	@Override
	public void enterText2List(simpleParser.Text2ListContext ctx) {
		varHasValueCheck(ctx.variable(0));
		varTextTypeCheck(ctx.variable(0), errorMsg::splitNotFromText);
		simpleParser.VariableContext to = ctx.variable(1);
		varListTextCheck(to, errorMsg::splitNotToTextList);
		if (to.struct_access() != null) structHasValueCheck(to.struct_access().var_name().NAME().getSymbol());
		initiateVar(to);
	}

	@Override
	public void enterVar_decl(simpleParser.Var_declContext ctx) {
		simpleParser.Var_defContext def = ctx.var_def();
		Token name = def.NAME().getSymbol();
		String nameStr = name.getText();
		Information var = getVariable(nameStr);
		if(var != null) errors.add(new Error(name, errorMsg.varAlreadyExist(nameStr, var.getLine())));
		else {
			typeVisitor.dataType type = typeChecker.visitType(def.type());
			if (type.isUnknown()) addLongError(def.type(), errorMsg.varTypeUnk(def.type().getText(), nameStr));
			else {
				Information varInfo = new Information(type, name);
				if (ctx.var_init() != null) {
					simpleParser.Direct_valueContext dv = ctx.var_init().direct_value();
					typeVisitor.dataType initType;
					if (ctx.var_init().reserved() != null) initType = new typeVisitor.numberType();
					else {
						initType = typeChecker.visitDirect_value(dv);
						simpleParser.VariableContext initVar = dv.variable();
						if(initVar != null) varHasValueCheck(initVar);
					}
					if (initType.isUnknown()) addLongError(dv, errorMsg.varInitTypeUnk(dv.getText(), nameStr));
					else if (!type.equals(initType)){
						addLongError(ctx.var_init(), errorMsg.varInitTypeWrong(type.toString(), initType.toString()));
					} else {
						varInfo.switchToInit();
						localVars.put(nameStr, varInfo);
					}
				} else localVars.put(nameStr, varInfo);
			}
		}
	}
	
	@Override
	public void enterVar_name(simpleParser.Var_nameContext ctx) {
		boolean undefined = true;
		String nameStr = ctx.NAME().getSymbol().getText();
		if (localVars.containsKey(nameStr)){
			undefined = false;
			Information info = localVars.get(nameStr);
			if(info.isNotUsed()) info.switchToUsed();
		} else {
			for (Map<String, Information> vars : allVars){
				if(vars.containsKey(nameStr)){
					undefined = false;
					Information info = vars.get(nameStr);
					if(info.isNotUsed()) info.switchToUsed();
					break;
				}
			}
		}
		if (undefined) {
			List<String> suggestions = new ArrayList<>();
			if (nameStr.length() > 1 && !(localVars.isEmpty() && allVars.isEmpty())) {
				List<String> defined = new ArrayList<>(localVars.keySet());
				for (Map<String, Information> vars : allVars) defined.addAll(vars.keySet());
				suggestions = findSimilar(nameStr, defined);
			}
			errors.add(new Error(ctx.NAME().getSymbol(), errorMsg.varUndefined(nameStr, suggestions)));
		}
	}

	@Override
	public void exitArith_op(simpleParser.Arith_opContext ctx) {
		simpleParser.Ar_oprndContext one = ctx.ar_oprnd(0);
		if (one.variable() != null) varHasValueCheck(one.variable());
		operandTypeCheck(one);
		simpleParser.Ar_oprndContext two = ctx.ar_oprnd(1);
		if (two.variable() != null) varHasValueCheck(two.variable());
		operandTypeCheck(two);
	}

	@Override
	public void exitCompare_op(simpleParser.Compare_opContext ctx) {
		if (ctx.EQ() == null){
			simpleParser.Ar_oprndContext one = ctx.ar_oprnd(0);
			simpleParser.Ar_oprndContext two = ctx.ar_oprnd(1);
			if (one.variable() != null) varHasValueCheck(one.variable());
			if (two.variable() != null) varHasValueCheck(two.variable());
			operandTypeCheck(one);
			operandTypeCheck(two);
		} else {
			simpleParser.Comp_oprndContext one = ctx.comp_oprnd(0);
			simpleParser.Comp_oprndContext two = ctx.comp_oprnd(1);
			simpleParser.Direct_valueContext oneVal = one.direct_value();
			if (oneVal != null && oneVal.variable() != null) varHasValueCheck(oneVal.variable());
			simpleParser.Direct_valueContext twoVal = two.direct_value();
			if (twoVal != null && twoVal.variable() != null) varHasValueCheck(twoVal.variable());
			typeVisitor.dataType oneType = typeChecker.visitComp_oprnd(one);
			typeVisitor.dataType twoType = typeChecker.visitComp_oprnd(two);
			if (oneType.isUnknown() && one.arith_op() == null) addLongError(one, errorMsg.operandTypeUnk(one.getText()));
			if (twoType.isUnknown() && two.arith_op() == null) addLongError(two, errorMsg.operandTypeUnk(two.getText()));
			if (!(oneType.isUnknown() || twoType.isUnknown()) && !oneType.equals(twoType)) {
				addLongError(ctx, errorMsg.equalityTypeWrong(oneType.toString(), twoType.toString()));
			}
		}
	}
	
	@Override
	public void exitFile(simpleParser.FileContext ctx) {
		localsUsageCheck();
		types.forEach((name, info)-> {
			if(info.isNotUsed()) errors.add(new Error(info.reference, errorMsg.typeUnused(name, info.getLine())));
		});
		String mainFunc = simpleLexer.VOCABULARY.getLiteralName(simpleParser.MAIN).replace("'", "");
		functions.forEach((name, info) -> {
			if(info.getUnused() && !name.equals(mainFunc)) {
				errors.add(new Error(info.reference, errorMsg.funUnused(name, info.getLine())));
			}
		});
		if (errors.isEmpty()) validationOk = true;
		else {
			errors.sort(Comparator.<Error>comparingInt(e -> e.offender().getLine())
					.thenComparingInt(e -> e.offender().getCharPositionInLine()));
			for (Error err : errors) recognizer.notifyErrorListeners(err.offender(), err.text(), null);
		}
	}
	
	@Override
	public void exitFun_body(simpleParser.Fun_bodyContext ctx) {
		exitScope_block(null);
	}
	
	@Override
	public void exitLogic_op(simpleParser.Logic_opContext ctx) {
		simpleParser.Log_oprndContext one = ctx.log_oprnd(0);
		if (one.variable() != null) varHasValueCheck(one.variable());
		operandTypeCheck(one);
		if (ctx.NOT() == null){
			simpleParser.Log_oprndContext two = ctx.log_oprnd(1);
			if (two.variable() != null) varHasValueCheck(two.variable());
			operandTypeCheck(two);
		}
	}
	
	@Override
	public void exitLoop(simpleParser.LoopContext ctx) {
		activeLoops--;
	}
	
	@Override
	public void exitScope_block(simpleParser.Scope_blockContext ctx) {
		localsUsageCheck();
		localVars.clear();
		localVars.putAll(allVars.pop());
		typeChecker.clearCache();
	}

	private void addLongError(ParserRuleContext ctx, String msg){
		CommonToken ref = new CommonToken(ctx.start);
		ref.setStopIndex(ctx.stop.getStopIndex());
		errors.add(new Error(ref, msg));
	}

	private boolean checkIfContainsType(simpleParser.TypeContext ctx, String type) {
		if (ctx.declared_type() != null) return ctx.declared_type().TYPENAME().getText().equals(type);
		if (ctx.struct_type() != null) {
			simpleParser.Struct_typeContext ref = ctx.struct_type();
			if(ref.seq_type() != null) return checkIfContainsType(ref.seq_type().type(), type);
			if(ref.list_type() != null) return checkIfContainsType(ref.list_type().type(), type);
			return ref.kit_type().param().stream().anyMatch(p -> checkIfContainsType(p.type(), type));
		}
		return false;
	}
	
	private void collectDeclarations(simpleParser.FileContext ctx){
		for (simpleParser.InformationContext d : ctx.information()){
			if (d.fun_decl() != null) collectFunDeclaration(d.fun_decl());
			else if(d.type_decl() != null) collectTypeDeclaration(d.type_decl());
		}
	}
	
	private void collectFunDeclaration(simpleParser.Fun_declContext fd) {
		Token name = fd.fun_def().name;
		String nameStr = name.getText();
		if (functions.containsKey(nameStr)){
			errors.add(new Error(name, errorMsg.funAlreadyExist(nameStr, functions.get(nameStr).getLine())));
		} else {
			simpleParser.TypeContext type = fd.fun_def().type();
			typeVisitor.dataType retType = type != null ? typeChecker.visitType(type) : new typeVisitor.nothingType();
			if (retType.isUnknown() && type != null){
				addLongError(type, errorMsg.funResTypeUnk(type.getText(), nameStr));
			} else {
				boolean allOk = true;
				List<typeVisitor.dataType> params = new ArrayList<>();
				String mainFunc = simpleLexer.VOCABULARY.getLiteralName(simpleParser.MAIN).replace("'", "");
				for (simpleParser.ParamContext p : fd.fun_def().param()) {
					typeVisitor.dataType pType = typeChecker.visitType(p.type());
					if (pType.isUnknown()){
						addLongError(p.type(), errorMsg.funParTypeUnk(p.type().getText(), p.NAME().getText()));
						allOk = false;
					} else if (nameStr.equals(mainFunc) && (pType.isSequence() || pType.isList() || pType.isKit())){
						addLongError(p.type(), errorMsg.mainParStruct(p.NAME().getText(), p.type().getText()));
						allOk = false;
					} else params.add(pType);
				}
				if (allOk) functions.put(nameStr, new FuncInfo(retType, params, name));
			}
		}
	}
	
	private void collectTypeDeclaration(simpleParser.Type_declContext td) {
		Token name = td.TYPENAME().getSymbol();
		String nameStr = name.getText();
		if (types.containsKey(nameStr)){
			errors.add(new Error(name, errorMsg.typeAlreadyExist(nameStr, types.get(nameStr).getLine())));
		} else {
			if (checkIfContainsType(td.type(), nameStr)) {
				if(td.type().struct_type() != null && td.type().struct_type().kit_type() != null){
					types.put(nameStr, new Information(null, null));
					typeVisitor.dataType type = typeChecker.visitType(td.type());
					if (type.isUnknown()) {
						addLongError(td.type(), errorMsg.typeDefUnk(type.toString()));
						types.remove(nameStr);
					} else {
						typeVisitor.recursiveType newType = new typeVisitor.recursiveType(nameStr, (typeVisitor.kitType) type);
						newType.settleRecursion();
						types.put(nameStr, new Information(newType, name));
					}
				}
				else addLongError(td.type(), errorMsg.typeDefRecInvalid(nameStr));
			} else {
				typeVisitor.dataType type = typeChecker.visitType(td.type());
				if (type.isUnknown()) addLongError(td.type(), errorMsg.typeDefUnk(type.toString()));
				else types.put(nameStr, new Information(new typeVisitor.customType(nameStr, type), name));
			}
		}
	}
	
	private void condTypeCheck(String condUse, simpleParser.ConditionContext condition) {
		if (typeChecker.visitCondition(condition).isUnknown()){
			addLongError(condition, errorMsg.condTypeUnk(condUse, condition.getText()));
		}
	}

	private List<String> findSimilar(String match, List<String> options){
		return Levenshtein.filterByMaxDistance(match, options, match.length() / 2);
	}

	public Map<Integer, typeVisitor.dataType> getRuntimeInputs() {return runtimeInputs;}

	private Information getVariable(String name){
		if (localVars.containsKey(name)) return localVars.get(name);
		else {
			for (Map<String, Information> higherLocals : allVars){
				if (higherLocals.containsKey(name)) return higherLocals.get(name);
			}
		}
		return null;
	}

	private void initiateVar(simpleParser.VariableContext var) {
		String name = var.var_name() != null ? var.var_name().getText() : var.struct_access().var_name().getText();
		if (localVars.containsKey(name)){
			Information info = localVars.get(name);
			if(info.isNotInit()) info.switchToInit();
		} else {
			for (Map<String, Information> vars : allVars){
				if(vars.containsKey(name)){
					Information info = vars.get(name);
					if(info.isNotInit()) info.switchToInit();
					break;
				}
			}
		}
	}

	public boolean isValidationOk() {return validationOk;}
	
	private void localsUsageCheck(){
		localVars.forEach((name, info)-> {
			if(info.isNotUsed()) errors.add(new Error(info.reference, errorMsg.varUnused(name)));
		});
	}
	
	private void operandTypeCheck(ParserRuleContext operand) {
		typeVisitor.dataType operandType = null;
		String operationKind = "";
		boolean notOperation = true;
		boolean invalidType = false;
		if (operand instanceof simpleParser.Ar_oprndContext){
			operandType = typeChecker.visitAr_oprnd((simpleParser.Ar_oprndContext) operand);
			operationKind = "arithmetic";
			notOperation = ((simpleParser.Ar_oprndContext) operand).arith_op() == null;
			invalidType = !operandType.isNumber();
		} else if (operand instanceof simpleParser.Log_oprndContext){
			operandType = typeChecker.visitLog_oprnd((simpleParser.Log_oprndContext) operand);
			operationKind = "logical";
			notOperation = (((simpleParser.Log_oprndContext) operand).logic_op() == null &&
								((simpleParser.Log_oprndContext) operand).compare_op() == null);
			invalidType = !operandType.isBoolean();
		} else if (operand instanceof simpleParser.Comp_oprndContext){
			operandType = typeChecker.visitComp_oprnd((simpleParser.Comp_oprndContext) operand);
			operationKind = "comparison";
			notOperation = ((simpleParser.Comp_oprndContext) operand).arith_op() == null;
			invalidType = !operandType.isNumber();
		}
		if (operandType != null){
			if (operandType.isUnknown()) {
				if (notOperation) addLongError(operand, errorMsg.operandTypeUnk(operand.getText()));
			} else if (invalidType) {
				addLongError(operand, errorMsg.operandTypeWrong(operandType.toString(), operationKind));
			}
		}
	}

	private void structHasValueCheck(Token name) {
		Information info = getVariable(name.getText());
		if (info != null && info.isNotInit()) errors.add(new Error(name, errorMsg.structNoValue(name.getText())));
	}

	private void structLitTypesCheck(List<simpleParser.LiteralContext> lvs, String structKind) {
		typeVisitor.dataType foundType = null;
		for(simpleParser.LiteralContext lv : lvs){
			typeVisitor.dataType ddv = typeChecker.visitLiteral(lv);
			if (ddv.isUnknown()) addLongError(lv, errorMsg.litStructTypeUnk(lv.getText(), structKind));
			if (!ddv.isNothing()){
				if(foundType == null) foundType = ddv;
				else if(!ddv.equals(foundType)){
					addLongError(lv, errorMsg.litStructTypeMixed(structKind, foundType.toString(), ddv.toString()));
					break;
				}
			}
		}
	}
	
	private void varHasValueCheck(simpleParser.VariableContext var){
		Token varName = var.var_name() != null ?
						var.var_name().NAME().getSymbol() :
						var.struct_access().var_name().NAME().getSymbol();
		Information info = getVariable(varName.getText());
		if (info != null && info.isNotInit()) errors.add(new Error(varName, errorMsg.varNoValue(varName.getText())));
	}

	public boolean validateProgramArgs(List<typeVisitor.dataType> args){
		String mainFunc = simpleLexer.VOCABULARY.getLiteralName(simpleParser.MAIN).replace("'", "");
		if(!functions.containsKey(mainFunc)) return args.isEmpty();
		List<typeVisitor.dataType> mainPTypes = functions.get(mainFunc).getParamTypes();
		if (args.size() != mainPTypes.size()) return false;
		for(int i = 0; i < args.size(); i++){
			if (!mainPTypes.get(i).equals(args.get(i))) return false;
		}
		return true;
	}

	private void varListTextCheck(simpleParser.VariableContext var, Function<String, String> notTextListMsg) {
		typeVisitor.dataType varType = typeChecker.visitVariable(var);
		if (varType.isUnknown()) errors.add(new Error(var.start, errorMsg.varTypeUnk(varType.toString(), var.getText())));
		else if (!(varType.isList() && ((typeVisitor.listType) varType).getInnerType().isText())){
			addLongError(var, notTextListMsg.apply(varType.toString()));
		}
	}

	private boolean varStructTypeCheck(simpleParser.Var_nameContext varName, Function<String, String> notStructTypeMsg){
		typeVisitor.dataType structType = typeChecker.visitVar_name(varName);
		if (structType.isUnknown()){
			errors.add(new Error(varName.NAME().getSymbol(), errorMsg.structTypeUnk(varName.NAME().getSymbol().getText())));
			return false;
		} else if (!(structType.isSequence() || structType.isList() || structType.isKit() || structType.isWildcard())){
			errors.add(new Error(varName.NAME().getSymbol(), notStructTypeMsg.apply(structType.toString())));
			return false;
		}
		return true;
	}

	private void varTextTypeCheck(simpleParser.VariableContext var, Function<String, String> notTextMsg) {
		typeVisitor.dataType varType = typeChecker.visitVariable(var);
		if (varType.isUnknown()) addLongError(var, errorMsg.varTypeUnk(varType.toString(), var.getText()));
		else if (!varType.isText()) addLongError(var, notTextMsg.apply(varType.toString()));
	}
}