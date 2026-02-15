/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     simpleParser.java is part of SIMPLE.
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

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all"})
public class simpleParser extends Parser {

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

	public static final int
		RULE_file = 0, RULE_information = 1, RULE_type_decl = 2, RULE_fun_decl = 3, RULE_fun_def = 4, RULE_fun_body = 5,
		RULE_scope_block = 6, RULE_comment = 7, RULE_nl = 8, RULE_statement = 9, RULE_closer = 10,
		RULE_flow_control = 11, RULE_branch = 12, RULE_if_cond = 13, RULE_elif_cond = 14, RULE_else_cond = 15,
		RULE_loop = 16, RULE_loop_def = 17, RULE_condition = 18, RULE_return = 19, RULE_instruction = 20,
		RULE_var_decl = 21, RULE_var_def = 22, RULE_var_init = 23, RULE_expr = 24, RULE_assignment = 25,
		RULE_list_op = 26, RULE_value = 27, RULE_direct_value = 28, RULE_variable = 29, RULE_var_name = 30,
		RULE_struct_access = 31, RULE_access = 32, RULE_reserved = 33, RULE_literal = 34, RULE_number = 35,
		RULE_struct_lit = 36, RULE_sequence = 37, RULE_list = 38, RULE_kit = 39, RULE_operation = 40,
		RULE_arith_op = 41, RULE_ar_oprnd = 42, RULE_logic_op = 43, RULE_log_oprnd = 44, RULE_compare_op = 45,
		RULE_comp_oprnd = 46, RULE_fun_call = 47, RULE_fun_name = 48, RULE_type = 49, RULE_declared_type = 50,
		RULE_struct_type = 51, RULE_seq_type = 52, RULE_list_type = 53, RULE_kit_type = 54, RULE_param = 55;

	private final String[] ruleNames = new String[] {
		"file", "information", "type_decl", "fun_decl", "fun_def", "fun_body", "scope_block", "comment", "nl",
		"statement", "closer", "flow_control", "branch", "if_cond", "elif_cond", "else_cond", "loop", "loop_def",
		"condition", "return", "instruction", "var_decl", "var_def", "var_init", "expr", "assignment", "list_op",
		"value", "direct_value", "variable", "var_name", "struct_access", "access", "reserved", "literal", "number",
		"struct_lit", "sequence", "list", "kit", "operation", "arith_op", "ar_oprnd", "logic_op", "log_oprnd",
		"compare_op", "comp_oprnd", "fun_call", "fun_name", "type", "declared_type", "struct_type", "seq_type",
		"list_type", "kit_type", "param"
	};

	private final Vocabulary VOCABULARY;

	public simpleParser(TokenStream input, simpleLanguages.Lang language){
		super(input);

		DFA[] _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,new PredictionContextCache());

		String[] _LITERAL_NAMES = simpleLanguages.literalNames(language);
		String[] _SYMBOLIC_NAMES = new String[] {
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

	@Deprecated
	@Override
	public String[] getTokenNames() {
		return null;
	}

	@Override
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "simple.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(simpleParser.EOF, 0); }
		public List<CommentContext> comment() {
			return getRuleContexts(CommentContext.class);
		}
		public CommentContext comment(int i) {
			return getRuleContext(CommentContext.class,i);
		}
		public List<InformationContext> information() {
			return getRuleContexts(InformationContext.class);
		}
		public InformationContext information(int i) {
			return getRuleContext(InformationContext.class,i);
		}
		public List<NlContext> nl() {
			return getRuleContexts(NlContext.class);
		}
		public NlContext nl(int i) {
			return getRuleContext(NlContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(114);
							_errHandler.sync(this);
							switch (_input.LA(1)) {
								case COMMENT:
								{
									setState(112);
									comment();
								}
								break;
								case DEFINE:
								case PREPARE:
								{
									setState(113);
									information();
								}
								break;
								default:
									throw new NoViableAltException(this);
							}
							setState(117);
							_errHandler.sync(this);
							_la = _input.LA(1);
							do {
								{
									{
										setState(116);
										nl();
									}
								}
								setState(119);
								_errHandler.sync(this);
								_la = _input.LA(1);
							} while ( _la==NEWLINE );
						}
					}
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 67109952L) != 0) );
				setState(125);
				match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InformationContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(simpleParser.DOT, 0); }
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public Type_declContext type_decl() {
			return getRuleContext(Type_declContext.class,0);
		}
		public Fun_declContext fun_decl() {
			return getRuleContext(Fun_declContext.class,0);
		}
		public InformationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_information; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterInformation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitInformation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitInformation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InformationContext information() throws RecognitionException {
		InformationContext _localctx = new InformationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_information);
		try {
			setState(134);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case PREPARE:
					enterOuterAlt(_localctx, 1);
				{
					setState(129);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
						case 1:
						{
							setState(127);
							var_decl();
						}
						break;
						case 2:
						{
							setState(128);
							type_decl();
						}
						break;
					}
					setState(131);
					match(DOT);
				}
				break;
				case DEFINE:
					enterOuterAlt(_localctx, 2);
				{
					setState(133);
					fun_decl();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_declContext extends ParserRuleContext {
		public TerminalNode PREPARE() { return getToken(simpleParser.PREPARE, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode NAMED() { return getToken(simpleParser.NAMED, 0); }
		public TerminalNode TYPENAME() { return getToken(simpleParser.TYPENAME, 0); }
		public TerminalNode NEWTYPE() { return getToken(simpleParser.NEWTYPE, 0); }
		public Type_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterType_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitType_decl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitType_decl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_declContext type_decl() throws RecognitionException {
		Type_declContext _localctx = new Type_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_type_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(136);
				match(PREPARE);
				setState(137);
				match(S);
				setState(138);
				type();
				setState(139);
				match(S);
				setState(140);
				match(NAMED);
				setState(141);
				match(S);
				setState(142);
				match(TYPENAME);
				setState(143);
				match(S);
				setState(144);
				match(NEWTYPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_declContext extends ParserRuleContext {
		public Fun_defContext fun_def() {
			return getRuleContext(Fun_defContext.class,0);
		}
		public TerminalNode SCOPE_OPEN() { return getToken(simpleParser.SCOPE_OPEN, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Fun_bodyContext fun_body() {
			return getRuleContext(Fun_bodyContext.class,0);
		}
		public Fun_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFun_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFun_decl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFun_decl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_declContext fun_decl() throws RecognitionException {
		Fun_declContext _localctx = new Fun_declContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_fun_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(146);
				fun_def();
				setState(147);
				match(SCOPE_OPEN);
				setState(148);
				nl();
				setState(149);
				fun_body();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_defContext extends ParserRuleContext {
		public Token name;
		public TerminalNode DEFINE() { return getToken(simpleParser.DEFINE, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public TerminalNode MAIN() { return getToken(simpleParser.MAIN, 0); }
		public TerminalNode RESULT() { return getToken(simpleParser.RESULT, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode PARAMS_ONLY() { return getToken(simpleParser.PARAMS_ONLY, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public TerminalNode PARAMS() { return getToken(simpleParser.PARAMS, 0); }
		public List<TerminalNode> SEPAR() { return getTokens(simpleParser.SEPAR); }
		public TerminalNode SEPAR(int i) {
			return getToken(simpleParser.SEPAR, i);
		}
		public Fun_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFun_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFun_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFun_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_defContext fun_def() throws RecognitionException {
		Fun_defContext _localctx = new Fun_defContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fun_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(151);
				match(DEFINE);
				setState(152);
				match(S);
				setState(153);
				((Fun_defContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==NAME || _la==MAIN) ) {
					((Fun_defContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==S) {
					{
						setState(154);
						match(S);
						setState(181);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
							case RESULT:
							{
								{
									setState(155);
									match(RESULT);
									setState(156);
									match(S);
									setState(157);
									type();
									setState(169);
									_errHandler.sync(this);
									_la = _input.LA(1);
									if (_la==S) {
										{
											setState(158);
											match(S);
											setState(159);
											match(PARAMS);
											setState(160);
											match(S);
											setState(161);
											param();
											setState(166);
											_errHandler.sync(this);
											_la = _input.LA(1);
											while (_la==SEPAR) {
												{
													{
														setState(162);
														match(SEPAR);
														setState(163);
														param();
													}
												}
												setState(168);
												_errHandler.sync(this);
												_la = _input.LA(1);
											}
										}
									}

								}
							}
							break;
							case PARAMS_ONLY:
							{
								{
									setState(171);
									match(PARAMS_ONLY);
									setState(172);
									match(S);
									setState(173);
									param();
									setState(178);
									_errHandler.sync(this);
									_la = _input.LA(1);
									while (_la==SEPAR) {
										{
											{
												setState(174);
												match(SEPAR);
												setState(175);
												param();
											}
										}
										setState(180);
										_errHandler.sync(this);
										_la = _input.LA(1);
									}
								}
							}
							break;
							default:
								throw new NoViableAltException(this);
						}
					}
				}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_bodyContext extends ParserRuleContext {
		public TerminalNode INDENT() { return getToken(simpleParser.INDENT, 0); }
		public ReturnContext return_() {
			return getRuleContext(ReturnContext.class,0);
		}
		public TerminalNode DOT() { return getToken(simpleParser.DOT, 0); }
		public List<NlContext> nl() {
			return getRuleContexts(NlContext.class);
		}
		public NlContext nl(int i) {
			return getRuleContext(NlContext.class,i);
		}
		public TerminalNode DEDENT() { return getToken(simpleParser.DEDENT, 0); }
		public List<CommentContext> comment() {
			return getRuleContexts(CommentContext.class);
		}
		public CommentContext comment(int i) {
			return getRuleContext(CommentContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Fun_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFun_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFun_body(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFun_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_bodyContext fun_body() throws RecognitionException {
		Fun_bodyContext _localctx = new Fun_bodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_fun_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(185);
				match(INDENT);
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 62885462080L) != 0) || _la==CALL) {
					{
						setState(190);
						_errHandler.sync(this);
						switch (_input.LA(1)) {
							case COMMENT:
							{
								setState(186);
								comment();
								setState(187);
								nl();
							}
							break;
							case IF:
							case REPEAT:
							case PREPARE:
							case SET:
							case INSERT:
							case REMOVE:
							case SPLIT:
							case MERGE:
							case CALL:
							{
								setState(189);
								statement();
							}
							break;
							default:
								throw new NoViableAltException(this);
						}
					}
					setState(194);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(195);
				return_();
				setState(196);
				match(DOT);
				setState(197);
				nl();
				setState(198);
				match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Scope_blockContext extends ParserRuleContext {
		public TerminalNode INDENT() { return getToken(simpleParser.INDENT, 0); }
		public CloserContext closer() {
			return getRuleContext(CloserContext.class,0);
		}
		public TerminalNode DEDENT() { return getToken(simpleParser.DEDENT, 0); }
		public List<CommentContext> comment() {
			return getRuleContexts(CommentContext.class);
		}
		public CommentContext comment(int i) {
			return getRuleContext(CommentContext.class,i);
		}
		public List<NlContext> nl() {
			return getRuleContexts(NlContext.class);
		}
		public NlContext nl(int i) {
			return getRuleContext(NlContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Scope_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scope_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterScope_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitScope_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitScope_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scope_blockContext scope_block() throws RecognitionException {
		Scope_blockContext _localctx = new Scope_blockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_scope_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(200);
				match(INDENT);
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
							setState(205);
							_errHandler.sync(this);
							switch (_input.LA(1)) {
								case COMMENT:
								{
									setState(201);
									comment();
									setState(202);
									nl();
								}
								break;
								case IF:
								case REPEAT:
								case PREPARE:
								case SET:
								case INSERT:
								case REMOVE:
								case SPLIT:
								case MERGE:
								case CALL:
								{
									setState(204);
									statement();
								}
								break;
								default:
									throw new NoViableAltException(this);
							}
						}
					}
					setState(209);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				setState(210);
				closer();
				setState(211);
				match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(simpleParser.COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(213);
				match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NlContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(simpleParser.NEWLINE, 0); }
		public NlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterNl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitNl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitNl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NlContext nl() throws RecognitionException {
		NlContext _localctx = new NlContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_nl);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(215);
				match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(simpleParser.COMMA, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Flow_controlContext flow_control() {
			return getRuleContext(Flow_controlContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		try {
			setState(222);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case PREPARE:
				case SET:
				case INSERT:
				case REMOVE:
				case SPLIT:
				case MERGE:
				case CALL:
					enterOuterAlt(_localctx, 1);
				{
					setState(217);
					instruction();
					setState(218);
					match(COMMA);
					setState(219);
					nl();
				}
				break;
				case IF:
				case REPEAT:
					enterOuterAlt(_localctx, 2);
				{
					setState(221);
					flow_control();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CloserContext extends ParserRuleContext {
		public TerminalNode SCOPE_CLOSE() { return getToken(simpleParser.SCOPE_CLOSE, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public InstructionContext instruction() {
			return getRuleContext(InstructionContext.class,0);
		}
		public ReturnContext return_() {
			return getRuleContext(ReturnContext.class,0);
		}
		public Flow_controlContext flow_control() {
			return getRuleContext(Flow_controlContext.class,0);
		}
		public CloserContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterCloser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitCloser(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitCloser(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloserContext closer() throws RecognitionException {
		CloserContext _localctx = new CloserContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_closer);
		try {
			setState(232);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case RETURN:
				case PREPARE:
				case SET:
				case INSERT:
				case REMOVE:
				case SPLIT:
				case MERGE:
				case CALL:
					enterOuterAlt(_localctx, 1);
				{
					setState(226);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
						case PREPARE:
						case SET:
						case INSERT:
						case REMOVE:
						case SPLIT:
						case MERGE:
						case CALL:
						{
							setState(224);
							instruction();
						}
						break;
						case RETURN:
						{
							setState(225);
							return_();
						}
						break;
						default:
							throw new NoViableAltException(this);
					}
					setState(228);
					match(SCOPE_CLOSE);
					setState(229);
					nl();
				}
				break;
				case IF:
				case REPEAT:
					enterOuterAlt(_localctx, 2);
				{
					setState(231);
					flow_control();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Flow_controlContext extends ParserRuleContext {
		public BranchContext branch() {
			return getRuleContext(BranchContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public Flow_controlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flow_control; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFlow_control(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFlow_control(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFlow_control(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Flow_controlContext flow_control() throws RecognitionException {
		Flow_controlContext _localctx = new Flow_controlContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_flow_control);
		try {
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case IF:
					enterOuterAlt(_localctx, 1);
				{
					setState(234);
					branch();
				}
				break;
				case REPEAT:
					enterOuterAlt(_localctx, 2);
				{
					setState(235);
					loop();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BranchContext extends ParserRuleContext {
		public If_condContext if_cond() {
			return getRuleContext(If_condContext.class,0);
		}
		public List<Elif_condContext> elif_cond() {
			return getRuleContexts(Elif_condContext.class);
		}
		public Elif_condContext elif_cond(int i) {
			return getRuleContext(Elif_condContext.class,i);
		}
		public Else_condContext else_cond() {
			return getRuleContext(Else_condContext.class,0);
		}
		public BranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitBranch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitBranch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BranchContext branch() throws RecognitionException {
		BranchContext _localctx = new BranchContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_branch);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(238);
				if_cond();
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ELSEIF) {
					{
						{
							setState(239);
							elif_cond();
						}
					}
					setState(244);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
						setState(245);
						else_cond();
					}
				}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_condContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(simpleParser.IF, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(simpleParser.THEN, 0); }
		public TerminalNode SCOPE_OPEN() { return getToken(simpleParser.SCOPE_OPEN, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Scope_blockContext scope_block() {
			return getRuleContext(Scope_blockContext.class,0);
		}
		public If_condContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterIf_cond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitIf_cond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitIf_cond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_condContext if_cond() throws RecognitionException {
		If_condContext _localctx = new If_condContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_if_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(248);
				match(IF);
				setState(249);
				match(S);
				setState(250);
				condition();
				setState(251);
				match(S);
				setState(252);
				match(THEN);
				setState(253);
				match(SCOPE_OPEN);
				setState(254);
				nl();
				setState(255);
				scope_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Elif_condContext extends ParserRuleContext {
		public TerminalNode ELSEIF() { return getToken(simpleParser.ELSEIF, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(simpleParser.THEN, 0); }
		public TerminalNode SCOPE_OPEN() { return getToken(simpleParser.SCOPE_OPEN, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Scope_blockContext scope_block() {
			return getRuleContext(Scope_blockContext.class,0);
		}
		public Elif_condContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elif_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterElif_cond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitElif_cond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitElif_cond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Elif_condContext elif_cond() throws RecognitionException {
		Elif_condContext _localctx = new Elif_condContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_elif_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(257);
				match(ELSEIF);
				setState(258);
				match(S);
				setState(259);
				condition();
				setState(260);
				match(S);
				setState(261);
				match(THEN);
				setState(262);
				match(SCOPE_OPEN);
				setState(263);
				nl();
				setState(264);
				scope_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Else_condContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(simpleParser.ELSE, 0); }
		public TerminalNode SCOPE_OPEN() { return getToken(simpleParser.SCOPE_OPEN, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Scope_blockContext scope_block() {
			return getRuleContext(Scope_blockContext.class,0);
		}
		public Else_condContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterElse_cond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitElse_cond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitElse_cond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_condContext else_cond() throws RecognitionException {
		Else_condContext _localctx = new Else_condContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_else_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(266);
				match(ELSE);
				setState(267);
				match(SCOPE_OPEN);
				setState(268);
				nl();
				setState(269);
				scope_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public TerminalNode REPEAT() { return getToken(simpleParser.REPEAT, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public Loop_defContext loop_def() {
			return getRuleContext(Loop_defContext.class,0);
		}
		public TerminalNode SCOPE_OPEN() { return getToken(simpleParser.SCOPE_OPEN, 0); }
		public NlContext nl() {
			return getRuleContext(NlContext.class,0);
		}
		public Scope_blockContext scope_block() {
			return getRuleContext(Scope_blockContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_loop);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(271);
				match(REPEAT);
				setState(272);
				match(S);
				setState(273);
				loop_def();
				setState(274);
				match(SCOPE_OPEN);
				setState(275);
				nl();
				setState(276);
				scope_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Loop_defContext extends ParserRuleContext {
		public Loop_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop_def; }

		public Loop_defContext() { }
		public void copyFrom(Loop_defContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class CondLoopContext extends Loop_defContext {
		public TerminalNode WHILE() { return getToken(simpleParser.WHILE, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public CondLoopContext(Loop_defContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterCondLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitCondLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitCondLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class QuantLoopContext extends Loop_defContext {
		public Direct_valueContext direct_value() {
			return getRuleContext(Direct_valueContext.class,0);
		}
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public TerminalNode TIMES() { return getToken(simpleParser.TIMES, 0); }
		public QuantLoopContext(Loop_defContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterQuantLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitQuantLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitQuantLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Loop_defContext loop_def() throws RecognitionException {
		Loop_defContext _localctx = new Loop_defContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_loop_def);
		try {
			setState(285);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case WHILE:
					_localctx = new CondLoopContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(278);
					match(WHILE);
					setState(279);
					match(S);
					setState(280);
					condition();
				}
				break;
				case Text:
				case Boolean:
				case NAME:
				case NUM:
				case NULL:
				case SEQ_OPEN:
				case LST_DELIM:
				case KIT_OPEN:
				case SUB:
					_localctx = new QuantLoopContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(281);
					direct_value();
					setState(282);
					match(S);
					setState(283);
					match(TIMES);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public Logic_opContext logic_op() {
			return getRuleContext(Logic_opContext.class,0);
		}
		public Compare_opContext compare_op() {
			return getRuleContext(Compare_opContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_condition);
		try {
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(287);
					logic_op();
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(288);
					compare_op();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(simpleParser.RETURN, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ReturnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitReturn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitReturn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnContext return_() throws RecognitionException {
		ReturnContext _localctx = new ReturnContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_return);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(291);
				match(RETURN);
				setState(292);
				match(S);
				setState(293);
				value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstructionContext extends ParserRuleContext {
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_instruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(297);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case PREPARE:
					{
						setState(295);
						var_decl();
					}
					break;
					case SET:
					case INSERT:
					case REMOVE:
					case SPLIT:
					case MERGE:
					case CALL:
					{
						setState(296);
						expr();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_declContext extends ParserRuleContext {
		public Var_defContext var_def() {
			return getRuleContext(Var_defContext.class,0);
		}
		public Var_initContext var_init() {
			return getRuleContext(Var_initContext.class,0);
		}
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterVar_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitVar_decl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitVar_decl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_var_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(299);
				var_def();
				setState(301);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==S) {
					{
						setState(300);
						var_init();
					}
				}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_defContext extends ParserRuleContext {
		public TerminalNode PREPARE() { return getToken(simpleParser.PREPARE, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode NAMED() { return getToken(simpleParser.NAMED, 0); }
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public Var_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterVar_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitVar_def(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitVar_def(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_defContext var_def() throws RecognitionException {
		Var_defContext _localctx = new Var_defContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_var_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(303);
				match(PREPARE);
				setState(304);
				match(S);
				setState(305);
				type();
				setState(306);
				match(S);
				setState(307);
				match(NAMED);
				setState(308);
				match(S);
				setState(309);
				match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_initContext extends ParserRuleContext {
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode VALUED() { return getToken(simpleParser.VALUED, 0); }
		public Direct_valueContext direct_value() {
			return getRuleContext(Direct_valueContext.class,0);
		}
		public ReservedContext reserved() {
			return getRuleContext(ReservedContext.class,0);
		}
		public Var_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterVar_init(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitVar_init(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitVar_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_initContext var_init() throws RecognitionException {
		Var_initContext _localctx = new Var_initContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_var_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(311);
				match(S);
				setState(312);
				match(VALUED);
				setState(313);
				match(S);
				setState(316);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case Text:
					case Boolean:
					case NAME:
					case NUM:
					case NULL:
					case SEQ_OPEN:
					case LST_DELIM:
					case KIT_OPEN:
					case SUB:
					{
						setState(314);
						direct_value();
					}
					break;
					case COMMA:
					case SCOPE_CLOSE:
					case COUNTER:
					case SIZEOF:
					case RANDOM:
					case DOT:
					{
						setState(315);
						reserved();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public Fun_callContext fun_call() {
			return getRuleContext(Fun_callContext.class,0);
		}
		public List_opContext list_op() {
			return getRuleContext(List_opContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_expr);
		try {
			setState(321);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case SET:
					enterOuterAlt(_localctx, 1);
				{
					setState(318);
					assignment();
				}
				break;
				case CALL:
					enterOuterAlt(_localctx, 2);
				{
					setState(319);
					fun_call();
				}
				break;
				case INSERT:
				case REMOVE:
				case SPLIT:
				case MERGE:
					enterOuterAlt(_localctx, 3);
				{
					setState(320);
					list_op();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(simpleParser.SET, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode TO() { return getToken(simpleParser.TO, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode OUTP() { return getToken(simpleParser.OUTP, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ReservedContext reserved() {
			return getRuleContext(ReservedContext.class,0);
		}
		public TerminalNode INP() { return getToken(simpleParser.INP, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(323);
				match(SET);
				setState(324);
				match(S);
				setState(327);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case NAME:
					{
						setState(325);
						variable();
					}
					break;
					case OUTP:
					{
						setState(326);
						match(OUTP);
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
				setState(329);
				match(S);
				setState(330);
				match(TO);
				setState(331);
				match(S);
				setState(335);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case Text:
					case Boolean:
					case NAME:
					case NUM:
					case EXP_OPEN:
					case NULL:
					case SEQ_OPEN:
					case LST_DELIM:
					case KIT_OPEN:
					case SUB:
					{
						setState(332);
						value();
					}
					break;
					case COMMA:
					case SCOPE_CLOSE:
					case COUNTER:
					case SIZEOF:
					case RANDOM:
					{
						setState(333);
						reserved();
					}
					break;
					case INP:
					{
						setState(334);
						match(INP);
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_opContext extends ParserRuleContext {
		public List_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_op; }

		public List_opContext() { }
		public void copyFrom(List_opContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class List2TextContext extends List_opContext {
		public TerminalNode MERGE() { return getToken(simpleParser.MERGE, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public TerminalNode IN() { return getToken(simpleParser.IN, 0); }
		public List2TextContext(List_opContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterList2Text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitList2Text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitList2Text(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ListRemovalContext extends List_opContext {
		public TerminalNode REMOVE() { return getToken(simpleParser.REMOVE, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public Struct_accessContext struct_access() {
			return getRuleContext(Struct_accessContext.class,0);
		}
		public ListRemovalContext(List_opContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterListRemoval(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitListRemoval(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitListRemoval(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Text2ListContext extends List_opContext {
		public TerminalNode SPLIT() { return getToken(simpleParser.SPLIT, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public TerminalNode IN() { return getToken(simpleParser.IN, 0); }
		public Text2ListContext(List_opContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterText2List(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitText2List(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitText2List(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class ListInsertionContext extends List_opContext {
		public TerminalNode INSERT() { return getToken(simpleParser.INSERT, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public Direct_valueContext direct_value() {
			return getRuleContext(Direct_valueContext.class,0);
		}
		public TerminalNode IN() { return getToken(simpleParser.IN, 0); }
		public Struct_accessContext struct_access() {
			return getRuleContext(Struct_accessContext.class,0);
		}
		public ListInsertionContext(List_opContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterListInsertion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitListInsertion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitListInsertion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final List_opContext list_op() throws RecognitionException {
		List_opContext _localctx = new List_opContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_list_op);
		try {
			setState(364);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case INSERT:
					_localctx = new ListInsertionContext(_localctx);
					enterOuterAlt(_localctx, 1);
				{
					setState(337);
					match(INSERT);
					setState(338);
					match(S);
					setState(339);
					direct_value();
					setState(340);
					match(S);
					setState(341);
					match(IN);
					setState(342);
					match(S);
					setState(343);
					struct_access();
				}
				break;
				case REMOVE:
					_localctx = new ListRemovalContext(_localctx);
					enterOuterAlt(_localctx, 2);
				{
					setState(345);
					match(REMOVE);
					setState(346);
					match(S);
					setState(347);
					struct_access();
				}
				break;
				case SPLIT:
					_localctx = new Text2ListContext(_localctx);
					enterOuterAlt(_localctx, 3);
				{
					setState(348);
					match(SPLIT);
					setState(349);
					match(S);
					setState(350);
					variable();
					setState(351);
					match(S);
					setState(352);
					match(IN);
					setState(353);
					match(S);
					setState(354);
					variable();
				}
				break;
				case MERGE:
					_localctx = new List2TextContext(_localctx);
					enterOuterAlt(_localctx, 4);
				{
					setState(356);
					match(MERGE);
					setState(357);
					match(S);
					setState(358);
					variable();
					setState(359);
					match(S);
					setState(360);
					match(IN);
					setState(361);
					match(S);
					setState(362);
					variable();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public Direct_valueContext direct_value() {
			return getRuleContext(Direct_valueContext.class,0);
		}
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public TerminalNode EXP_OPEN() { return getToken(simpleParser.EXP_OPEN, 0); }
		public Fun_callContext fun_call() {
			return getRuleContext(Fun_callContext.class,0);
		}
		public TerminalNode EXP_CLOSE() { return getToken(simpleParser.EXP_CLOSE, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_value);
		try {
			setState(372);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(366);
					direct_value();
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(367);
					operation();
				}
				break;
				case 3:
					enterOuterAlt(_localctx, 3);
				{
					setState(368);
					match(EXP_OPEN);
					setState(369);
					fun_call();
					setState(370);
					match(EXP_CLOSE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Direct_valueContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public Direct_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_direct_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterDirect_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitDirect_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitDirect_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Direct_valueContext direct_value() throws RecognitionException {
		Direct_valueContext _localctx = new Direct_valueContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_direct_value);
		try {
			setState(376);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case NAME:
					enterOuterAlt(_localctx, 1);
				{
					setState(374);
					variable();
				}
				break;
				case Text:
				case Boolean:
				case NUM:
				case NULL:
				case SEQ_OPEN:
				case LST_DELIM:
				case KIT_OPEN:
				case SUB:
					enterOuterAlt(_localctx, 2);
				{
					setState(375);
					literal();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public Var_nameContext var_name() {
			return getRuleContext(Var_nameContext.class,0);
		}
		public Struct_accessContext struct_access() {
			return getRuleContext(Struct_accessContext.class,0);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_variable);
		try {
			setState(380);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(378);
					var_name();
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(379);
					struct_access();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Var_nameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public Var_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterVar_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitVar_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitVar_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_nameContext var_name() throws RecognitionException {
		Var_nameContext _localctx = new Var_nameContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_var_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(382);
				match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Struct_accessContext extends ParserRuleContext {
		public Var_nameContext var_name() {
			return getRuleContext(Var_nameContext.class,0);
		}
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode POSITION() { return getToken(simpleParser.POSITION, 0); }
		public AccessContext access() {
			return getRuleContext(AccessContext.class,0);
		}
		public Struct_accessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_access; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterStruct_access(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitStruct_access(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitStruct_access(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_accessContext struct_access() throws RecognitionException {
		Struct_accessContext _localctx = new Struct_accessContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_struct_access);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(384);
				var_name();
				setState(385);
				match(S);
				setState(386);
				match(POSITION);
				setState(387);
				match(S);
				setState(388);
				access();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AccessContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(simpleParser.NUM, 0); }
		public TerminalNode AT_FIELD() { return getToken(simpleParser.AT_FIELD, 0); }
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public Var_nameContext var_name() {
			return getRuleContext(Var_nameContext.class,0);
		}
		public ReservedContext reserved() {
			return getRuleContext(ReservedContext.class,0);
		}
		public AccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_access; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccessContext access() throws RecognitionException {
		AccessContext _localctx = new AccessContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_access);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(395);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case NUM:
					{
						setState(390);
						match(NUM);
					}
					break;
					case AT_FIELD:
					{
						setState(391);
						match(AT_FIELD);
						setState(392);
						match(NAME);
					}
					break;
					case NAME:
					{
						setState(393);
						var_name();
					}
					break;
					case S:
					case SEPAR:
					case COMMA:
					case SCOPE_CLOSE:
					case EXP_CLOSE:
					case COUNTER:
					case SIZEOF:
					case RANDOM:
					case DOT:
					{
						setState(394);
						reserved();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReservedContext extends ParserRuleContext {
		public TerminalNode COUNTER() { return getToken(simpleParser.COUNTER, 0); }
		public TerminalNode SIZEOF() { return getToken(simpleParser.SIZEOF, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public Var_nameContext var_name() {
			return getRuleContext(Var_nameContext.class,0);
		}
		public TerminalNode RANDOM() { return getToken(simpleParser.RANDOM, 0); }
		public ReservedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reserved; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterReserved(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitReserved(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitReserved(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReservedContext reserved() throws RecognitionException {
		ReservedContext _localctx = new ReservedContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_reserved);
		try {
			setState(403);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case S:
				case SEPAR:
				case COMMA:
				case SCOPE_CLOSE:
				case EXP_CLOSE:
				case DOT:
					enterOuterAlt(_localctx, 1);
				{
				}
				break;
				case COUNTER:
					enterOuterAlt(_localctx, 2);
				{
					setState(398);
					match(COUNTER);
				}
				break;
				case SIZEOF:
					enterOuterAlt(_localctx, 3);
				{
					setState(399);
					match(SIZEOF);
					setState(400);
					match(S);
					setState(401);
					var_name();
				}
				break;
				case RANDOM:
					enterOuterAlt(_localctx, 4);
				{
					setState(402);
					match(RANDOM);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode Text() { return getToken(simpleParser.Text, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public TerminalNode Boolean() { return getToken(simpleParser.Boolean, 0); }
		public TerminalNode NULL() { return getToken(simpleParser.NULL, 0); }
		public Struct_litContext struct_lit() {
			return getRuleContext(Struct_litContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_literal);
		try {
			setState(410);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case Text:
					enterOuterAlt(_localctx, 1);
				{
					setState(405);
					match(Text);
				}
				break;
				case NUM:
				case SUB:
					enterOuterAlt(_localctx, 2);
				{
					setState(406);
					number();
				}
				break;
				case Boolean:
					enterOuterAlt(_localctx, 3);
				{
					setState(407);
					match(Boolean);
				}
				break;
				case NULL:
					enterOuterAlt(_localctx, 4);
				{
					setState(408);
					match(NULL);
				}
				break;
				case SEQ_OPEN:
				case LST_DELIM:
				case KIT_OPEN:
					enterOuterAlt(_localctx, 5);
				{
					setState(409);
					struct_lit();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> NUM() { return getTokens(simpleParser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(simpleParser.NUM, i);
		}
		public TerminalNode SUB() { return getToken(simpleParser.SUB, 0); }
		public TerminalNode DOT() { return getToken(simpleParser.DOT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(413);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SUB) {
					{
						setState(412);
						match(SUB);
					}
				}

				setState(415);
				match(NUM);
				setState(418);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
					case 1:
					{
						setState(416);
						match(DOT);
						setState(417);
						match(NUM);
					}
					break;
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Struct_litContext extends ParserRuleContext {
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public KitContext kit() {
			return getRuleContext(KitContext.class,0);
		}
		public Struct_litContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_lit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterStruct_lit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitStruct_lit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitStruct_lit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_litContext struct_lit() throws RecognitionException {
		Struct_litContext _localctx = new Struct_litContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_struct_lit);
		try {
			setState(423);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case SEQ_OPEN:
					enterOuterAlt(_localctx, 1);
				{
					setState(420);
					sequence();
				}
				break;
				case LST_DELIM:
					enterOuterAlt(_localctx, 2);
				{
					setState(421);
					list();
				}
				break;
				case KIT_OPEN:
					enterOuterAlt(_localctx, 3);
				{
					setState(422);
					kit();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SequenceContext extends ParserRuleContext {
		public TerminalNode SEQ_OPEN() { return getToken(simpleParser.SEQ_OPEN, 0); }
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public TerminalNode SEQ_CLOSE() { return getToken(simpleParser.SEQ_CLOSE, 0); }
		public List<TerminalNode> SEPAR() { return getTokens(simpleParser.SEPAR); }
		public TerminalNode SEPAR(int i) {
			return getToken(simpleParser.SEPAR, i);
		}
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SequenceContext sequence() throws RecognitionException {
		SequenceContext _localctx = new SequenceContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_sequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(425);
				match(SEQ_OPEN);
				setState(426);
				literal();
				setState(431);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPAR) {
					{
						{
							setState(427);
							match(SEPAR);
							setState(428);
							literal();
						}
					}
					setState(433);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(434);
				match(SEQ_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public List<TerminalNode> LST_DELIM() { return getTokens(simpleParser.LST_DELIM); }
		public TerminalNode LST_DELIM(int i) {
			return getToken(simpleParser.LST_DELIM, i);
		}
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public List<TerminalNode> SEPAR_ALT() { return getTokens(simpleParser.SEPAR_ALT); }
		public TerminalNode SEPAR_ALT(int i) {
			return getToken(simpleParser.SEPAR_ALT, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(436);
				match(LST_DELIM);
				setState(445);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
					case 1:
					{
						setState(437);
						literal();
						setState(442);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SEPAR_ALT) {
							{
								{
									setState(438);
									match(SEPAR_ALT);
									setState(439);
									literal();
								}
							}
							setState(444);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
					}
					break;
				}
				setState(447);
				match(LST_DELIM);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KitContext extends ParserRuleContext {
		public TerminalNode KIT_OPEN() { return getToken(simpleParser.KIT_OPEN, 0); }
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public TerminalNode KIT_CLOSE() { return getToken(simpleParser.KIT_CLOSE, 0); }
		public List<TerminalNode> SEPAR() { return getTokens(simpleParser.SEPAR); }
		public TerminalNode SEPAR(int i) {
			return getToken(simpleParser.SEPAR, i);
		}
		public KitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterKit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitKit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitKit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KitContext kit() throws RecognitionException {
		KitContext _localctx = new KitContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_kit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(449);
				match(KIT_OPEN);
				setState(450);
				literal();
				setState(455);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPAR) {
					{
						{
							setState(451);
							match(SEPAR);
							setState(452);
							literal();
						}
					}
					setState(457);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(458);
				match(KIT_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperationContext extends ParserRuleContext {
		public Arith_opContext arith_op() {
			return getRuleContext(Arith_opContext.class,0);
		}
		public Logic_opContext logic_op() {
			return getRuleContext(Logic_opContext.class,0);
		}
		public Compare_opContext compare_op() {
			return getRuleContext(Compare_opContext.class,0);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_operation);
		try {
			setState(463);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(460);
					arith_op();
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(461);
					logic_op();
				}
				break;
				case 3:
					enterOuterAlt(_localctx, 3);
				{
					setState(462);
					compare_op();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arith_opContext extends ParserRuleContext {
		public Token opr;
		public TerminalNode EXP_OPEN() { return getToken(simpleParser.EXP_OPEN, 0); }
		public List<Ar_oprndContext> ar_oprnd() {
			return getRuleContexts(Ar_oprndContext.class);
		}
		public Ar_oprndContext ar_oprnd(int i) {
			return getRuleContext(Ar_oprndContext.class,i);
		}
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode EXP_CLOSE() { return getToken(simpleParser.EXP_CLOSE, 0); }
		public TerminalNode ADD() { return getToken(simpleParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(simpleParser.SUB, 0); }
		public TerminalNode MULT() { return getToken(simpleParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(simpleParser.DIV, 0); }
		public TerminalNode MODULUS() { return getToken(simpleParser.MODULUS, 0); }
		public Arith_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arith_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterArith_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitArith_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitArith_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arith_opContext arith_op() throws RecognitionException {
		Arith_opContext _localctx = new Arith_opContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_arith_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(465);
				match(EXP_OPEN);
				setState(466);
				ar_oprnd();
				setState(467);
				match(S);
				setState(468);
				((Arith_opContext)_localctx).opr = _input.LT(1);
				_la = _input.LA(1);
				if ( !(((((_la - 61)) & ~0x3f) == 0 && ((1L << (_la - 61)) & 31L) != 0)) ) {
					((Arith_opContext)_localctx).opr = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(469);
				match(S);
				setState(470);
				ar_oprnd();
				setState(471);
				match(EXP_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ar_oprndContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ReservedContext reserved() {
			return getRuleContext(ReservedContext.class,0);
		}
		public Arith_opContext arith_op() {
			return getRuleContext(Arith_opContext.class,0);
		}
		public Ar_oprndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ar_oprnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterAr_oprnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitAr_oprnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitAr_oprnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ar_oprndContext ar_oprnd() throws RecognitionException {
		Ar_oprndContext _localctx = new Ar_oprndContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_ar_oprnd);
		try {
			setState(477);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case NAME:
					enterOuterAlt(_localctx, 1);
				{
					setState(473);
					variable();
				}
				break;
				case NUM:
				case SUB:
					enterOuterAlt(_localctx, 2);
				{
					setState(474);
					number();
				}
				break;
				case S:
				case EXP_CLOSE:
				case COUNTER:
				case SIZEOF:
				case RANDOM:
					enterOuterAlt(_localctx, 3);
				{
					setState(475);
					reserved();
				}
				break;
				case EXP_OPEN:
					enterOuterAlt(_localctx, 4);
				{
					setState(476);
					arith_op();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logic_opContext extends ParserRuleContext {
		public TerminalNode EXP_OPEN() { return getToken(simpleParser.EXP_OPEN, 0); }
		public TerminalNode EXP_CLOSE() { return getToken(simpleParser.EXP_CLOSE, 0); }
		public List<Log_oprndContext> log_oprnd() {
			return getRuleContexts(Log_oprndContext.class);
		}
		public Log_oprndContext log_oprnd(int i) {
			return getRuleContext(Log_oprndContext.class,i);
		}
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode NOT() { return getToken(simpleParser.NOT, 0); }
		public TerminalNode AND() { return getToken(simpleParser.AND, 0); }
		public TerminalNode OR() { return getToken(simpleParser.OR, 0); }
		public Logic_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterLogic_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitLogic_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitLogic_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_opContext logic_op() throws RecognitionException {
		Logic_opContext _localctx = new Logic_opContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_logic_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(479);
				match(EXP_OPEN);
				setState(489);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
					case Boolean:
					case NAME:
					case EXP_OPEN:
					{
						setState(480);
						log_oprnd();
						setState(481);
						match(S);
						setState(482);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(483);
						match(S);
						setState(484);
						log_oprnd();
					}
					break;
					case NOT:
					{
						setState(486);
						match(NOT);
						setState(487);
						match(S);
						setState(488);
						log_oprnd();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
				setState(491);
				match(EXP_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Log_oprndContext extends ParserRuleContext {
		public TerminalNode Boolean() { return getToken(simpleParser.Boolean, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public Compare_opContext compare_op() {
			return getRuleContext(Compare_opContext.class,0);
		}
		public Logic_opContext logic_op() {
			return getRuleContext(Logic_opContext.class,0);
		}
		public Log_oprndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_log_oprnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterLog_oprnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitLog_oprnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitLog_oprnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Log_oprndContext log_oprnd() throws RecognitionException {
		Log_oprndContext _localctx = new Log_oprndContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_log_oprnd);
		try {
			setState(497);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(493);
					match(Boolean);
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(494);
					variable();
				}
				break;
				case 3:
					enterOuterAlt(_localctx, 3);
				{
					setState(495);
					compare_op();
				}
				break;
				case 4:
					enterOuterAlt(_localctx, 4);
				{
					setState(496);
					logic_op();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Compare_opContext extends ParserRuleContext {
		public TerminalNode EXP_OPEN() { return getToken(simpleParser.EXP_OPEN, 0); }
		public TerminalNode EXP_CLOSE() { return getToken(simpleParser.EXP_CLOSE, 0); }
		public List<Ar_oprndContext> ar_oprnd() {
			return getRuleContexts(Ar_oprndContext.class);
		}
		public Ar_oprndContext ar_oprnd(int i) {
			return getRuleContext(Ar_oprndContext.class,i);
		}
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public List<Comp_oprndContext> comp_oprnd() {
			return getRuleContexts(Comp_oprndContext.class);
		}
		public Comp_oprndContext comp_oprnd(int i) {
			return getRuleContext(Comp_oprndContext.class,i);
		}
		public TerminalNode EQ() { return getToken(simpleParser.EQ, 0); }
		public TerminalNode GT() { return getToken(simpleParser.GT, 0); }
		public TerminalNode LT() { return getToken(simpleParser.LT, 0); }
		public Compare_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterCompare_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitCompare_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitCompare_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_opContext compare_op() throws RecognitionException {
		Compare_opContext _localctx = new Compare_opContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_compare_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(499);
				match(EXP_OPEN);
				setState(512);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
					case 1:
					{
						setState(500);
						ar_oprnd();
						setState(501);
						match(S);
						setState(502);
						_la = _input.LA(1);
						if ( !(_la==GT || _la==LT) ) {
							_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(503);
						match(S);
						setState(504);
						ar_oprnd();
					}
					break;
					case 2:
					{
						setState(506);
						comp_oprnd();
						setState(507);
						match(S);
						setState(508);
						match(EQ);
						setState(509);
						match(S);
						setState(510);
						comp_oprnd();
					}
					break;
				}
				setState(514);
				match(EXP_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Comp_oprndContext extends ParserRuleContext {
		public Direct_valueContext direct_value() {
			return getRuleContext(Direct_valueContext.class,0);
		}
		public ReservedContext reserved() {
			return getRuleContext(ReservedContext.class,0);
		}
		public Arith_opContext arith_op() {
			return getRuleContext(Arith_opContext.class,0);
		}
		public Comp_oprndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_oprnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterComp_oprnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitComp_oprnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitComp_oprnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comp_oprndContext comp_oprnd() throws RecognitionException {
		Comp_oprndContext _localctx = new Comp_oprndContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_comp_oprnd);
		try {
			setState(519);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case Text:
				case Boolean:
				case NAME:
				case NUM:
				case NULL:
				case SEQ_OPEN:
				case LST_DELIM:
				case KIT_OPEN:
				case SUB:
					enterOuterAlt(_localctx, 1);
				{
					setState(516);
					direct_value();
				}
				break;
				case S:
				case EXP_CLOSE:
				case COUNTER:
				case SIZEOF:
				case RANDOM:
					enterOuterAlt(_localctx, 2);
				{
					setState(517);
					reserved();
				}
				break;
				case EXP_OPEN:
					enterOuterAlt(_localctx, 3);
				{
					setState(518);
					arith_op();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_callContext extends ParserRuleContext {
		public TerminalNode CALL() { return getToken(simpleParser.CALL, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public Fun_nameContext fun_name() {
			return getRuleContext(Fun_nameContext.class,0);
		}
		public TerminalNode ARGUMENTS() { return getToken(simpleParser.ARGUMENTS, 0); }
		public List<Direct_valueContext> direct_value() {
			return getRuleContexts(Direct_valueContext.class);
		}
		public Direct_valueContext direct_value(int i) {
			return getRuleContext(Direct_valueContext.class,i);
		}
		public List<TerminalNode> SEPAR() { return getTokens(simpleParser.SEPAR); }
		public TerminalNode SEPAR(int i) {
			return getToken(simpleParser.SEPAR, i);
		}
		public Fun_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFun_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFun_call(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFun_call(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_callContext fun_call() throws RecognitionException {
		Fun_callContext _localctx = new Fun_callContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_fun_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(521);
				match(CALL);
				setState(522);
				match(S);
				setState(523);
				fun_name();
				setState(535);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==S) {
					{
						setState(524);
						match(S);
						setState(525);
						match(ARGUMENTS);
						setState(526);
						match(S);
						setState(527);
						direct_value();
						setState(532);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SEPAR) {
							{
								{
									setState(528);
									match(SEPAR);
									setState(529);
									direct_value();
								}
							}
							setState(534);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
					}
				}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fun_nameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public Fun_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterFun_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitFun_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitFun_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Fun_nameContext fun_name() throws RecognitionException {
		Fun_nameContext _localctx = new Fun_nameContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_fun_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(537);
				match(NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(simpleParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(simpleParser.STRING, 0); }
		public TerminalNode BOOL() { return getToken(simpleParser.BOOL, 0); }
		public Declared_typeContext declared_type() {
			return getRuleContext(Declared_typeContext.class,0);
		}
		public TerminalNode EXP_OPEN() { return getToken(simpleParser.EXP_OPEN, 0); }
		public Struct_typeContext struct_type() {
			return getRuleContext(Struct_typeContext.class,0);
		}
		public TerminalNode EXP_CLOSE() { return getToken(simpleParser.EXP_CLOSE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_type);
		try {
			setState(547);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case NUMBER:
					enterOuterAlt(_localctx, 1);
				{
					setState(539);
					match(NUMBER);
				}
				break;
				case STRING:
					enterOuterAlt(_localctx, 2);
				{
					setState(540);
					match(STRING);
				}
				break;
				case BOOL:
					enterOuterAlt(_localctx, 3);
				{
					setState(541);
					match(BOOL);
				}
				break;
				case TYPENAME:
					enterOuterAlt(_localctx, 4);
				{
					setState(542);
					declared_type();
				}
				break;
				case EXP_OPEN:
					enterOuterAlt(_localctx, 5);
				{
					setState(543);
					match(EXP_OPEN);
					setState(544);
					struct_type();
					setState(545);
					match(EXP_CLOSE);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Declared_typeContext extends ParserRuleContext {
		public TerminalNode TYPENAME() { return getToken(simpleParser.TYPENAME, 0); }
		public Declared_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declared_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterDeclared_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitDeclared_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitDeclared_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Declared_typeContext declared_type() throws RecognitionException {
		Declared_typeContext _localctx = new Declared_typeContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_declared_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(549);
				match(TYPENAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Struct_typeContext extends ParserRuleContext {
		public Seq_typeContext seq_type() {
			return getRuleContext(Seq_typeContext.class,0);
		}
		public List_typeContext list_type() {
			return getRuleContext(List_typeContext.class,0);
		}
		public Kit_typeContext kit_type() {
			return getRuleContext(Kit_typeContext.class,0);
		}
		public Struct_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterStruct_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitStruct_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitStruct_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_typeContext struct_type() throws RecognitionException {
		Struct_typeContext _localctx = new Struct_typeContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_struct_type);
		try {
			setState(554);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
				case SEQUENCE:
					enterOuterAlt(_localctx, 1);
				{
					setState(551);
					seq_type();
				}
				break;
				case LIST:
					enterOuterAlt(_localctx, 2);
				{
					setState(552);
					list_type();
				}
				break;
				case KIT:
					enterOuterAlt(_localctx, 3);
				{
					setState(553);
					kit_type();
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Seq_typeContext extends ParserRuleContext {
		public TerminalNode SEQUENCE() { return getToken(simpleParser.SEQUENCE, 0); }
		public List<TerminalNode> S() { return getTokens(simpleParser.S); }
		public TerminalNode S(int i) {
			return getToken(simpleParser.S, i);
		}
		public TerminalNode NUM() { return getToken(simpleParser.NUM, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Seq_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_seq_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterSeq_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitSeq_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitSeq_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Seq_typeContext seq_type() throws RecognitionException {
		Seq_typeContext _localctx = new Seq_typeContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_seq_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(556);
				match(SEQUENCE);
				setState(557);
				match(S);
				setState(558);
				match(NUM);
				setState(559);
				match(S);
				setState(560);
				type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_typeContext extends ParserRuleContext {
		public TerminalNode LIST() { return getToken(simpleParser.LIST, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterList_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitList_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitList_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final List_typeContext list_type() throws RecognitionException {
		List_typeContext _localctx = new List_typeContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_list_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(562);
				match(LIST);
				setState(563);
				match(S);
				setState(564);
				type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Kit_typeContext extends ParserRuleContext {
		public TerminalNode KIT() { return getToken(simpleParser.KIT, 0); }
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<TerminalNode> SEPAR() { return getTokens(simpleParser.SEPAR); }
		public TerminalNode SEPAR(int i) {
			return getToken(simpleParser.SEPAR, i);
		}
		public Kit_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kit_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterKit_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitKit_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitKit_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Kit_typeContext kit_type() throws RecognitionException {
		Kit_typeContext _localctx = new Kit_typeContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_kit_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(566);
				match(KIT);
				setState(567);
				match(S);
				setState(568);
				param();
				setState(571);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						{
							setState(569);
							match(SEPAR);
							setState(570);
							param();
						}
					}
					setState(573);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==SEPAR );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode S() { return getToken(simpleParser.S, 0); }
		public TerminalNode NAME() { return getToken(simpleParser.NAME, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof simpleListener ) ((simpleListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof simpleVisitor ) return ((simpleVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
				{
					setState(575);
					type();
					setState(576);
					match(S);
					setState(577);
					match(NAME);
				}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	private final String _serializedATN =
		"\u0004\u0001R\u0244\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u0002"+
		"2\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u0002"+
		"7\u00077\u0001\u0000\u0001\u0000\u0003\u0000s\b\u0000\u0001\u0000\u0004"+
		"\u0000v\b\u0000\u000b\u0000\f\u0000w\u0004\u0000z\b\u0000\u000b\u0000"+
		"\f\u0000{\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001"+
		"\u0082\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0087\b"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u00a5"+
		"\b\u0004\n\u0004\f\u0004\u00a8\t\u0004\u0003\u0004\u00aa\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u00b1"+
		"\b\u0004\n\u0004\f\u0004\u00b4\t\u0004\u0003\u0004\u00b6\b\u0004\u0003"+
		"\u0004\u00b8\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0005\u0005\u00bf\b\u0005\n\u0005\f\u0005\u00c2\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u00ce\b\u0006\n\u0006"+
		"\f\u0006\u00d1\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u00df\b\t\u0001\n\u0001\n\u0003\n\u00e3\b\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0003\n\u00e9\b\n\u0001\u000b\u0001\u000b\u0003\u000b\u00ed\b"+
		"\u000b\u0001\f\u0001\f\u0005\f\u00f1\b\f\n\f\f\f\u00f4\t\f\u0001\f\u0003"+
		"\f\u00f7\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011"+
		"\u011e\b\u0011\u0001\u0012\u0001\u0012\u0003\u0012\u0122\b\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0003"+
		"\u0014\u012a\b\u0014\u0001\u0015\u0001\u0015\u0003\u0015\u012e\b\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0003\u0017\u013d\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0003\u0018\u0142\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u0148\b\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0003\u0019\u0150\b\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0003\u001a\u016d\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0175\b\u001b\u0001\u001c"+
		"\u0001\u001c\u0003\u001c\u0179\b\u001c\u0001\u001d\u0001\u001d\u0003\u001d"+
		"\u017d\b\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0001 \u0001"+
		" \u0003 \u018c\b \u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003!\u0194"+
		"\b!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0003\"\u019b\b\"\u0001#\u0003"+
		"#\u019e\b#\u0001#\u0001#\u0001#\u0003#\u01a3\b#\u0001$\u0001$\u0001$\u0003"+
		"$\u01a8\b$\u0001%\u0001%\u0001%\u0001%\u0005%\u01ae\b%\n%\f%\u01b1\t%"+
		"\u0001%\u0001%\u0001&\u0001&\u0001&\u0001&\u0005&\u01b9\b&\n&\f&\u01bc"+
		"\t&\u0003&\u01be\b&\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0005"+
		"\'\u01c6\b\'\n\'\f\'\u01c9\t\'\u0001\'\u0001\'\u0001(\u0001(\u0001(\u0003"+
		"(\u01d0\b(\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001"+
		"*\u0001*\u0001*\u0001*\u0003*\u01de\b*\u0001+\u0001+\u0001+\u0001+\u0001"+
		"+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003+\u01ea\b+\u0001+\u0001+\u0001"+
		",\u0001,\u0001,\u0001,\u0003,\u01f2\b,\u0001-\u0001-\u0001-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0003-\u0201"+
		"\b-\u0001-\u0001-\u0001.\u0001.\u0001.\u0003.\u0208\b.\u0001/\u0001/\u0001"+
		"/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0005/\u0213\b/\n/\f/\u0216"+
		"\t/\u0003/\u0218\b/\u00010\u00010\u00011\u00011\u00011\u00011\u00011\u0001"+
		"1\u00011\u00011\u00031\u0224\b1\u00012\u00012\u00013\u00013\u00013\u0003"+
		"3\u022b\b3\u00014\u00014\u00014\u00014\u00014\u00014\u00015\u00015\u0001"+
		"5\u00015\u00016\u00016\u00016\u00016\u00016\u00046\u023c\b6\u000b6\f6"+
		"\u023d\u00017\u00017\u00017\u00017\u00017\u0000\u00008\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjln\u0000\u0004\u0002\u0000\u0004"+
		"\u0004\u000b\u000b\u0001\u0000=A\u0001\u0000BC\u0001\u0000EF\u0257\u0000"+
		"y\u0001\u0000\u0000\u0000\u0002\u0086\u0001\u0000\u0000\u0000\u0004\u0088"+
		"\u0001\u0000\u0000\u0000\u0006\u0092\u0001\u0000\u0000\u0000\b\u0097\u0001"+
		"\u0000\u0000\u0000\n\u00b9\u0001\u0000\u0000\u0000\f\u00c8\u0001\u0000"+
		"\u0000\u0000\u000e\u00d5\u0001\u0000\u0000\u0000\u0010\u00d7\u0001\u0000"+
		"\u0000\u0000\u0012\u00de\u0001\u0000\u0000\u0000\u0014\u00e8\u0001\u0000"+
		"\u0000\u0000\u0016\u00ec\u0001\u0000\u0000\u0000\u0018\u00ee\u0001\u0000"+
		"\u0000\u0000\u001a\u00f8\u0001\u0000\u0000\u0000\u001c\u0101\u0001\u0000"+
		"\u0000\u0000\u001e\u010a\u0001\u0000\u0000\u0000 \u010f\u0001\u0000\u0000"+
		"\u0000\"\u011d\u0001\u0000\u0000\u0000$\u0121\u0001\u0000\u0000\u0000"+
		"&\u0123\u0001\u0000\u0000\u0000(\u0129\u0001\u0000\u0000\u0000*\u012b"+
		"\u0001\u0000\u0000\u0000,\u012f\u0001\u0000\u0000\u0000.\u0137\u0001\u0000"+
		"\u0000\u00000\u0141\u0001\u0000\u0000\u00002\u0143\u0001\u0000\u0000\u0000"+
		"4\u016c\u0001\u0000\u0000\u00006\u0174\u0001\u0000\u0000\u00008\u0178"+
		"\u0001\u0000\u0000\u0000:\u017c\u0001\u0000\u0000\u0000<\u017e\u0001\u0000"+
		"\u0000\u0000>\u0180\u0001\u0000\u0000\u0000@\u018b\u0001\u0000\u0000\u0000"+
		"B\u0193\u0001\u0000\u0000\u0000D\u019a\u0001\u0000\u0000\u0000F\u019d"+
		"\u0001\u0000\u0000\u0000H\u01a7\u0001\u0000\u0000\u0000J\u01a9\u0001\u0000"+
		"\u0000\u0000L\u01b4\u0001\u0000\u0000\u0000N\u01c1\u0001\u0000\u0000\u0000"+
		"P\u01cf\u0001\u0000\u0000\u0000R\u01d1\u0001\u0000\u0000\u0000T\u01dd"+
		"\u0001\u0000\u0000\u0000V\u01df\u0001\u0000\u0000\u0000X\u01f1\u0001\u0000"+
		"\u0000\u0000Z\u01f3\u0001\u0000\u0000\u0000\\\u0207\u0001\u0000\u0000"+
		"\u0000^\u0209\u0001\u0000\u0000\u0000`\u0219\u0001\u0000\u0000\u0000b"+
		"\u0223\u0001\u0000\u0000\u0000d\u0225\u0001\u0000\u0000\u0000f\u022a\u0001"+
		"\u0000\u0000\u0000h\u022c\u0001\u0000\u0000\u0000j\u0232\u0001\u0000\u0000"+
		"\u0000l\u0236\u0001\u0000\u0000\u0000n\u023f\u0001\u0000\u0000\u0000p"+
		"s\u0003\u000e\u0007\u0000qs\u0003\u0002\u0001\u0000rp\u0001\u0000\u0000"+
		"\u0000rq\u0001\u0000\u0000\u0000su\u0001\u0000\u0000\u0000tv\u0003\u0010"+
		"\b\u0000ut\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wu\u0001\u0000"+
		"\u0000\u0000wx\u0001\u0000\u0000\u0000xz\u0001\u0000\u0000\u0000yr\u0001"+
		"\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000"+
		"{|\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}~\u0005\u0000\u0000"+
		"\u0001~\u0001\u0001\u0000\u0000\u0000\u007f\u0082\u0003*\u0015\u0000\u0080"+
		"\u0082\u0003\u0004\u0002\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081"+
		"\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u00056\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000\u0085\u0087"+
		"\u0003\u0006\u0003\u0000\u0086\u0081\u0001\u0000\u0000\u0000\u0086\u0085"+
		"\u0001\u0000\u0000\u0000\u0087\u0003\u0001\u0000\u0000\u0000\u0088\u0089"+
		"\u0005\u001a\u0000\u0000\u0089\u008a\u0005\u0007\u0000\u0000\u008a\u008b"+
		"\u0003b1\u0000\u008b\u008c\u0005\u0007\u0000\u0000\u008c\u008d\u0005\u001b"+
		"\u0000\u0000\u008d\u008e\u0005\u0007\u0000\u0000\u008e\u008f\u0005\u0003"+
		"\u0000\u0000\u008f\u0090\u0005\u0007\u0000\u0000\u0090\u0091\u0005\b\u0000"+
		"\u0000\u0091\u0005\u0001\u0000\u0000\u0000\u0092\u0093\u0003\b\u0004\u0000"+
		"\u0093\u0094\u0005\t\u0000\u0000\u0094\u0095\u0003\u0010\b\u0000\u0095"+
		"\u0096\u0003\n\u0005\u0000\u0096\u0007\u0001\u0000\u0000\u0000\u0097\u0098"+
		"\u0005\n\u0000\u0000\u0098\u0099\u0005\u0007\u0000\u0000\u0099\u00b7\u0007"+
		"\u0000\u0000\u0000\u009a\u00b5\u0005\u0007\u0000\u0000\u009b\u009c\u0005"+
		"\f\u0000\u0000\u009c\u009d\u0005\u0007\u0000\u0000\u009d\u00a9\u0003b"+
		"1\u0000\u009e\u009f\u0005\u0007\u0000\u0000\u009f\u00a0\u0005\r\u0000"+
		"\u0000\u00a0\u00a1\u0005\u0007\u0000\u0000\u00a1\u00a6\u0003n7\u0000\u00a2"+
		"\u00a3\u0005\u000e\u0000\u0000\u00a3\u00a5\u0003n7\u0000\u00a4\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a5\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a9\u009e\u0001"+
		"\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u00b6\u0001"+
		"\u0000\u0000\u0000\u00ab\u00ac\u0005\u000f\u0000\u0000\u00ac\u00ad\u0005"+
		"\u0007\u0000\u0000\u00ad\u00b2\u0003n7\u0000\u00ae\u00af\u0005\u000e\u0000"+
		"\u0000\u00af\u00b1\u0003n7\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1"+
		"\u00b4\u0001\u0000\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b2"+
		"\u00b3\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b5\u009b\u0001\u0000\u0000\u0000\u00b5"+
		"\u00ab\u0001\u0000\u0000\u0000\u00b6\u00b8\u0001\u0000\u0000\u0000\u00b7"+
		"\u009a\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8"+
		"\t\u0001\u0000\u0000\u0000\u00b9\u00c0\u0005O\u0000\u0000\u00ba\u00bb"+
		"\u0003\u000e\u0007\u0000\u00bb\u00bc\u0003\u0010\b\u0000\u00bc\u00bf\u0001"+
		"\u0000\u0000\u0000\u00bd\u00bf\u0003\u0012\t\u0000\u00be\u00ba\u0001\u0000"+
		"\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c2\u0001\u0000"+
		"\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c3\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c3\u00c4\u0003&\u0013\u0000\u00c4\u00c5\u00056\u0000\u0000"+
		"\u00c5\u00c6\u0003\u0010\b\u0000\u00c6\u00c7\u0005P\u0000\u0000\u00c7"+
		"\u000b\u0001\u0000\u0000\u0000\u00c8\u00cf\u0005O\u0000\u0000\u00c9\u00ca"+
		"\u0003\u000e\u0007\u0000\u00ca\u00cb\u0003\u0010\b\u0000\u00cb\u00ce\u0001"+
		"\u0000\u0000\u0000\u00cc\u00ce\u0003\u0012\t\u0000\u00cd\u00c9\u0001\u0000"+
		"\u0000\u0000\u00cd\u00cc\u0001\u0000\u0000\u0000\u00ce\u00d1\u0001\u0000"+
		"\u0000\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00cf\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0003\u0014\n\u0000\u00d3\u00d4\u0005P\u0000"+
		"\u0000\u00d4\r\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\u0006\u0000\u0000"+
		"\u00d6\u000f\u0001\u0000\u0000\u0000\u00d7\u00d8\u0005Q\u0000\u0000\u00d8"+
		"\u0011\u0001\u0000\u0000\u0000\u00d9\u00da\u0003(\u0014\u0000\u00da\u00db"+
		"\u0005\u0010\u0000\u0000\u00db\u00dc\u0003\u0010\b\u0000\u00dc\u00df\u0001"+
		"\u0000\u0000\u0000\u00dd\u00df\u0003\u0016\u000b\u0000\u00de\u00d9\u0001"+
		"\u0000\u0000\u0000\u00de\u00dd\u0001\u0000\u0000\u0000\u00df\u0013\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e3\u0003(\u0014\u0000\u00e1\u00e3\u0003&\u0013"+
		"\u0000\u00e2\u00e0\u0001\u0000\u0000\u0000\u00e2\u00e1\u0001\u0000\u0000"+
		"\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000\u00e4\u00e5\u0005\u0011\u0000"+
		"\u0000\u00e5\u00e6\u0003\u0010\b\u0000\u00e6\u00e9\u0001\u0000\u0000\u0000"+
		"\u00e7\u00e9\u0003\u0016\u000b\u0000\u00e8\u00e2\u0001\u0000\u0000\u0000"+
		"\u00e8\u00e7\u0001\u0000\u0000\u0000\u00e9\u0015\u0001\u0000\u0000\u0000"+
		"\u00ea\u00ed\u0003\u0018\f\u0000\u00eb\u00ed\u0003 \u0010\u0000\u00ec"+
		"\u00ea\u0001\u0000\u0000\u0000\u00ec\u00eb\u0001\u0000\u0000\u0000\u00ed"+
		"\u0017\u0001\u0000\u0000\u0000\u00ee\u00f2\u0003\u001a\r\u0000\u00ef\u00f1"+
		"\u0003\u001c\u000e\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000\u00f1\u00f4"+
		"\u0001\u0000\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f2\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f3\u00f6\u0001\u0000\u0000\u0000\u00f4\u00f2"+
		"\u0001\u0000\u0000\u0000\u00f5\u00f7\u0003\u001e\u000f\u0000\u00f6\u00f5"+
		"\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7\u0019"+
		"\u0001\u0000\u0000\u0000\u00f8\u00f9\u0005\u0012\u0000\u0000\u00f9\u00fa"+
		"\u0005\u0007\u0000\u0000\u00fa\u00fb\u0003$\u0012\u0000\u00fb\u00fc\u0005"+
		"\u0007\u0000\u0000\u00fc\u00fd\u0005\u0013\u0000\u0000\u00fd\u00fe\u0005"+
		"\t\u0000\u0000\u00fe\u00ff\u0003\u0010\b\u0000\u00ff\u0100\u0003\f\u0006"+
		"\u0000\u0100\u001b\u0001\u0000\u0000\u0000\u0101\u0102\u0005\u0014\u0000"+
		"\u0000\u0102\u0103\u0005\u0007\u0000\u0000\u0103\u0104\u0003$\u0012\u0000"+
		"\u0104\u0105\u0005\u0007\u0000\u0000\u0105\u0106\u0005\u0013\u0000\u0000"+
		"\u0106\u0107\u0005\t\u0000\u0000\u0107\u0108\u0003\u0010\b\u0000\u0108"+
		"\u0109\u0003\f\u0006\u0000\u0109\u001d\u0001\u0000\u0000\u0000\u010a\u010b"+
		"\u0005\u0015\u0000\u0000\u010b\u010c\u0005\t\u0000\u0000\u010c\u010d\u0003"+
		"\u0010\b\u0000\u010d\u010e\u0003\f\u0006\u0000\u010e\u001f\u0001\u0000"+
		"\u0000\u0000\u010f\u0110\u0005\u0016\u0000\u0000\u0110\u0111\u0005\u0007"+
		"\u0000\u0000\u0111\u0112\u0003\"\u0011\u0000\u0112\u0113\u0005\t\u0000"+
		"\u0000\u0113\u0114\u0003\u0010\b\u0000\u0114\u0115\u0003\f\u0006\u0000"+
		"\u0115!\u0001\u0000\u0000\u0000\u0116\u0117\u0005\u0017\u0000\u0000\u0117"+
		"\u0118\u0005\u0007\u0000\u0000\u0118\u011e\u0003$\u0012\u0000\u0119\u011a"+
		"\u00038\u001c\u0000\u011a\u011b\u0005\u0007\u0000\u0000\u011b\u011c\u0005"+
		"\u0018\u0000\u0000\u011c\u011e\u0001\u0000\u0000\u0000\u011d\u0116\u0001"+
		"\u0000\u0000\u0000\u011d\u0119\u0001\u0000\u0000\u0000\u011e#\u0001\u0000"+
		"\u0000\u0000\u011f\u0122\u0003V+\u0000\u0120\u0122\u0003Z-\u0000\u0121"+
		"\u011f\u0001\u0000\u0000\u0000\u0121\u0120\u0001\u0000\u0000\u0000\u0122"+
		"%\u0001\u0000\u0000\u0000\u0123\u0124\u0005\u0019\u0000\u0000\u0124\u0125"+
		"\u0005\u0007\u0000\u0000\u0125\u0126\u00036\u001b\u0000\u0126\'\u0001"+
		"\u0000\u0000\u0000\u0127\u012a\u0003*\u0015\u0000\u0128\u012a\u00030\u0018"+
		"\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u0129\u0128\u0001\u0000\u0000"+
		"\u0000\u012a)\u0001\u0000\u0000\u0000\u012b\u012d\u0003,\u0016\u0000\u012c"+
		"\u012e\u0003.\u0017\u0000\u012d\u012c\u0001\u0000\u0000\u0000\u012d\u012e"+
		"\u0001\u0000\u0000\u0000\u012e+\u0001\u0000\u0000\u0000\u012f\u0130\u0005"+
		"\u001a\u0000\u0000\u0130\u0131\u0005\u0007\u0000\u0000\u0131\u0132\u0003"+
		"b1\u0000\u0132\u0133\u0005\u0007\u0000\u0000\u0133\u0134\u0005\u001b\u0000"+
		"\u0000\u0134\u0135\u0005\u0007\u0000\u0000\u0135\u0136\u0005\u0004\u0000"+
		"\u0000\u0136-\u0001\u0000\u0000\u0000\u0137\u0138\u0005\u0007\u0000\u0000"+
		"\u0138\u0139\u0005\u001c\u0000\u0000\u0139\u013c\u0005\u0007\u0000\u0000"+
		"\u013a\u013d\u00038\u001c\u0000\u013b\u013d\u0003B!\u0000\u013c\u013a"+
		"\u0001\u0000\u0000\u0000\u013c\u013b\u0001\u0000\u0000\u0000\u013d/\u0001"+
		"\u0000\u0000\u0000\u013e\u0142\u00032\u0019\u0000\u013f\u0142\u0003^/"+
		"\u0000\u0140\u0142\u00034\u001a\u0000\u0141\u013e\u0001\u0000\u0000\u0000"+
		"\u0141\u013f\u0001\u0000\u0000\u0000\u0141\u0140\u0001\u0000\u0000\u0000"+
		"\u01421\u0001\u0000\u0000\u0000\u0143\u0144\u0005\u001d\u0000\u0000\u0144"+
		"\u0147\u0005\u0007\u0000\u0000\u0145\u0148\u0003:\u001d\u0000\u0146\u0148"+
		"\u0005.\u0000\u0000\u0147\u0145\u0001\u0000\u0000\u0000\u0147\u0146\u0001"+
		"\u0000\u0000\u0000\u0148\u0149\u0001\u0000\u0000\u0000\u0149\u014a\u0005"+
		"\u0007\u0000\u0000\u014a\u014b\u0005\u001e\u0000\u0000\u014b\u014f\u0005"+
		"\u0007\u0000\u0000\u014c\u0150\u00036\u001b\u0000\u014d\u0150\u0003B!"+
		"\u0000\u014e\u0150\u0005/\u0000\u0000\u014f\u014c\u0001\u0000\u0000\u0000"+
		"\u014f\u014d\u0001\u0000\u0000\u0000\u014f\u014e\u0001\u0000\u0000\u0000"+
		"\u01503\u0001\u0000\u0000\u0000\u0151\u0152\u0005\u001f\u0000\u0000\u0152"+
		"\u0153\u0005\u0007\u0000\u0000\u0153\u0154\u00038\u001c\u0000\u0154\u0155"+
		"\u0005\u0007\u0000\u0000\u0155\u0156\u0005 \u0000\u0000\u0156\u0157\u0005"+
		"\u0007\u0000\u0000\u0157\u0158\u0003>\u001f\u0000\u0158\u016d\u0001\u0000"+
		"\u0000\u0000\u0159\u015a\u0005!\u0000\u0000\u015a\u015b\u0005\u0007\u0000"+
		"\u0000\u015b\u016d\u0003>\u001f\u0000\u015c\u015d\u0005\"\u0000\u0000"+
		"\u015d\u015e\u0005\u0007\u0000\u0000\u015e\u015f\u0003:\u001d\u0000\u015f"+
		"\u0160\u0005\u0007\u0000\u0000\u0160\u0161\u0005 \u0000\u0000\u0161\u0162"+
		"\u0005\u0007\u0000\u0000\u0162\u0163\u0003:\u001d\u0000\u0163\u016d\u0001"+
		"\u0000\u0000\u0000\u0164\u0165\u0005#\u0000\u0000\u0165\u0166\u0005\u0007"+
		"\u0000\u0000\u0166\u0167\u0003:\u001d\u0000\u0167\u0168\u0005\u0007\u0000"+
		"\u0000\u0168\u0169\u0005 \u0000\u0000\u0169\u016a\u0005\u0007\u0000\u0000"+
		"\u016a\u016b\u0003:\u001d\u0000\u016b\u016d\u0001\u0000\u0000\u0000\u016c"+
		"\u0151\u0001\u0000\u0000\u0000\u016c\u0159\u0001\u0000\u0000\u0000\u016c"+
		"\u015c\u0001\u0000\u0000\u0000\u016c\u0164\u0001\u0000\u0000\u0000\u016d"+
		"5\u0001\u0000\u0000\u0000\u016e\u0175\u00038\u001c\u0000\u016f\u0175\u0003"+
		"P(\u0000\u0170\u0171\u0005\'\u0000\u0000\u0171\u0172\u0003^/\u0000\u0172"+
		"\u0173\u0005(\u0000\u0000\u0173\u0175\u0001\u0000\u0000\u0000\u0174\u016e"+
		"\u0001\u0000\u0000\u0000\u0174\u016f\u0001\u0000\u0000\u0000\u0174\u0170"+
		"\u0001\u0000\u0000\u0000\u01757\u0001\u0000\u0000\u0000\u0176\u0179\u0003"+
		":\u001d\u0000\u0177\u0179\u0003D\"\u0000\u0178\u0176\u0001\u0000\u0000"+
		"\u0000\u0178\u0177\u0001\u0000\u0000\u0000\u01799\u0001\u0000\u0000\u0000"+
		"\u017a\u017d\u0003<\u001e\u0000\u017b\u017d\u0003>\u001f\u0000\u017c\u017a"+
		"\u0001\u0000\u0000\u0000\u017c\u017b\u0001\u0000\u0000\u0000\u017d;\u0001"+
		"\u0000\u0000\u0000\u017e\u017f\u0005\u0004\u0000\u0000\u017f=\u0001\u0000"+
		"\u0000\u0000\u0180\u0181\u0003<\u001e\u0000\u0181\u0182\u0005\u0007\u0000"+
		"\u0000\u0182\u0183\u0005,\u0000\u0000\u0183\u0184\u0005\u0007\u0000\u0000"+
		"\u0184\u0185\u0003@ \u0000\u0185?\u0001\u0000\u0000\u0000\u0186\u018c"+
		"\u0005\u0005\u0000\u0000\u0187\u0188\u0005-\u0000\u0000\u0188\u018c\u0005"+
		"\u0004\u0000\u0000\u0189\u018c\u0003<\u001e\u0000\u018a\u018c\u0003B!"+
		"\u0000\u018b\u0186\u0001\u0000\u0000\u0000\u018b\u0187\u0001\u0000\u0000"+
		"\u0000\u018b\u0189\u0001\u0000\u0000\u0000\u018b\u018a\u0001\u0000\u0000"+
		"\u0000\u018cA\u0001\u0000\u0000\u0000\u018d\u0194\u0001\u0000\u0000\u0000"+
		"\u018e\u0194\u00050\u0000\u0000\u018f\u0190\u00051\u0000\u0000\u0190\u0191"+
		"\u0005\u0007\u0000\u0000\u0191\u0194\u0003<\u001e\u0000\u0192\u0194\u0005"+
		"2\u0000\u0000\u0193\u018d\u0001\u0000\u0000\u0000\u0193\u018e\u0001\u0000"+
		"\u0000\u0000\u0193\u018f\u0001\u0000\u0000\u0000\u0193\u0192\u0001\u0000"+
		"\u0000\u0000\u0194C\u0001\u0000\u0000\u0000\u0195\u019b\u0005\u0001\u0000"+
		"\u0000\u0196\u019b\u0003F#\u0000\u0197\u019b\u0005\u0002\u0000\u0000\u0198"+
		"\u019b\u00053\u0000\u0000\u0199\u019b\u0003H$\u0000\u019a\u0195\u0001"+
		"\u0000\u0000\u0000\u019a\u0196\u0001\u0000\u0000\u0000\u019a\u0197\u0001"+
		"\u0000\u0000\u0000\u019a\u0198\u0001\u0000\u0000\u0000\u019a\u0199\u0001"+
		"\u0000\u0000\u0000\u019bE\u0001\u0000\u0000\u0000\u019c\u019e\u0005>\u0000"+
		"\u0000\u019d\u019c\u0001\u0000\u0000\u0000\u019d\u019e\u0001\u0000\u0000"+
		"\u0000\u019e\u019f\u0001\u0000\u0000\u0000\u019f\u01a2\u0005\u0005\u0000"+
		"\u0000\u01a0\u01a1\u00056\u0000\u0000\u01a1\u01a3\u0005\u0005\u0000\u0000"+
		"\u01a2\u01a0\u0001\u0000\u0000\u0000\u01a2\u01a3\u0001\u0000\u0000\u0000"+
		"\u01a3G\u0001\u0000\u0000\u0000\u01a4\u01a8\u0003J%\u0000\u01a5\u01a8"+
		"\u0003L&\u0000\u01a6\u01a8\u0003N\'\u0000\u01a7\u01a4\u0001\u0000\u0000"+
		"\u0000\u01a7\u01a5\u0001\u0000\u0000\u0000\u01a7\u01a6\u0001\u0000\u0000"+
		"\u0000\u01a8I\u0001\u0000\u0000\u0000\u01a9\u01aa\u00057\u0000\u0000\u01aa"+
		"\u01af\u0003D\"\u0000\u01ab\u01ac\u0005\u000e\u0000\u0000\u01ac\u01ae"+
		"\u0003D\"\u0000\u01ad\u01ab\u0001\u0000\u0000\u0000\u01ae\u01b1\u0001"+
		"\u0000\u0000\u0000\u01af\u01ad\u0001\u0000\u0000\u0000\u01af\u01b0\u0001"+
		"\u0000\u0000\u0000\u01b0\u01b2\u0001\u0000\u0000\u0000\u01b1\u01af\u0001"+
		"\u0000\u0000\u0000\u01b2\u01b3\u00058\u0000\u0000\u01b3K\u0001\u0000\u0000"+
		"\u0000\u01b4\u01bd\u00059\u0000\u0000\u01b5\u01ba\u0003D\"\u0000\u01b6"+
		"\u01b7\u0005:\u0000\u0000\u01b7\u01b9\u0003D\"\u0000\u01b8\u01b6\u0001"+
		"\u0000\u0000\u0000\u01b9\u01bc\u0001\u0000\u0000\u0000\u01ba\u01b8\u0001"+
		"\u0000\u0000\u0000\u01ba\u01bb\u0001\u0000\u0000\u0000\u01bb\u01be\u0001"+
		"\u0000\u0000\u0000\u01bc\u01ba\u0001\u0000\u0000\u0000\u01bd\u01b5\u0001"+
		"\u0000\u0000\u0000\u01bd\u01be\u0001\u0000\u0000\u0000\u01be\u01bf\u0001"+
		"\u0000\u0000\u0000\u01bf\u01c0\u00059\u0000\u0000\u01c0M\u0001\u0000\u0000"+
		"\u0000\u01c1\u01c2\u0005;\u0000\u0000\u01c2\u01c7\u0003D\"\u0000\u01c3"+
		"\u01c4\u0005\u000e\u0000\u0000\u01c4\u01c6\u0003D\"\u0000\u01c5\u01c3"+
		"\u0001\u0000\u0000\u0000\u01c6\u01c9\u0001\u0000\u0000\u0000\u01c7\u01c5"+
		"\u0001\u0000\u0000\u0000\u01c7\u01c8\u0001\u0000\u0000\u0000\u01c8\u01ca"+
		"\u0001\u0000\u0000\u0000\u01c9\u01c7\u0001\u0000\u0000\u0000\u01ca\u01cb"+
		"\u0005<\u0000\u0000\u01cbO\u0001\u0000\u0000\u0000\u01cc\u01d0\u0003R"+
		")\u0000\u01cd\u01d0\u0003V+\u0000\u01ce\u01d0\u0003Z-\u0000\u01cf\u01cc"+
		"\u0001\u0000\u0000\u0000\u01cf\u01cd\u0001\u0000\u0000\u0000\u01cf\u01ce"+
		"\u0001\u0000\u0000\u0000\u01d0Q\u0001\u0000\u0000\u0000\u01d1\u01d2\u0005"+
		"\'\u0000\u0000\u01d2\u01d3\u0003T*\u0000\u01d3\u01d4\u0005\u0007\u0000"+
		"\u0000\u01d4\u01d5\u0007\u0001\u0000\u0000\u01d5\u01d6\u0005\u0007\u0000"+
		"\u0000\u01d6\u01d7\u0003T*\u0000\u01d7\u01d8\u0005(\u0000\u0000\u01d8"+
		"S\u0001\u0000\u0000\u0000\u01d9\u01de\u0003:\u001d\u0000\u01da\u01de\u0003"+
		"F#\u0000\u01db\u01de\u0003B!\u0000\u01dc\u01de\u0003R)\u0000\u01dd\u01d9"+
		"\u0001\u0000\u0000\u0000\u01dd\u01da\u0001\u0000\u0000\u0000\u01dd\u01db"+
		"\u0001\u0000\u0000\u0000\u01dd\u01dc\u0001\u0000\u0000\u0000\u01deU\u0001"+
		"\u0000\u0000\u0000\u01df\u01e9\u0005\'\u0000\u0000\u01e0\u01e1\u0003X"+
		",\u0000\u01e1\u01e2\u0005\u0007\u0000\u0000\u01e2\u01e3\u0007\u0002\u0000"+
		"\u0000\u01e3\u01e4\u0005\u0007\u0000\u0000\u01e4\u01e5\u0003X,\u0000\u01e5"+
		"\u01ea\u0001\u0000\u0000\u0000\u01e6\u01e7\u0005D\u0000\u0000\u01e7\u01e8"+
		"\u0005\u0007\u0000\u0000\u01e8\u01ea\u0003X,\u0000\u01e9\u01e0\u0001\u0000"+
		"\u0000\u0000\u01e9\u01e6\u0001\u0000\u0000\u0000\u01ea\u01eb\u0001\u0000"+
		"\u0000\u0000\u01eb\u01ec\u0005(\u0000\u0000\u01ecW\u0001\u0000\u0000\u0000"+
		"\u01ed\u01f2\u0005\u0002\u0000\u0000\u01ee\u01f2\u0003:\u001d\u0000\u01ef"+
		"\u01f2\u0003Z-\u0000\u01f0\u01f2\u0003V+\u0000\u01f1\u01ed\u0001\u0000"+
		"\u0000\u0000\u01f1\u01ee\u0001\u0000\u0000\u0000\u01f1\u01ef\u0001\u0000"+
		"\u0000\u0000\u01f1\u01f0\u0001\u0000\u0000\u0000\u01f2Y\u0001\u0000\u0000"+
		"\u0000\u01f3\u0200\u0005\'\u0000\u0000\u01f4\u01f5\u0003T*\u0000\u01f5"+
		"\u01f6\u0005\u0007\u0000\u0000\u01f6\u01f7\u0007\u0003\u0000\u0000\u01f7"+
		"\u01f8\u0005\u0007\u0000\u0000\u01f8\u01f9\u0003T*\u0000\u01f9\u0201\u0001"+
		"\u0000\u0000\u0000\u01fa\u01fb\u0003\\.\u0000\u01fb\u01fc\u0005\u0007"+
		"\u0000\u0000\u01fc\u01fd\u0005G\u0000\u0000\u01fd\u01fe\u0005\u0007\u0000"+
		"\u0000\u01fe\u01ff\u0003\\.\u0000\u01ff\u0201\u0001\u0000\u0000\u0000"+
		"\u0200\u01f4\u0001\u0000\u0000\u0000\u0200\u01fa\u0001\u0000\u0000\u0000"+
		"\u0201\u0202\u0001\u0000\u0000\u0000\u0202\u0203\u0005(\u0000\u0000\u0203"+
		"[\u0001\u0000\u0000\u0000\u0204\u0208\u00038\u001c\u0000\u0205\u0208\u0003"+
		"B!\u0000\u0206\u0208\u0003R)\u0000\u0207\u0204\u0001\u0000\u0000\u0000"+
		"\u0207\u0205\u0001\u0000\u0000\u0000\u0207\u0206\u0001\u0000\u0000\u0000"+
		"\u0208]\u0001\u0000\u0000\u0000\u0209\u020a\u0005H\u0000\u0000\u020a\u020b"+
		"\u0005\u0007\u0000\u0000\u020b\u0217\u0003`0\u0000\u020c\u020d\u0005\u0007"+
		"\u0000\u0000\u020d\u020e\u0005I\u0000\u0000\u020e\u020f\u0005\u0007\u0000"+
		"\u0000\u020f\u0214\u00038\u001c\u0000\u0210\u0211\u0005\u000e\u0000\u0000"+
		"\u0211\u0213\u00038\u001c\u0000\u0212\u0210\u0001\u0000\u0000\u0000\u0213"+
		"\u0216\u0001\u0000\u0000\u0000\u0214\u0212\u0001\u0000\u0000\u0000\u0214"+
		"\u0215\u0001\u0000\u0000\u0000\u0215\u0218\u0001\u0000\u0000\u0000\u0216"+
		"\u0214\u0001\u0000\u0000\u0000\u0217\u020c\u0001\u0000\u0000\u0000\u0217"+
		"\u0218\u0001\u0000\u0000\u0000\u0218_\u0001\u0000\u0000\u0000\u0219\u021a"+
		"\u0005\u0004\u0000\u0000\u021aa\u0001\u0000\u0000\u0000\u021b\u0224\u0005"+
		"$\u0000\u0000\u021c\u0224\u0005%\u0000\u0000\u021d\u0224\u0005&\u0000"+
		"\u0000\u021e\u0224\u0003d2\u0000\u021f\u0220\u0005\'\u0000\u0000\u0220"+
		"\u0221\u0003f3\u0000\u0221\u0222\u0005(\u0000\u0000\u0222\u0224\u0001"+
		"\u0000\u0000\u0000\u0223\u021b\u0001\u0000\u0000\u0000\u0223\u021c\u0001"+
		"\u0000\u0000\u0000\u0223\u021d\u0001\u0000\u0000\u0000\u0223\u021e\u0001"+
		"\u0000\u0000\u0000\u0223\u021f\u0001\u0000\u0000\u0000\u0224c\u0001\u0000"+
		"\u0000\u0000\u0225\u0226\u0005\u0003\u0000\u0000\u0226e\u0001\u0000\u0000"+
		"\u0000\u0227\u022b\u0003h4\u0000\u0228\u022b\u0003j5\u0000\u0229\u022b"+
		"\u0003l6\u0000\u022a\u0227\u0001\u0000\u0000\u0000\u022a\u0228\u0001\u0000"+
		"\u0000\u0000\u022a\u0229\u0001\u0000\u0000\u0000\u022bg\u0001\u0000\u0000"+
		"\u0000\u022c\u022d\u0005)\u0000\u0000\u022d\u022e\u0005\u0007\u0000\u0000"+
		"\u022e\u022f\u0005\u0005\u0000\u0000\u022f\u0230\u0005\u0007\u0000\u0000"+
		"\u0230\u0231\u0003b1\u0000\u0231i\u0001\u0000\u0000\u0000\u0232\u0233"+
		"\u0005*\u0000\u0000\u0233\u0234\u0005\u0007\u0000\u0000\u0234\u0235\u0003"+
		"b1\u0000\u0235k\u0001\u0000\u0000\u0000\u0236\u0237\u0005+\u0000\u0000"+
		"\u0237\u0238\u0005\u0007\u0000\u0000\u0238\u023b\u0003n7\u0000\u0239\u023a"+
		"\u0005\u000e\u0000\u0000\u023a\u023c\u0003n7\u0000\u023b\u0239\u0001\u0000"+
		"\u0000\u0000\u023c\u023d\u0001\u0000\u0000\u0000\u023d\u023b\u0001\u0000"+
		"\u0000\u0000\u023d\u023e\u0001\u0000\u0000\u0000\u023em\u0001\u0000\u0000"+
		"\u0000\u023f\u0240\u0003b1\u0000\u0240\u0241\u0005\u0007\u0000\u0000\u0241"+
		"\u0242\u0005\u0004\u0000\u0000\u0242o\u0001\u0000\u0000\u00005rw{\u0081"+
		"\u0086\u00a6\u00a9\u00b2\u00b5\u00b7\u00be\u00c0\u00cd\u00cf\u00de\u00e2"+
		"\u00e8\u00ec\u00f2\u00f6\u011d\u0121\u0129\u012d\u013c\u0141\u0147\u014f"+
		"\u016c\u0174\u0178\u017c\u018b\u0193\u019a\u019d\u01a2\u01a7\u01af\u01ba"+
		"\u01bd\u01c7\u01cf\u01dd\u01e9\u01f1\u0200\u0207\u0214\u0217\u0223\u022a"+
		"\u023d";
	private final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
}
