/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     simpleErrorStrategy.java is part of SIMPLE.
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

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.regex.Pattern;

class simpleErrorStrategy extends DefaultErrorStrategy{

    @Override
    protected void reportMissingToken(Parser recognizer) {
        if (!this.inErrorRecoveryMode(recognizer)) {
            this.beginErrorCondition(recognizer);
            Token t = recognizer.getCurrentToken();
            IntervalSet expecting = this.getExpectedTokens(recognizer);
            String msg = "Missing ";
            if(expecting.size() > 1){
                msg += "at " + this.getTokenErrorDisplay(t) + ":\n" + getTokenExpectations(expecting, recognizer.getVocabulary());
            } else {
                String descr = getDescription(expecting.get(0), recognizer.getVocabulary());
                descr = Pattern.compile("^.").matcher(descr).replaceFirst(m -> m.group().toLowerCase());
                msg += descr + " at " + this.getTokenErrorDisplay(t);
            }
            recognizer.notifyErrorListeners(t, msg, null);
        }
    }

    @Override
    protected void reportNoViableAlternative(Parser recognizer, NoViableAltException e) {
        TokenStream tokens = recognizer.getInputStream();
        String msg = "No viable parsing alternative ";
        if (tokens != null) {
            if (e.getStartToken().getType() == -1) {
                msg = msg + "at <EOF>";
            } else {
                Token start = e.getStartToken();
                String from = escapeWSAndQuote(start.getText()) + " (line " + start.getLine() + ":" + start.getCharPositionInLine() + ")";
                String to;
                if (e.getOffendingToken().getType() == simpleParser.INDENT){
                    to = "<indentation increase>";
                } else if (e.getOffendingToken().getType() == simpleParser.DEDENT){
                    to = "<indentation decrease>";
                } else {
                    to = escapeWSAndQuote(e.getOffendingToken().getText());
                }
                msg = msg + "from " + from + " to " + to;
            }
        } else {
            msg = msg + "at <unknown input>";
        }
        recognizer.notifyErrorListeners(e.getOffendingToken(), msg, e);
    }

    @Override
    protected void reportInputMismatch(Parser recognizer, InputMismatchException e) {
        Token offender = e.getOffendingToken();
        String offendStr;
        if (offender.getType() == simpleParser.INDENT){
            offendStr = "<indentation increase>";
        } else if (offender.getType() == simpleParser.DEDENT){
            offendStr = "<indentation decrease>";
        } else {
            offendStr = getTokenErrorDisplay(offender);
        }
        IntervalSet expecting = e.getExpectedTokens();
        String msg = "Mismatched input " + offendStr + " expecting";
        if(expecting.size() > 1){
            msg += ":\n" + getTokenExpectations(expecting, recognizer.getVocabulary());
        } else {
            String descr = getDescription(expecting.get(0), recognizer.getVocabulary());
            msg += " a " + Pattern.compile("^.").matcher(descr).replaceFirst(m -> m.group().toLowerCase());
        }
        recognizer.notifyErrorListeners(offender, msg, e);
    }

    @Override
    protected void reportUnwantedToken(Parser recognizer) {
        if (!this.inErrorRecoveryMode(recognizer)) {
            this.beginErrorCondition(recognizer);
            Token t = recognizer.getCurrentToken();
            String tokenName;
            if (t.getType() == simpleParser.INDENT){
                tokenName = "<indentation increase>";
            } else if (t.getType() == simpleParser.DEDENT){
                tokenName = "<indentation decrease>";
            } else {
                tokenName = getTokenErrorDisplay(t);
            }
            IntervalSet expecting = this.getExpectedTokens(recognizer);
            String msg = "Extraneous input " + tokenName + " expecting";
            if(expecting.size() > 1){
                msg += ":\n" + getTokenExpectations(expecting, recognizer.getVocabulary());
            } else {
                String descr = getDescription(expecting.get(0), recognizer.getVocabulary());
                msg += " a " + Pattern.compile("^.").matcher(descr).replaceFirst(m -> m.group().toLowerCase());
            }
            recognizer.notifyErrorListeners(t, msg, null);
        }
    }

    private String getTokenExpectations(IntervalSet tokens, Vocabulary vocabulary){
        StringBuilder sb = new StringBuilder();
        for (Integer i: tokens.toList()){
            sb.append("   -> ").append(getDescription(i, vocabulary)).append("\n");
        }
        if (!sb.isEmpty()){
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private String getDescription(Integer type, Vocabulary vocabulary) {
        return switch (type) {
            case -1 -> "End of file";
            case simpleParser.Text -> "Text literal";
            case simpleParser.Boolean -> "Boolean literal";
            case simpleParser.TYPENAME -> "Type alias";
            case simpleParser.NAME -> "Name";
            case simpleParser.NUM -> "Number literal";
            case simpleParser.COMMENT -> "Comment";

            case simpleParser.INDENT -> "Indentation increase";
            case simpleParser.DEDENT -> "Indentation decrease";
            case simpleParser.NEWLINE -> "Newline";
            default -> {
                String description = switch (type) {
                    case simpleParser.NEWTYPE -> "Type aliasing";
                    case simpleParser.DEFINE -> "Function declaration";
                    case simpleParser.RESULT -> "Function result";
                    case simpleParser.PARAMS,
                         simpleParser.PARAMS2 -> "Function parameters";
                    case simpleParser.IF -> "Conditional statement";
                    case simpleParser.THEN -> "Statement entry";
                    case simpleParser.ELSEIF,
                         simpleParser.ELSE -> "Continued conditional statement";
                    case simpleParser.REPEAT -> "Loop statement";
                    case simpleParser.WHILE -> "Loop condition";
                    case simpleParser.TIMES -> "Loop quantification";
                    case simpleParser.RETURN -> "Return instruction";
                    case simpleParser.PREPARE -> "Declaration";
                    case simpleParser.NAMED -> "Naming";
                    case simpleParser.VALUED -> "Value initialization";
                    case simpleParser.SET -> "Assignment instruction";
                    case simpleParser.TO -> "Value assignment";
                    case simpleParser.INSERT -> "List insertion instruction";
                    case simpleParser.IN -> "List operation destination";
                    case simpleParser.REMOVE -> "List removal instruction";
                    case simpleParser.SPLIT -> "Text splitting instruction";
                    case simpleParser.MERGE -> "Text list merging instruction";
                    case simpleParser.NUMBER,
                         simpleParser.STRING,
                         simpleParser.BOOL,
                         simpleParser.SEQUENCE,
                         simpleParser.LIST,
                         simpleParser.KIT -> "Type";
                    case simpleParser.POSITION -> "Position";
                    case simpleParser.SIZEOF -> "Structure size";
                    //All operators here ?
                    case simpleParser.CALL -> "Function call";
                    case simpleParser.ARGUMENTS -> "Call arguments";
                    default -> "";
                };
                description += (!description.isEmpty() ? " using " : "") + vocabulary.getLiteralName(type);
                yield description;
            }
        };
    }
}
