/*
 * Copyright (c) 2025 -2026 PCazzaniga (github.com)
 *
 *     errorMsg.java is part of SIMPLE.
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

import java.util.List;

class errorMsg {

	public static String accKitNoField(String fieldName, String kitName, List<String> suggestions){
		StringBuilder msg = new StringBuilder("Attempting to access non-existent field '" + fieldName + "' in Kit " + kitName + ".");
		if (!suggestions.isEmpty()) {
			msg.append(" Did you mean");
			for (String s : suggestions) {
				msg.append(" '").append(s).append("',");
			}
			msg.replace(msg.length() - 1, msg.length(), " ?");
		}
		return msg.toString();
	}

	public static String accKitDynamic() {
		return "Attempting to access kit structure with a dynamic positional value.";
	}

	public static String accNotKit(String structType) {
		return "Attempting to access non-kit structure of type " + structType + " with a field name.";
	}

	public static String accNotNum(String varType) {
		return "Attempting to use non-number variable of type " + varType + " as positional access to a structure.";
	}

	public static String accNotStruct(String varType) {
		return "Attempting to access non-structured variable of type " + varType + ".";
	}

	public static String accOverSize(int position, int size) {
		return "Attempting to access position " + position + " out of structure's size " + size + ".";
	}

	public static String accRandom() {
		return "Attempting to access structure at purely random position, which could be invalid.";
	}

	public static String accUnderSize(int position) {
		return "Attempting to access structure at invalid position " + position + ".";
	}

	public static String assignInputToStruct(String typeString) {
		return "Attempting to assign from input to variable of non-basic type " + typeString + ".";
	}

	public static String assignTypeWrong(String varType, String valType){
		return "Attempting to assign to variable of type " + varType + " value of mismatched type " + valType + ".";
	}

	public static String assignTypeUnk(String valueString){
		return "Unable to recognize type of assigned value " + valueString + ".";
	}

	public static String assignUseless(String from, String to) {
		return "Attempting useless assignment from " + from + " to " + to + ".";
	}

	public static String condTypeUnk(String condUse, String condString) {
		return "Unable to recognize type of " + condUse + " condition " + condString + ".";
	}

	public static String equalityTypeWrong(String firstType, String secondType) {
		return "Attempting to compare value of type " + firstType + " with value of mismatched type " + secondType + ".";
	}

	public static String funAlreadyExist(String funName, int definedLine){
		return "Procedure '" + funName + "' is already defined at line " + definedLine + ".";
	}

	public static String funArgTypeUnk(String argType, String argString){
		return "Unable to recognize type " + argType + " of argument " + argString + ".";
	}

	public static String funArgsNumWrong(String funName, int gotN, int wantN){
		return "Attempting to call procedure " + funName + " with " + gotN + " argument" + (gotN != 1 ? "s" : "") + " but it requires " + wantN + " parameter" + (wantN != 1 ? "s" : "") + ".";
	}

	public static String funArgsTypeWrong(String funName, List<String> gotTypes, List<String> wantTypes){
		StringBuilder msg = new StringBuilder("Attempting to call procedure " + funName + " with argument");
		if (gotTypes.size() > 1) msg.append("s");
		msg.append(" of type");
		for (String g : gotTypes){
			msg.append(" ").append(g).append(",");
		}
		msg.delete(msg.length() - 1, msg.length());
		msg.append(" but it requires parameter");
		if (wantTypes.size() > 1) msg.append("s");
		msg.append(" of type");
		for (String w : wantTypes){
			msg.append(" ").append(w).append(",");
		}
		msg.replace(msg.length() - 1, msg.length(), ".");
		return msg.toString();
	}

	public static String funParAlreadyExist(String funName, String paramName){
		return "Procedure " + funName + " already has a parameter variable with name " + paramName + ".";
	}

	public static String funParAlreadyVar(String paramName, int declaredLine){
		return "Name for parameter variable '" + paramName + "' is already used for a variable at line " + declaredLine + ".";
	}

	public static String funParTypeUnk(String paramType, String paramName){
		return "Unable to recognize type " + paramType + " of parameter variable " + paramName + ".";
	}

	public static String funResNotVoid(String typeString) {
		String nothing = simpleLexer.VOCABULARY.getLiteralName(simpleParser.NULL).replace("'", "");
		return "Attempting to use as instruction a procedure call that produces a value of type " + typeString + " instead of " + nothing + ".";
	}

	public static String funResTypeUnk(String resType, String funName){
		return "Unable to recognize result type " + resType + " of procedure " + funName + ".";
	}

	public static String funUndefined(String funName, List<String> suggestions){
		StringBuilder msg = new StringBuilder("Procedure '" + funName + "' is undefined.");
		if (!suggestions.isEmpty()) {
			msg.append(" Did you mean");
			for (String s : suggestions) {
				msg.append(" '").append(s).append("',");
			}
			msg.replace(msg.length() - 1, msg.length(), " ?");
		}
		return msg.toString();
	}

	public static String funUnused(String funName, int definedLine){
		return "Procedure " + funName + " defined at line " + definedLine + " but never used.";
	}

	public static String invalidCounterUse(){
		return "Attempting to use " + simpleLexer.VOCABULARY.getLiteralName(simpleParser.COUNTER) + " outside of any loop.";
	}

	public static String invalidSizeOf(String varType) {
		return "Attempting to get size of non-structured variable of type " + varType + ".";
	}

	public static String invalidSizeOfEmpty() {
		return "Attempting to get size of structured variable but it has none because it appears to have no value yet.";
	}

	public static String listInsertTypeWrong(typeVisitor.dataType gotType, typeVisitor.dataType wantType) {
		return "Attempting to insert value of type " + gotType + " in a list of " + wantType + ".";
	}

	public static String listOpNotList(typeVisitor.dataType structType) {
		return "Attempting to use structure of type " + structType + " in list operation.";
	}

	public static String litStructTypeMixed(String structKind, String foundType1, String foundType2) {
		return "Attempting to use " + structKind + " literal with heterogeneous elements, found type " + foundType1 + " but also type " + foundType2 + ".";
	}

	public static String litStructTypeUnk(String valueString, String structKind) {
		return "Unable to recognize type of element " + valueString + " in " + structKind + " literal.";
	}

	public static String loopQuantTypeWrong(String typeString) {
		return "Attempting to use value of mismatched type " + typeString + " as loop quantifier.";
	}

	public static String loopQuantTypeUnk(String valueString) {
		return "Unable to recognize type of loop quantifier value " + valueString + ".";
	}

	public static String mainParStruct(String paramName, String paramType) {
		String mainFunc = simpleLexer.VOCABULARY.getLiteralName(simpleParser.MAIN).replace("'", "");
		return "Attempting to define " + mainFunc + " with structured parameter " + paramName + " of type " + paramType + ".";
	}

	public static String mergeNotFromTextList(String typeString) {
		return "Attempting to use variable of mismatched type " + typeString + " as list of Text to merge into Text.";
	}

	public static String mergeNotToText(String typeString) {
		return "Attempting to use variable of mismatched type " + typeString + " as Text to merge a list of Text into.";
	}

	public static String operandTypeWrong(String operandType, String operationKind) {
		return "Attempting to use operand of mismatched type " + operandType + " in " + operationKind + " operation.";
	}

	public static String operandTypeUnk(String operandString) {
		return "Unable to recognize type of operand " + operandString + ".";
	}

	public static String retTypeWrong(String gotType, String funName, String wantType){
		return "Attempting to return value of mismatched type " + gotType + " but procedure " + funName + " should return " + wantType + ".";
	}

	public static String retTypeUnk(String valueString){
		return "Unable to recognize type of returned value " + valueString + ".";
	}

	public static String splitNotFromText(String typeString) {
		return "Attempting to use variable of mismatched type " + typeString + " as Text to split into a list of Text.";
	}

	public static String splitNotToTextList(String typeString) {
		return "Attempting to use variable of mismatched type " + typeString + " as s list of Text to split Text into.";
	}

	public static String structNoValue(String structName) {
		return "Attempting to access structure " + structName + " but it appears to have no value yet.";
	}

	public static String structTypeUnk(String structName) {
		return "Unable to recognize if " + structName + " is a variable of structured type.";
	}

	public static String typeAlreadyExist(String typeName, int declaredLine){
		return "Type '" + typeName + "' is already defined at line " + declaredLine + ".";
	}

	public static String typeDefRecInvalid(String nameStr) {
		return "Attempting to define type " + nameStr + " as an invalid recursive type.";
	}

	public static String typeDefUnk(String typeString) {
		return "Unable to recognize aliased type " + typeString + ".";
	}

	public static String typeUndefined(String typeName, List<String> suggestions){
		StringBuilder msg = new StringBuilder("Type '" + typeName + "' is undefined.");
		if (!suggestions.isEmpty()) {
			msg.append(" Did you mean");
			for (String s : suggestions) {
				msg.append(" '").append(s).append("',");
			}
			msg.replace(msg.length() - 1, msg.length(), " ?");
		}
		return msg.toString();
	}

	public static String typeUnused(String typeName, int declaredLine){
		return "Type " + typeName + " declared at line " + declaredLine + " but never used.";
	}

	public static String varAlreadyExist(String varName, int declaredLine){
		return "Variable " + varName + " is already declared at line " + declaredLine + ".";
	}

	public static String varInitTypeWrong(String varType, String valType){
		return "Attempting to initialize variable of type " + varType + " with value of mismatched type " + valType + ".";
	}

	public static String varInitTypeUnk(String valueString, String varName){
		return "Unable to recognize type of initialization value " + valueString + " for variable " + varName + ".";
	}

	public static String varNoValue(String varName) {
		return "Attempting to use value of variable " + varName + " but it appears to have no value yet.";
	}

	public static String varTypeUnk(String typeString, String varName){
		return "Unable to recognize type " + typeString + " of variable " + varName +".";
	}

	public static String varUndefined(String varName, List<String> suggestions){
		StringBuilder msg = new StringBuilder("Variable '" + varName + "' is undefined.");
		if (!suggestions.isEmpty()) {
			msg.append(" Did you mean");
			for (String s : suggestions) {
				msg.append(" '").append(s).append("',");
			}
			msg.replace(msg.length() - 1, msg.length(), " ?");
		}
		return msg.toString();
	}

	public static String varUnused(String varName){
		return "Variable " + varName + " declared but never used.";
	}

}
