/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     errorRuntimeMsg.java is part of SIMPLE.
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

class errorRuntimeMsg {

	private static final String prefix = "[RUNTIME ERROR] ";

	public static String accessOverSize(int position, int size) {
		return prefix + "Attempting to access position " + position + " out of structure's size " + size + ".";
	}

	public static String accessUnderSize(int position) {
		return prefix + "Attempting to access structure at invalid position " + position + ".";
	}

	public static String divisionByZero() {
		return prefix + "Attempting to divide by a value equal to zero.";
	}

	public static String inputAgain() {
		return prefix + "Input unsuccessful. Enter an input again.";
	}

	public static String inputInterrupted() {
		return prefix + "Program execution interrupted at input.";
	}

	public static String inputMismatchedType(typeVisitor.dataType gotType, typeVisitor.dataType wantType) {
		return prefix + "Attempting to input value of mismatched type " + gotType + " but type " + wantType + " is expected.";
	}

	public static String inputTooManyAttempts(int maxLoop){
		return prefix + "Unsuccessful input attempts over limit of " + maxLoop +
				". If this is not user error then program may be broken.";
	}

	public static String loopNegative(int limit) {
		return prefix + "Attempting to execute quantified loop using negative value " + limit + ".";
	}

	public static String loopOverLimit(int maxLoop) {
		return prefix + "Attempting to execute conditional loop over iteration limit of " + maxLoop +
				". Program may be stuck in an infinite loop.";
	}

	public static String modulusByZero() {
		return prefix + "Attempting to calculate modulus using a value equal to zero.";
	}

	public static String noMain() {
		return prefix + "Attempting to execute file with no Main procedure.";
	}

	public static String recursionOverLimit(String funName, int maxRec) {
		return prefix + "Attempting to execute function " + funName + " over recursion limit of " + maxRec +
				". Program may be stuck in an infinite call loop.";
	}

	public static String unexpectedInputRequest() {
		return prefix + "Unable to determine expected type from input. Missing record from validation.";
	}
}
