/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     executeVisitor.java is part of SIMPLE.
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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class executeVisitor extends simpleBaseVisitor<Void>{
	
	private static int MAX_LOOP = 100;
	
	private final Map<String, Integer> callTracker = new HashMap<>();
	
	private static int MAX_RECURSION = 100;
	
	private final Map<String, valueVisitor.Val> localVars = new HashMap<>();
	
	private final Deque<Map<String, valueVisitor.Val>> allVars = new ArrayDeque<>();
	
	private final Map<String, valueVisitor.Val> pendingVars = new HashMap<>();
	
	private final Deque<valueVisitor.Val> valuesStack = new ArrayDeque<>();
	
	private final Map<String, simpleParser.Fun_declContext> functions = new HashMap<>();
	
	private final Map<String, List<String>> kitTypes = new HashMap<>();
	
	private boolean returnedFlag = false;

	private final valueVisitor eval;
	
	private final Map<Integer,typeVisitor.dataType> runtimeInputs = new HashMap<>();

	private simpleRTLitLexer runtimeLexer;
	private simpleRTLitParser runtimeParser;
	
	private executeVisitor(Builder builder) {
		super();
		if (builder.expectedInputs != null) runtimeInputs.putAll(builder.expectedInputs);
		MAX_RECURSION = builder.maxRecursion;
		MAX_LOOP = builder.maxLoop;
		eval = new valueVisitor(localVars, allVars);
		if (builder.programArgs != null){
			List<valueVisitor.Val> argsCopy = new ArrayList<>(builder.programArgs);
			Collections.reverse(argsCopy);
			for (valueVisitor.Val a : argsCopy) valuesStack.push(a);
		}
	}
	
	static class Builder {

		private List<valueVisitor.Val> programArgs = null;
		private Map<Integer, typeVisitor.dataType> expectedInputs = null;
		private int maxLoop = MAX_LOOP;
		private int maxRecursion = MAX_RECURSION;
		
		public void setExpectedInputs(Map<Integer, typeVisitor.dataType> expectedInputs) {
			this.expectedInputs = expectedInputs;
		}
		
		public void setMaxLoop(int maxLoop) {
			this.maxLoop = maxLoop;
		}
		
		public void setMaxRecursion(int maxRecursion) {
			this.maxRecursion = maxRecursion;
		}
		
		public void setProgramArgs(List<valueVisitor.Val> programArgs) {
			this.programArgs = programArgs;
		}
		
		public executeVisitor build(){
			return new executeVisitor(this);
		}
	}
	
	@Override
	public Void visitAssignment(simpleParser.AssignmentContext ctx) {
		simpleParser.ValueContext value = ctx.value();
		valueVisitor.Val val;
		if (value != null){
			if(value.fun_call() != null){
				visitFun_call(value.fun_call());
				val = valuesStack.pop();
			} else if (value.direct_value() != null) val = eval.visitDirect_value(value.direct_value());
			else {
				simpleParser.OperationContext op = value.operation();
				if(op.arith_op() != null) val = eval.visitArith_op(op.arith_op());
				else if (op.logic_op() != null) val = eval.visitLogic_op(op.logic_op());
				else val = eval.visitCompare_op(op.compare_op());
			}
		} else if (ctx.reserved() != null) val = eval.visitReserved(ctx.reserved());
		else val = getInput(ctx.start.getLine());
		if (ctx.OUTP() != null) System.out.print(val);
		else {
			if(val instanceof valueVisitor.structVal) val = ((valueVisitor.structVal) val).makeCopy();
			updateVariable(ctx.variable(), val);
		}
		return null;
	}
	
	@Override
	public Void visitBranch(simpleParser.BranchContext ctx) {
		if (((valueVisitor.booleanVal) eval.visitCondition(ctx.if_cond().condition())).val()){
			enteringScopeUpdate();
			visitScope_block(ctx.if_cond().scope_block());
			exitingScopeUpdate();
		} else {
			if (ctx.elif_cond() != null){
				for(simpleParser.Elif_condContext eif: ctx.elif_cond()){
					if (((valueVisitor.booleanVal) eval.visitCondition(eif.condition())).val()) {
						enteringScopeUpdate();
						visitScope_block(eif.scope_block());
						exitingScopeUpdate();
						return null;
					}
				}
			}
			if (ctx.else_cond() != null){
				enteringScopeUpdate();
				visitScope_block(ctx.else_cond().scope_block());
				exitingScopeUpdate();
			}
		}
		return null;
	}
	
	@Override
	public Void visitFile(simpleParser.FileContext ctx) {
		for (simpleParser.InformationContext i : ctx.information()){
			if (i.fun_decl() != null) functions.put(i.fun_decl().fun_def().name.getText(), i.fun_decl());
			else if(i.type_decl() != null){
				simpleParser.Struct_typeContext structT = i.type_decl().type().struct_type();
				if(structT != null && structT.kit_type() != null){
					List<String> fields = new ArrayList<>();
					for(simpleParser.ParamContext p : structT.kit_type().param()) fields.add(p.NAME().getText());
					kitTypes.put(i.type_decl().TYPENAME().getText(), fields);
				}
			}
		}
		String mainFunc = simpleLexer.VOCABULARY.getLiteralName(simpleParser.MAIN).replace("'", "");
		if(functions.containsKey(mainFunc)){
			for (simpleParser.InformationContext i : ctx.information()){
				if (i.var_decl() != null) createVar(i.var_decl());
			}
			if (!runtimeInputs.isEmpty()){
				runtimeLexer = new simpleRTLitLexer(null);
				runtimeParser = new simpleRTLitParser(null);
			}
			visitFun_decl(functions.get(mainFunc));
			if (!valuesStack.isEmpty() && !(valuesStack.peek() instanceof valueVisitor.nothingVal)){
				System.out.println("Program produced " + valuesStack);
			}
		} else {
			System.out.println(errorRuntimeMsg.noMain());
			System.exit(1);
		}
		return null;
	}
	
	@Override
	public Void visitFun_body(simpleParser.Fun_bodyContext ctx) {
		enteringScopeUpdate();
		for (simpleParser.StatementContext s : ctx.statement()){
			if (s.instruction() != null) visitInstruction(s.instruction());
			else if (s.flow_control().branch() != null) visitBranch(s.flow_control().branch());
			else visitLoop(s.flow_control().loop());
			
			if (returnedFlag) break;
		}
		if (!returnedFlag) visitReturn(ctx.return_());
		returnedFlag = false;
		exitingScopeUpdate();
		return null;
	}

	@Override
	public Void visitFun_call(simpleParser.Fun_callContext ctx) {
		String fun_name = ctx.fun_name().NAME().getText();
		if (callTracker.containsKey(fun_name)){
			int current_call = callTracker.get(fun_name) + 1;
			if (current_call > MAX_RECURSION){
				System.out.println(errPrefix(ctx.start) + errorRuntimeMsg.recursionOverLimit(fun_name, MAX_RECURSION));
				System.exit(1);
			} else callTracker.replace(fun_name, current_call);
		} else callTracker.put(fun_name, 1);
		List<valueVisitor.Val> args = new ArrayList<>(ctx.direct_value().size());
		for (simpleParser.Direct_valueContext val : ctx.direct_value()) args.add(eval.visitDirect_value(val));
		Collections.reverse(args);
		for (valueVisitor.Val a : args){
			valuesStack.push(a instanceof valueVisitor.structVal ? ((valueVisitor.structVal) a).makeCopy() : a);
		}
		visitFun_decl(functions.get(ctx.fun_name().NAME().getText()));
		callTracker.replace(fun_name, callTracker.get(fun_name) - 1);
		return null;
	}
	
	@Override
	public Void visitFun_decl(simpleParser.Fun_declContext ctx) {
		for(simpleParser.ParamContext p : ctx.fun_def().param()) pendingVars.put(p.NAME().getText(), valuesStack.pop());
		visitFun_body(ctx.fun_body());
		return  null;
	}
	
	@Override
	public Void visitInstruction(simpleParser.InstructionContext ctx) {
		if (ctx.var_decl() != null) createVar(ctx.var_decl());
		else {
			simpleParser.ExprContext expr = ctx.expr();
			if (expr.assignment()!= null) visitAssignment(expr.assignment());
			else if (expr.fun_call() != null){
				visitFun_call(expr.fun_call());
				valuesStack.pop();
			} else {
				simpleParser.List_opContext lop = expr.list_op();
				if (lop instanceof simpleParser.ListInsertionContext){
					visitListInsertion((simpleParser.ListInsertionContext) lop);
				} else if (lop instanceof simpleParser.ListRemovalContext){
					visitListRemoval((simpleParser.ListRemovalContext) lop);
				} else if (lop instanceof simpleParser.Text2ListContext){
					visitText2List((simpleParser.Text2ListContext) lop);
				} else if (lop instanceof simpleParser.List2TextContext){
					visitList2Text((simpleParser.List2TextContext) lop);
				}
			}
		}
		return null;
	}

	@Override
	public Void visitList2Text(simpleParser.List2TextContext ctx) {
		simpleParser.VariableContext src = ctx.variable(0);
		valueVisitor.listVal lv;
		if(src.var_name() != null) lv = (valueVisitor.listVal) eval.visitVar_name(src.var_name());
		else lv = (valueVisitor.listVal) eval.visitStruct_access(src.struct_access());
		String txt = String.join("", lv.vals().stream().map(valueVisitor.Val::toString).toList());
		updateVariable(ctx.variable(1), new valueVisitor.textVal(txt));
		return null;
	}

	@Override
	public Void visitListInsertion(simpleParser.ListInsertionContext ctx) {
		List<valueVisitor.Val> list = ((valueVisitor.listVal) eval.visitVar_name(ctx.struct_access().var_name())).vals();
		int position = getPosition(ctx.struct_access(), list.size() + 1);
		list.add(position - 1, eval.visitDirect_value(ctx.direct_value()));
		return null;
	}

	@Override
	public Void visitListRemoval(simpleParser.ListRemovalContext ctx) {
		List<valueVisitor.Val> list = ((valueVisitor.listVal) eval.visitVar_name(ctx.struct_access().var_name())).vals();
		list.remove(getPosition(ctx.struct_access(), list.size()) - 1);
		return null;
	}

	@Override
	public Void visitLoop(simpleParser.LoopContext ctx) {
		enteringScopeUpdate();
		String counter = simpleLexer.VOCABULARY.getLiteralName(simpleParser.COUNTER).replace("'", "");
		localVars.put(counter, new valueVisitor.numberVal(1));
		simpleParser.Loop_defContext ld = ctx.loop_def();
		if (ld instanceof simpleParser.CondLoopContext){
			runCondLoop(((simpleParser.CondLoopContext) ld).condition(), ctx.scope_block());
		} else runQuantLoop(((simpleParser.QuantLoopContext) ld).direct_value(), ctx.scope_block());
		exitingScopeUpdate();
		return null;
	}

	@Override
	public Void visitReturn(simpleParser.ReturnContext ctx) {
		simpleParser.ValueContext val = ctx.value();
		if(val.fun_call() != null) visitFun_call(val.fun_call());
		else if(val.direct_value() != null) valuesStack.push(eval.visitDirect_value(val.direct_value()));
		else {
			simpleParser.OperationContext op = val.operation();
			if(op.arith_op() != null) valuesStack.push(eval.visitArith_op(op.arith_op()));
			else if (op.logic_op() != null) valuesStack.push(eval.visitLogic_op(op.logic_op()));
			else valuesStack.push(eval.visitCompare_op(op.compare_op()));
		}
		returnedFlag = true;
		return null;
	}
	
	@Override
	public Void visitScope_block(simpleParser.Scope_blockContext ctx) {
		for (simpleParser.StatementContext s : ctx.statement()){
			if (s.instruction() != null) visitInstruction(s.instruction());
			else if (s.flow_control().branch() != null) visitBranch(s.flow_control().branch());
			else visitLoop(s.flow_control().loop());
			
			if (returnedFlag) break;
		}
		if (!returnedFlag){
			if(ctx.closer().instruction() != null) visitInstruction(ctx.closer().instruction());
			else if (ctx.closer().flow_control() != null){
				if (ctx.closer().flow_control().branch() != null) visitBranch(ctx.closer().flow_control().branch());
				else visitLoop(ctx.closer().flow_control().loop());
			} else visitReturn(ctx.closer().return_());
		}
		return null;
	}

	@Override
	public Void visitText2List(simpleParser.Text2ListContext ctx) {
		simpleParser.VariableContext src = ctx.variable(0);
		String txt;
		if (src.var_name() != null) txt = eval.visitVar_name(src.var_name()).toString();
		else txt = eval.visitStruct_access(src.struct_access()).toString();
		List<valueVisitor.Val> lst = txt.chars()
								.mapToObj(c -> new valueVisitor.textVal(String.valueOf((char) c)))
								.collect(Collectors.toCollection(ArrayList::new));
		updateVariable(ctx.variable(1), new valueVisitor.listVal(lst));
		return null;
	}

	private void createVar(simpleParser.Var_declContext ctx) {
		valueVisitor.Val initVal = null;
		simpleParser.Var_initContext initRef = ctx.var_init();
		if (initRef != null){
			if(initRef.direct_value() != null) initVal = eval.visitDirect_value(initRef.direct_value());
			else initVal = eval.visitReserved(initRef.reserved());
		}
		if(initVal instanceof valueVisitor.kitVal){
			List<String> fieldNames;
			simpleParser.TypeContext type = ctx.var_def().type();
			if(type.declared_type() != null) fieldNames = kitTypes.get(type.declared_type().TYPENAME().getText());
			else {
				fieldNames = new ArrayList<>();
				for (simpleParser.ParamContext p : type.struct_type().kit_type().param()) {
					fieldNames.add(p.NAME().getText());
				}
			}
			((valueVisitor.kitVal) initVal).setFields(fieldNames);
		}
		localVars.put(ctx.var_def().NAME().getText(), initVal);
	}

	private void enteringScopeUpdate() {
		allVars.push(new HashMap<>(localVars));
		localVars.clear();
		if(!pendingVars.isEmpty()){
			localVars.putAll(pendingVars);
			pendingVars.clear();
		}
	}

	private void exitingScopeUpdate() {
		localVars.clear();
		localVars.putAll(allVars.pop());
	}

	private valueVisitor.Val getInput(int line) {
		if(!runtimeInputs.containsKey(line)){
			System.out.println("line " + line + " " + (errorRuntimeMsg.unexpectedInputRequest()));
			System.exit(1);
		}
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < MAX_LOOP; i++){
			if (sc.hasNextLine()){
				runtimeLexer.setInputStream(CharStreams.fromString(sc.nextLine()));
				runtimeParser.setInputStream(new CommonTokenStream(runtimeLexer));
				simpleRTLitParser.LiteralContext inputLit = runtimeParser.literal();
				if (runtimeParser.getNumberOfSyntaxErrors() < 1) {
					typeVisitor.dataType gotType = inputHandler.typeOfLiteral(inputLit);
					if (gotType.equals(runtimeInputs.get(line))) return inputHandler.valOfLiteral(inputLit);
					System.out.println(errorRuntimeMsg.inputMismatchedType(gotType, runtimeInputs.get(line)));
				}
				if (i < MAX_LOOP - 1) System.out.println(errorRuntimeMsg.inputAgain());
			} else {
				System.out.println(errorRuntimeMsg.inputInterrupted());
				System.exit(1);
			}
		}
		System.out.println(errorRuntimeMsg.inputTooManyAttempts(MAX_LOOP));
		System.exit(1);
		return new valueVisitor.nothingVal();	
	}
	
	private int getPosition(simpleParser.Struct_accessContext strAcc, int maxPos){
		int result = 0;
		simpleParser.AccessContext acc = strAcc.access();
		if (acc.NUM() != null) result = Integer.parseInt(acc.NUM().getText());
		else if (acc.var_name() != null){
			result = (int) ((valueVisitor.numberVal) eval.visitVar_name(acc.var_name())).val();
			if(result < 1) {
				System.out.println(errPrefix(acc.start) + errorRuntimeMsg.accessUnderSize(result));
				System.exit(1);
			}
		} else if(acc.reserved() != null){
			result = (int) ((valueVisitor.numberVal) eval.visitReserved(acc.reserved())).val();
		}
		if (result > maxPos) {
			System.out.println(errPrefix(acc.start) + errorRuntimeMsg.accessOverSize(result, maxPos));
			System.exit(1);
		}
		return result;
	}

	private String errPrefix(Token posRef){
		return "line " + posRef.getLine() + ":" + posRef.getCharPositionInLine() + " ";
	}
	
	private void runCondLoop(simpleParser.ConditionContext cond, simpleParser.Scope_blockContext block) {
		String counter = simpleLexer.VOCABULARY.getLiteralName(simpleParser.COUNTER).replace("'", "");
		int loopCounter = (int) ((valueVisitor.numberVal) localVars.get(counter)).val();
		while(loopCounter <= MAX_LOOP && ((valueVisitor.booleanVal) eval.visitCondition(cond)).val()){
			visitScope_block(block);
			if (returnedFlag) break;
			loopCounter++;
			localVars.put(counter, new valueVisitor.numberVal(loopCounter));
		}
		if (loopCounter > MAX_LOOP){
			System.out.println("line " + cond.start.getLine() + " " + errorRuntimeMsg.loopOverLimit(MAX_LOOP));
			System.exit(1);
		}
	}
	
	private void runQuantLoop(simpleParser.Direct_valueContext dv, simpleParser.Scope_blockContext block) {
		int limit = (int) ((valueVisitor.numberVal) eval.visitDirect_value(dv)).val();
		if (limit < 0){
			System.out.println("line " + dv.start.getLine() + " " + errorRuntimeMsg.loopNegative(limit));
			System.exit(1);
		}
		String counter = simpleLexer.VOCABULARY.getLiteralName(simpleParser.COUNTER).replace("'", "");
		int loopCounter = (int) ((valueVisitor.numberVal) localVars.get(counter)).val();
		while(loopCounter <= limit){
			visitScope_block(block);
			if (returnedFlag) break;
			loopCounter++;
			localVars.put(counter, new valueVisitor.numberVal(loopCounter));
		}
	}
	
	private void updateVariable(simpleParser.VariableContext var, valueVisitor.Val newVal){
		if(var.var_name() != null) {
			String varName = var.var_name().NAME().getText();
			if (localVars.containsKey(varName)){
				if (localVars.get(varName) instanceof valueVisitor.kitVal){
					((valueVisitor.kitVal) localVars.get(varName)).setAll(((valueVisitor.kitVal)newVal).vals());
				} else localVars.replace(varName, newVal);
			} else {
				for (Map<String, valueVisitor.Val> higher : allVars){
					if (higher.containsKey(varName)){
						if (higher.get(varName) instanceof valueVisitor.kitVal){
							((valueVisitor.kitVal) higher.get(varName)).setAll(((valueVisitor.kitVal) newVal).vals());
						} else higher.replace(varName, newVal);
						break;
					}
				}
			}
		} else {
			simpleParser.Struct_accessContext strAcc = var.struct_access();
			valueVisitor.structVal sv = (valueVisitor.structVal) eval.visitVar_name(strAcc.var_name());
			if(strAcc.access().NAME() != null){
				((valueVisitor.kitVal) sv).setAtField(strAcc.access().NAME().getText(), newVal);
			} else {
				int pos = getPosition(strAcc, (sv instanceof valueVisitor.listVal) ? (sv.size() + 1) : sv.size());
				sv.setAt(pos - 1, newVal);
			}
		}
	}
}