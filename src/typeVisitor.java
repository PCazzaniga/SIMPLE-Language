/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     typeVisitor.java is part of SIMPLE.
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

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class typeVisitor extends simpleBaseVisitor<typeVisitor.dataType>{

	public abstract static class dataType {
		String typeString;

		public boolean equals(dataType dt) {
			return dt.isWildcard() || (dt.getClass() == this.getClass());
		}

		@Override
		public String toString() {
			return typeString;
		}

		public boolean isUnknown(){
			return this instanceof unknownType;
		}

		public boolean isNothing(){
			return this instanceof nothingType;
		}

		public boolean isNumber(){
			return this instanceof numberType;
		}

		public boolean isText(){
			return this instanceof textType;
		}

		public boolean isBoolean(){
			return this instanceof booleanType;
		}

		public boolean isWildcard(){
			return this instanceof wildcardType;
		}

		public boolean isCustom(){ return this instanceof customType; }

		public boolean isSequence(){
			return this instanceof seqType;
		}

		public boolean isList(){
			return this instanceof listType;
		}

		public boolean isKit(){
			return this instanceof kitType;
		}

	}

	public static class unknownType extends dataType{
		public unknownType(){
			typeString = "!Unknown!";
		}
	}
	
	public static class nothingType extends  dataType{
		public nothingType(){
			this.typeString = "Nothing";
		}
	}
	
	public static class numberType extends dataType{
		public numberType() {
			this.typeString = "Number";
		}
	}
	
	public static class textType extends dataType{
		public textType() {
			this.typeString = "Text";
		}
	}
	
	public static class booleanType extends  dataType{
		public booleanType() {
			this.typeString = "Boolean";
		}
	}

	public static class wildcardType extends dataType{
		public wildcardType(String details){
			this.typeString = "!" + details + "!";
		}

		@Override
		public boolean equals(dataType dt) {
			return true;
		}
	}

	public static class customType extends dataType{
		private final dataType actual;

		public customType(String name, dataType actual){
			this.typeString = name;
			this.actual = actual;
		}

		@Override
		public boolean equals(dataType dt) {
			return actual.equals(dt);
		}

		public dataType getActual() {
			return actual;
		}

		@Override
		public boolean isUnknown() {
			return actual.isUnknown();
		}

		@Override
		public boolean isNothing() {
			return actual.isNothing();
		}

		@Override
		public boolean isNumber() {
			return actual.isNumber();
		}

		@Override
		public boolean isText() {
			return actual.isText();
		}

		@Override
		public boolean isBoolean() {
			return actual.isBoolean();
		}

		@Override
		public boolean isWildcard() {
			return actual.isWildcard();
		}

		@Override
		public boolean isSequence() {
			return actual.isSequence();
		}

		@Override
		public boolean isList() {
			return actual.isList();
		}

		@Override
		public boolean isKit() {
			return actual.isKit();
		}

	}

	public static class recursiveType extends dataType{

		private final kitType actual;

		public recursiveType(String name,kitType actual) {
			this.typeString = name;
			this.actual = actual;
		}

		public dataType getActual() {
			return actual;
		}

		public void settleRecursion() {
			actual.replacePlaceHolders(this);
		}

		@Override
		public boolean equals(dataType dt) {
			return assumptions.isEmpty() ? baseEquals(dt) : deepEquals(dt);
		}

		private boolean baseEquals(dataType dt){
			boolean answer = deepEquals(dt);
			assumptions.clear();
			return answer;
		}

		private boolean deepEquals(dataType dt){
			if(dt.isWildcard()) return true;
			if(dt instanceof recursiveType) return isEquivalent(((recursiveType) dt).actual);
			if(dt instanceof kitType) return isEquivalent((kitType) dt);
			return false;
		}

		private final List<dataType> assumptions = new ArrayList<>();

		private boolean isEquivalent(kitType that){
			if(assumptions.contains(that)) return true;
			assumptions.add(that);
			if(actual.fieldTypes.size() != that.fieldTypes.size()) return false;
			for(int i = 0; i < actual.fieldTypes.size(); i++) {
				dataType thisField = actual.fieldTypes.get(i);
				dataType thatField = that.fieldTypes.get(i);
				if ((thisField == this && !this.deepEquals(thatField)) || !thisField.equals(thatField)) return false;
			}
			return true;
		}

	}
	
	public abstract static class structType extends dataType{
		public abstract void replacePlaceHolders(recursiveType actual);
	}

	private static class placeHolder extends dataType{
		public placeHolder() {
			this.typeString = "PlAcEhOlDeR";
		}

		@Override
		public boolean equals(dataType dt) {
			return false;
		}
	}
	
	public static class seqType extends structType{

		private dataType innerType;
		private final String size;

		public seqType(String size, dataType dt){
			this.typeString = "Sequence of ";
			this.size = size;
			innerType = dt;
		}

		@Override
		public boolean equals(dataType dt) {
			if (dt.isWildcard()) return true;
			if (dt.isCustom()) return dt.equals(this);
			if (!dt.isSequence() || !size.equals(((seqType) dt).getSize())) return false;
			dataType thatIt = ((seqType) dt).getInnerType();
			return (thatIt instanceof recursiveType) ? thatIt.equals(innerType) : innerType.equals(thatIt);
		}

		public dataType getInnerType() {
			return innerType;
		}

		public String getSize() {
			return size;
		}

		@Override
		public void replacePlaceHolders(recursiveType actual) {
			if (innerType instanceof placeHolder) innerType = actual;
			else if (innerType instanceof structType) ((structType) innerType).replacePlaceHolders(actual);
		}
		
		@Override
		public String toString() {
			return innerType.isWildcard() ? "(" + innerType + ")" : "(" + typeString + size + " " + innerType + ")";
		}
	}
	
	public static class listType extends structType{

		private dataType innerType;

		public listType(dataType dt){
			this.typeString = "List of ";
			innerType = dt;
		}

		@Override
		public boolean equals(dataType dt) {
			if (dt.isWildcard()) return true;
			if (!dt.isList()) return false;
			dataType thatIt = ((listType) dt).getInnerType();
			return thatIt instanceof recursiveType ? thatIt.equals(innerType) : innerType.equals(thatIt);
		}

		public dataType getInnerType() {
			return innerType;
		}

		@Override
		public void replacePlaceHolders(recursiveType actual) {
			if (innerType instanceof placeHolder) innerType = actual;
			else if (innerType instanceof structType) ((structType) innerType).replacePlaceHolders(actual);
		}
		
		@Override
		public String toString() {
			return innerType.isWildcard() ? "(" + innerType + ")" : "(" + typeString + innerType + ")";
		}
	}

	public static class kitType extends structType{

		private final List<dataType> fieldTypes;
		private final List<String> fieldNames;

		private String preStringedType;

		public kitType(List<dataType> fieldTypes){
			this(fieldTypes, new ArrayList<>());
		}

		public kitType(List<dataType> fieldTypes, List<String> fieldNames){
			typeString = "Kit of ";
			this.fieldTypes = fieldTypes;
			this.fieldNames = fieldNames;
			prepareString(fieldTypes, fieldNames);
		}

		private void prepareString(List<dataType> fTypes, List<String> fNames){
			StringBuilder type = new StringBuilder(typeString);
			for(dataType f : fTypes){
				type.append(f.toString());
				if (fTypes.size() == fNames.size()) type.append(" ").append(fNames.get(fTypes.indexOf(f)));
				type.append(", ");
			}
			preStringedType = "(" + type.substring(0, type.length() - 2) +")";
		}
		
		@Override
		public boolean equals(dataType dt) {
			if (dt.isWildcard()) return true;
			if(!dt.isKit()) return false;
			if (fieldTypes.size() != ((kitType) dt).getFieldTypes().size()) return false;
			for(int i = 0; i < fieldTypes.size(); i++){
				dataType thatIt = ((kitType) dt).getFieldTypes().get(i);
				if (thatIt instanceof recursiveType) {
					if (!thatIt.equals(fieldTypes.get(i))) return false;
				} else if (!fieldTypes.get(i).equals(thatIt)) return false;
			}
			return true;
		}

		public List<String> getFieldNames() {
			return fieldNames;
		}

		public List<dataType> getFieldTypes() {
			return fieldTypes;
		}

		@Override
		public void replacePlaceHolders(recursiveType actual) {
			for(int i = 0; i < fieldTypes.size(); i++){
				if (fieldTypes.get(i) instanceof placeHolder) fieldTypes.set(i, actual);
				else if(fieldTypes.get(i) instanceof structType) ((structType) fieldTypes.get(i)).replacePlaceHolders(actual);
			}
			prepareString(fieldTypes, fieldNames);
		}

		@Override
		public String toString() {
			return preStringedType;
		}
	}
	
	private final Map<String, dataType> cache = new HashMap<>();

	private final Map<String, validateListener.Information> localVariables;
	private final Deque<Map<String, validateListener.Information>> allVariables;
	private final Map<String, validateListener.FuncInfo> declaredFunctions;
	private final Map<String, validateListener.Information> types;

	public typeVisitor(Map<String, validateListener.Information> localVariables,
					   Deque<Map<String, validateListener.Information>> allVariables,
					   Map<String, validateListener.FuncInfo> declaredFunctions,
					   Map<String, validateListener.Information> types) {
		super();
		this.localVariables = localVariables;
		this.allVariables = allVariables;
		this.declaredFunctions = declaredFunctions;
		this.types = types;
	}
	
	public void clearCache(){
		cache.clear();
	}

	@Override
	public dataType visitAr_oprnd(simpleParser.Ar_oprndContext ctx) {
		if (ctx.variable() != null) return visitVariable(ctx.variable());
		if (ctx.number() != null || ctx.reserved() != null) return new numberType();
		return visitArith_op(ctx.arith_op());
	}
	
	@Override
	public dataType visitArith_op(simpleParser.Arith_opContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		dataType op1 = visitAr_oprnd(ctx.ar_oprnd(0));
		if (op1.isNumber()){
			dataType op2 = visitAr_oprnd(ctx.ar_oprnd(1));
			if (op2.isNumber()) {
				dataType type = new numberType();
				cache.put(ctx.getText(), type);
				return type;
			}
		}
		return new unknownType();
	}
	
	@Override
	public dataType visitComp_oprnd(simpleParser.Comp_oprndContext ctx) {
		if (ctx.direct_value() != null) return visitDirect_value(ctx.direct_value());
		if (ctx.reserved() != null)	return new numberType();
		return visitArith_op(ctx.arith_op());
	}
	
	@Override
	public dataType visitCompare_op(simpleParser.Compare_opContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		boolean isEquality = ctx.EQ() != null;
		dataType op1 = isEquality ? visitComp_oprnd(ctx.comp_oprnd(0)) : visitAr_oprnd(ctx.ar_oprnd(0));
		dataType op2 = isEquality ? visitComp_oprnd(ctx.comp_oprnd(1)) : visitAr_oprnd(ctx.ar_oprnd(1));
		if ((isEquality && op1.equals(op2)) || (!isEquality && op1.isNumber() && op2.isNumber())){
				dataType type = new booleanType();
				cache.put(ctx.getText(), type);
				return type;
		}
		return new unknownType();
	}

	@Override
	public dataType visitCondition(simpleParser.ConditionContext ctx) {
		return ctx.logic_op() != null ? visitLogic_op(ctx.logic_op()) : visitCompare_op(ctx.compare_op());
	}
	
	@Override
	public dataType visitDirect_value(simpleParser.Direct_valueContext ctx) {
		if (ctx.literal() != null) return visitLiteral(ctx.literal());
		if (ctx.variable().var_name() != null) return visitVar_name(ctx.variable().var_name());
		return visitStruct_access(ctx.variable().struct_access());
	}

	@Override
	public dataType visitFun_call(simpleParser.Fun_callContext ctx) {
		String name = ctx.fun_name().NAME().getText();
		return declaredFunctions.containsKey(name) ? declaredFunctions.get(name).getReturnType() : new unknownType();
	}
	
	@Override
	public dataType visitLiteral(simpleParser.LiteralContext ctx) {
		if (ctx.Text() != null) return new textType();
		if (ctx.number() != null) return new numberType();
		if (ctx.Boolean() != null) return new booleanType();
		if (ctx.NULL() != null) return new nothingType();
		return visitStruct_lit(ctx.struct_lit());
	}

	@Override
	public dataType visitLog_oprnd(simpleParser.Log_oprndContext ctx) {
		if (ctx.Boolean() != null) return new booleanType();
		if (ctx.variable() != null) return visitVariable(ctx.variable());
		if (ctx.compare_op() != null) return visitCompare_op(ctx.compare_op());
		return visitLogic_op(ctx.logic_op());
	}

	@Override
	public dataType visitLogic_op(simpleParser.Logic_opContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		if (visitLog_oprnd(ctx.log_oprnd(0)).isBoolean() && (ctx.NOT() != null ||
																visitLog_oprnd(ctx.log_oprnd(1)).isBoolean())){
			dataType type = new booleanType();
			cache.put(ctx.getText(), type);
			return type;
		}
		return new unknownType();
	}

	@Override
	public dataType visitOperation(simpleParser.OperationContext ctx) {
		if (ctx.arith_op() != null) return visitArith_op(ctx.arith_op());
		if (ctx.logic_op() != null) return visitLogic_op(ctx.logic_op());
		return visitCompare_op(ctx.compare_op());
	}
	
	@Override
	public dataType visitStruct_access(simpleParser.Struct_accessContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		dataType structType = visitVar_name(ctx.var_name());
		simpleParser.AccessContext access = ctx.access();
		dataType type = null;
		if (structType.isSequence()){
			if (access.NUM() != null) {
				int position = Integer.parseInt(access.NUM().getText());
				int size = Integer.parseInt(((seqType) structType).getSize());
				if (position <= size) type = ((seqType) structType).getInnerType();
			}
			if (access.reserved() != null) type = ((seqType) structType).getInnerType();
			if (access.var_name() != null && visitVar_name(access.var_name()).isNumber()) {
				type = ((seqType) structType).getInnerType();
			}
		}else if (structType.isList()){
			if (access.NUM() != null || access.reserved() != null) type = ((listType) structType).getInnerType();
			if (access.var_name() != null && visitVar_name(access.var_name()).isNumber()) {
				type = ((listType) structType).getInnerType();
			}
		}else if (structType.isKit()){
			if (access.NUM() != null){
				int position = Integer.parseInt(access.NUM().getText());
				List<dataType> fields = ((kitType) structType).getFieldTypes();
				if (position <= fields.size()) type = fields.get(position);
			}
			if (access.NAME() != null){
				List<String> names = ((kitType) structType).getFieldNames();
				if (names.contains(access.NAME().getText())){
					type = ((kitType) structType).getFieldTypes().get(names.indexOf(access.NAME().getText()));
				}
			}
		}
		if (type != null) {
			cache.put(ctx.getText(), type);
			return type;
		}
		return new unknownType();
	}

	@Override
	public dataType visitStruct_lit(simpleParser.Struct_litContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		dataType type;
		if (ctx.sequence() != null){
			dataType foundType = new wildcardType("Sequence of " + ctx.sequence().literal().size() + " Nothing");
			for(simpleParser.LiteralContext lv : ctx.sequence().literal()){
				dataType ddv = visitLiteral(lv);
				if (ddv.isUnknown()) return new unknownType();
				if (!ddv.isNothing()){
					if(foundType.isWildcard()) foundType = ddv;
					else if(!ddv.equals(foundType)) return new unknownType();
				}
			}
			type =  new seqType(String.valueOf(ctx.sequence().literal().size()), foundType);
		} else if (ctx.list() != null){
			if (ctx.list().literal().isEmpty()) return new listType(new wildcardType("Empty list"));
			dataType foundType = new wildcardType("List of Nothing");
			for (simpleParser.LiteralContext lv : ctx.list().literal()){
				dataType ddv = visitLiteral(lv);
				if (ddv.isUnknown()) return new unknownType();
				if (!ddv.isNothing()){
					if(foundType.isWildcard()) foundType = ddv;
					else if(!ddv.equals(foundType)) return new unknownType();
				}
			}
			type = new listType(foundType);
		} else {
			List<dataType> fieldsTypes = new ArrayList<>();
			for(simpleParser.LiteralContext lv : ctx.kit().literal()){
				dataType ft = this.visitLiteral(lv);
				if (ft.isUnknown()) return new unknownType();
				fieldsTypes.add(ft);
			}
			type = new kitType(fieldsTypes);
		}
		cache.put(ctx.getText(), type);
		return type;
	}

	@Override
	public dataType visitStruct_type(simpleParser.Struct_typeContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		dataType type;
		if (ctx.seq_type() != null){
			dataType innerType = this.visitType(ctx.seq_type().type());
			if (innerType.isUnknown()) return new unknownType();
			type = new seqType(ctx.seq_type().NUM().getText(), innerType);
		} else if (ctx.list_type() != null) {
			dataType innerType = this.visitType(ctx.list_type().type());
			if (innerType.isUnknown()) return new unknownType();
			type = new listType(visitType(ctx.list_type().type()));
		} else {
			List<dataType> fieldsTypes = new ArrayList<>();
			List<String> fieldNames = new ArrayList<>();
			for(simpleParser.ParamContext p : ctx.kit_type().param()){
				dataType paramType = this.visitType(p.type());
				if (paramType.isUnknown()) return new unknownType();
				fieldsTypes.add(paramType);
				fieldNames.add(p.NAME().getText());
			}
			type = new kitType(fieldsTypes, fieldNames);
		}
		cache.put(ctx.getText(), type);
		return type;
	}

	@Override
	public dataType visitType(simpleParser.TypeContext ctx) {
		if(ctx.NUMBER() != null) return new numberType();
		if (ctx.STRING() != null) return new textType();
		if (ctx.BOOL() != null) return new booleanType();
		if (ctx.struct_type() != null) return visitStruct_type(ctx.struct_type());
		String typeStr = ctx.declared_type().TYPENAME().getText();
		if (types.containsKey(typeStr)){
			if(types.get(typeStr).getType() instanceof customType){
				return ((customType) types.get(typeStr).getType()).getActual();
			} else if (types.get(typeStr).getType() instanceof recursiveType) {
				return ((recursiveType) types.get(typeStr).getType()).getActual();
			}
			return new placeHolder();
		}
		return new unknownType();
	}

	@Override
	public dataType visitValue(simpleParser.ValueContext ctx) {
		if (ctx.direct_value() != null) return visitDirect_value(ctx.direct_value());
		if (ctx.operation() != null) return visitOperation(ctx.operation());
		return visitFun_call(ctx.fun_call());
	}

	@Override
	public dataType visitVar_name(simpleParser.Var_nameContext ctx) {
		if (cache.containsKey(ctx.getText())) return cache.get(ctx.getText());
		dataType type = null;
		String nameStr = ctx.NAME().getSymbol().getText();
		if (localVariables.containsKey(nameStr)) type = localVariables.get(nameStr).getType();
		else {
			Iterator<Map<String, validateListener.Information>> itr = allVariables.descendingIterator();
			while (itr.hasNext()) {
				Map<String, validateListener.Information> nxt = itr.next();
				if (nxt.containsKey(nameStr)) {
					type = nxt.get(nameStr).getType();
					break;
				}
			}
		}
		if (type != null) {
			cache.put(ctx.getText(), type);
			return type;
		}
		return new unknownType();
	}
	
	@Override
	public dataType visitVariable(simpleParser.VariableContext ctx) {
		return ctx.var_name() != null ? visitVar_name(ctx.var_name()) : visitStruct_access(ctx.struct_access());
	}
}