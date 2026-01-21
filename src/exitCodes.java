/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     exitCodes.java is part of SIMPLE.
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

public final class exitCodes {
	public static final int
			TERMINATION = 0,
			WRONG_FILE_TYPE = 1,
			CANNOT_READ_FILE = 2,
			INVALID_COMMAND_ARGS = 3,
			FAILED_SPELLING_PARSE = 4,
			FAILED_SPELLING_CHECK = 5,
			FAILED_PROGRAM_PARSE = 6,
			FAILED_PROGRAM_VALIDATION = 7,
			INVALID_PROGRAM_ARGS = 8,
			ILLEGAL_OPERATION = 9,
			ILLEGAL_ACCESS = 10,
			ILLEGAL_EXECUTION_STATE = 11,
			ILLEGAL_INSTRUCTION = 12,
			FAILED_INPUT = 13;
}
