/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     simpleSpellCheckLexer.java is part of SIMPLE.
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

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"unused"})
public class simpleSpellCheckLexer extends Lexer {

	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	public static final int
		NAME=1, NUM=2, TEXT=3, COMMENT=4, TAB=5, NEWLINE=6, S=7, NEWTYPE=8, SCOPE_OPEN=9, DEFINE=10, MAIN=11, RESULT=12,
		PARAMS=13, SEPAR=14, PARAMS_ONLY=15, COMMA=16, SCOPE_CLOSE=17, IF=18, THEN=19, ELSEIF=20, ELSE=21, REPEAT=22,
		WHILE=23, TIMES=24, RETURN=25, PREPARE=26, NAMED=27, VALUED=28, SET=29, TO=30, INSERT=31, IN=32, REMOVE=33,
		SPLIT=34, MERGE=35, NUMBER=36, STRING=37, BOOL=38, EXP_OPEN=39, EXP_CLOSE=40, SEQUENCE=41, LIST=42, KIT=43,
		POSITION=44, AT_FIELD=45, OUTP=46, INP=47, COUNTER=48, SIZEOF=49, RANDOM=50, NULL=51, TXT_DELIM=52, ESCAPER=53,
		DOT=54, SEQ_OPEN=55, SEQ_CLOSE=56, LST_DELIM=57, SEPAR_ALT=58, KIT_OPEN=59, KIT_CLOSE=60, ADD=61, SUB=62,
		MULT=63, DIV=64, MODULUS=65, AND=66, OR=67, NOT=68, GT=69, LT=70, EQ=71, CALL=72, ARGUMENTS=73, TYPE_PREFIX=74,
		COMM_DELIM=75, TRUE=76, FALSE=77, UNKNOWN=78;

	private final String[] channelNames = {
			"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	private final String[] modeNames = {
			"DEFAULT_MODE"
	};

	private final String[] ruleNames = new String[] {
		"NAME", "NUM", "TEXT", "LETTER", "DIGIT", "COMMENT", "TAB", "NEWLINE", "S", "NEWTYPE", "SCOPE_OPEN", "DEFINE",
		"MAIN", "RESULT", "PARAMS", "SEPAR", "PARAMS_ONLY", "COMMA", "SCOPE_CLOSE", "IF", "THEN", "ELSEIF", "ELSE",
		"REPEAT", "WHILE", "TIMES", "RETURN", "PREPARE", "NAMED", "VALUED", "SET", "TO", "INSERT", "IN", "REMOVE",
		"SPLIT", "MERGE", "NUMBER", "STRING", "BOOL", "EXP_OPEN", "EXP_CLOSE", "SEQUENCE", "LIST", "KIT", "POSITION",
		"AT_FIELD", "OUTP", "INP", "COUNTER", "SIZEOF", "RANDOM", "NULL", "TXT_DELIM", "ESCAPER", "DOT", "SEQ_OPEN",
		"SEQ_CLOSE", "LST_DELIM", "SEPAR_ALT", "KIT_OPEN", "KIT_CLOSE", "ADD", "SUB", "MULT", "DIV", "MODULUS", "AND",
		"OR", "NOT", "GT", "LT", "EQ", "CALL", "ARGUMENTS", "TYPE_PREFIX", "COMM_DELIM", "TRUE", "FALSE", "UNKNOWN"
	};

	private final Vocabulary VOCABULARY;

	private final String _serializedATN;
	private final ATN _ATN;

	@Override
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	public simpleSpellCheckLexer(CharStream input, simpleLanguages.Lang language) {
		super(input);

		_serializedATN = simpleLanguages.SpellCheckLexerSerializedATN(language);
		_ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
		DFA[] _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA, new PredictionContextCache());

		String[] _LITERAL_NAMES = simpleLanguages.literalNames(language);
		String[] _SYMBOLIC_NAMES = new String[] {
			null, "NAME", "NUM", "TEXT", "COMMENT", "TAB", "NEWLINE", "S", "NEWTYPE", "SCOPE_OPEN", "DEFINE", "MAIN",
			"RESULT", "PARAMS", "SEPAR", "PARAMS_ONLY", "COMMA", "SCOPE_CLOSE", "IF", "THEN", "ELSEIF", "ELSE",
			"REPEAT", "WHILE", "TIMES", "RETURN", "PREPARE", "NAMED", "VALUED", "SET", "TO", "INSERT", "IN", "REMOVE",
			"SPLIT", "MERGE", "NUMBER", "STRING", "BOOL", "EXP_OPEN", "EXP_CLOSE", "SEQUENCE", "LIST", "KIT",
			"POSITION", "AT_FIELD", "OUTP", "INP", "COUNTER", "SIZEOF", "RANDOM", "NULL", "TXT_DELIM", "ESCAPER", "DOT",
			"SEQ_OPEN", "SEQ_CLOSE", "LST_DELIM", "SEPAR_ALT", "KIT_OPEN", "KIT_CLOSE", "ADD", "SUB", "MULT", "DIV",
			"MODULUS", "AND", "OR", "NOT", "GT", "LT", "EQ", "CALL", "ARGUMENTS", "TYPE_PREFIX", "COMM_DELIM", "TRUE",
			"FALSE", "UNKNOWN"
		};
		VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
	}

	@Override
	public String getGrammarFileName() { return "simpleSpellCheck.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() {
		return _ATN;
	}

	/*********************************/

	private final java.util.Deque<Token> stack = new java.util.ArrayDeque<>();

	@Override
	public Token nextToken() {
		Token next = (!stack.isEmpty() ? stack.pop() : super.nextToken());

		if(next.getType() == UNKNOWN){
			next = collectUnknowns(next);
		} else if (next.getType() == NAME){
			CommonToken nexter = new CommonToken(super.nextToken());
			if (nexter.getType() == UNKNOWN) {
				nexter.setText(next.getText() + nexter.getText());
				nexter.setStartIndex(next.getStartIndex());
				nexter.setCharPositionInLine(next.getCharPositionInLine());
				nexter.setTokenIndex(next.getTokenIndex());
				next = collectUnknowns(nexter);
			} else {
				stack.push(nexter);
			}
		}
		return next;
	}

	private Token collectUnknowns(Token first) {
		StringBuilder collector = new StringBuilder(first.getText());
		Token next = super.nextToken();
		Token prev = first;
		while (!((prev.getType() == S && next.getType() != UNKNOWN) || next.getType() == NEWLINE || next.getType() == EOF)){
			collector.append(next.getText());
			prev = next;
			next = super.nextToken();
		}
		stack.push(next);
		CommonToken collected = new CommonToken(first);
		if(prev.getType() == S || prev.getType() == DOT || prev.getType() == SCOPE_CLOSE || prev.getType() == SCOPE_OPEN || prev.getType() == COMMA){
			collector.setLength(collector.length() - 1);
			collected.setStopIndex(prev.getStartIndex() - 1);
			stack.push(prev);
		} else {
			collected.setStopIndex(prev.getStopIndex());
		}
		collected.setText(collector.toString());
		return collected;
	}
}
