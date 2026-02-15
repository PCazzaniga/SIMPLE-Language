/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     simpleLexer.java is part of SIMPLE.
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
public class simpleLexer extends Lexer {

	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	public static final int
		Text=1, Boolean=2, TYPENAME=3, NAME=4, NUM=5, COMMENT=6, S=7, NEWTYPE=8, SCOPE_OPEN=9, DEFINE=10, MAIN=11,
		RESULT=12, PARAMS=13, SEPAR=14, PARAMS_ONLY=15, COMMA=16, SCOPE_CLOSE=17, IF=18, THEN=19, ELSEIF=20, ELSE=21,
		REPEAT=22, WHILE=23, TIMES=24, RETURN=25, PREPARE=26, NAMED=27, VALUED=28, SET=29, TO=30, INSERT=31, IN=32,
		REMOVE=33, SPLIT=34, MERGE=35, NUMBER=36, STRING=37, BOOL=38, EXP_OPEN=39, EXP_CLOSE=40, SEQUENCE=41, LIST=42,
		KIT=43, POSITION=44, AT_FIELD=45, OUTP=46, INP=47, COUNTER=48, SIZEOF=49, RANDOM=50, NULL=51, TXT_DELIM=52,
		ESCAPER=53, DOT=54, SEQ_OPEN=55, SEQ_CLOSE=56, LST_DELIM=57, SEPAR_ALT=58, KIT_OPEN=59, KIT_CLOSE=60, ADD=61,
		SUB=62, MULT=63, DIV=64, MODULUS=65, AND=66, OR=67, NOT=68, GT=69, LT=70, EQ=71, CALL=72, ARGUMENTS=73,
		TYPE_PREFIX=74, COMM_DELIM=75, TRUE=76, FALSE=77, TAB=78, INDENT=79, DEDENT=80, NEWLINE=81, EXTRANEOUS_INPUT=82;

	private final String[] channelNames = {
			"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	private final String[] modeNames = {
			"DEFAULT_MODE"
	};

	private final String[] ruleNames = new String[] {
		"Text", "Boolean", "TYPENAME", "NAME", "NUM", "LETTER", "DIGIT", "COMMENT", "S", "NEWTYPE", "SCOPE_OPEN",
		"DEFINE", "MAIN", "RESULT", "PARAMS", "SEPAR", "PARAMS_ONLY", "COMMA", "SCOPE_CLOSE", "IF", "THEN", "ELSEIF",
		"ELSE", "REPEAT", "WHILE", "TIMES", "RETURN", "PREPARE", "NAMED", "VALUED", "SET", "TO", "INSERT", "IN",
		"REMOVE", "SPLIT", "MERGE", "NUMBER", "STRING", "BOOL", "EXP_OPEN", "EXP_CLOSE", "SEQUENCE", "LIST", "KIT",
		"POSITION", "AT_FIELD", "OUTP", "INP", "COUNTER", "SIZEOF", "RANDOM", "NULL", "TXT_DELIM", "ESCAPER", "DOT",
		"SEQ_OPEN", "SEQ_CLOSE", "LST_DELIM", "SEPAR_ALT", "KIT_OPEN", "KIT_CLOSE", "ADD", "SUB", "MULT", "DIV",
		"MODULUS", "AND", "OR", "NOT", "GT", "LT", "EQ", "CALL", "ARGUMENTS", "TYPE_PREFIX", "COMM_DELIM", "TRUE",
		"FALSE", "TAB", "INDENT", "DEDENT", "NEWLINE", "EXTRANEOUS_INPUT"
	};

	private final Vocabulary VOCABULARY;

	private final String _serializedATN;
	private final ATN _ATN;

	@Override
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	public simpleLexer(CharStream input, simpleLanguages.Lang language) {
		super(input);

		_serializedATN = simpleLanguages.lexerSerializedATN(language);
		_ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
		DFA[] _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA, new PredictionContextCache());

		String[] _LITERAL_NAMES = simpleLanguages.literalNames(language);
		String[] _SYMBOLIC_NAMES = new String[]{
			null, "Text", "Boolean", "TYPENAME", "NAME", "NUM", "COMMENT", "S", "NEWTYPE", "SCOPE_OPEN", "DEFINE",
			"MAIN", "RESULT", "PARAMS", "SEPAR", "PARAMS_ONLY", "COMMA", "SCOPE_CLOSE", "IF", "THEN", "ELSEIF", "ELSE",
			"REPEAT", "WHILE", "TIMES", "RETURN", "PREPARE", "NAMED", "VALUED", "SET", "TO", "INSERT", "IN", "REMOVE",
			"SPLIT", "MERGE", "NUMBER", "STRING", "BOOL", "EXP_OPEN", "EXP_CLOSE", "SEQUENCE", "LIST", "KIT",
			"POSITION", "AT_FIELD", "OUTP", "INP", "COUNTER", "SIZEOF", "RANDOM", "NULL", "TXT_DELIM", "ESCAPER", "DOT",
			"SEQ_OPEN", "SEQ_CLOSE", "LST_DELIM", "SEPAR_ALT", "KIT_OPEN", "KIT_CLOSE", "ADD", "SUB", "MULT", "DIV",
			"MODULUS", "AND", "OR", "NOT", "GT", "LT", "EQ", "CALL", "ARGUMENTS", "TYPE_PREFIX", "COMM_DELIM", "TRUE",
			"FALSE", "TAB", "INDENT", "DEDENT", "NEWLINE", "EXTRANEOUS_INPUT"
		};
		VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
	}

	@Override
	public String getGrammarFileName() { return "simple.g4"; }

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

	private int currentIndentation = 0;

	private final java.util.LinkedList<Token> pendingTokens = new java.util.LinkedList<>();

	private Token makeToken (int type, String text, Token adj) {
		CommonToken token = new CommonToken(adj);
		token.setType(type);
		token.setText(text);
		token.setChannel(0);
		return token;
	}

	@Override
	public Token nextToken() {
		if (!pendingTokens.isEmpty()) return pendingTokens.poll();
		Token next = super.nextToken();

		if(next.getType() == NEWLINE){
			Token nexter = super.nextToken();
			if (nexter.getType() != TAB){
				while(currentIndentation > 0){
					pendingTokens.offer(makeToken(DEDENT, "Dedent#" + currentIndentation, nexter));
					currentIndentation--;
				}
				if (nexter.getType() == EOF) pendingTokens.offer(makeToken(NEWLINE, "NL", next));
				pendingTokens.offer(nexter);
			} else {
				int indentCount = nexter.getText().length();
				CommonToken nxtr = new CommonToken(nexter);
				nxtr.setChannel(HIDDEN);
				pendingTokens.offer(nxtr);
				while (indentCount > currentIndentation) {
					currentIndentation++;
					pendingTokens.offer(makeToken(INDENT, "Indent#" + currentIndentation, nexter));
				}
				while (indentCount < currentIndentation) {
					pendingTokens.offer(makeToken(DEDENT, "Dedent#" + currentIndentation, nexter));
					currentIndentation--;
				}
			}
		}
		else if (next.getType() == EOF){
			pendingTokens.offer(makeToken(NEWLINE, "NL", next));
			while(currentIndentation > 0){
				pendingTokens.offer(makeToken(DEDENT, "Dedent#" + currentIndentation, next));
				currentIndentation--;
			}
			pendingTokens.offer(makeToken(NEWLINE, "NL", next));
			pendingTokens.offer(next);
			return pendingTokens.poll();
		}
		return next;
	}
}
