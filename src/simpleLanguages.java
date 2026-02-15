/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     simpleLanguages.java is part of SIMPLE.
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

public class simpleLanguages {

	public enum Lang {
		ALT,
		ENG,
		ESP,
		FRA,
		ITA
	}

	public static String[] literalNames(Lang l){
		return switch (l){
			case ALT -> simpleLangAlternative.literalNames();
			case ENG -> simpleLangEnglish.literalNames();
			case ESP -> simpleLangSpanish.literalNames();
			case FRA -> simpleLangFrench.literalNames();
			case ITA -> simpleLangItalian.literalNames();
		};
	}
	public static String lexerSerializedATN(Lang l){
		return switch (l){
			case ALT -> simpleLangAlternative.lexerSerializedATN();
			case ENG -> simpleLangEnglish.lexerSerializedATN();
			case ESP -> simpleLangSpanish.lexerSerializedATN();
			case FRA -> simpleLangFrench.lexerSerializedATN();
			case ITA -> simpleLangItalian.lexerSerializedATN();
		};
	}
	public static String SpellCheckLexerSerializedATN(Lang l){
		return switch (l){
			case ALT -> simpleLangAlternative.SpellCheckLexerSerializedATN();
			case ENG -> simpleLangEnglish.SpellCheckLexerSerializedATN();
			case ESP -> simpleLangSpanish.SpellCheckLexerSerializedATN();
			case FRA -> simpleLangFrench.SpellCheckLexerSerializedATN();
			case ITA -> simpleLangItalian.SpellCheckLexerSerializedATN();
		};
	}

}