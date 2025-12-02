/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     simpleErrorListener.java is part of SIMPLE.
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

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

public class simpleErrorListener extends BaseErrorListener {

    private final String[] lines;
    private int counter = 0;
    private boolean counterOn = true;

    public simpleErrorListener(Parser recognizer) {
        lines = recognizer.getInputStream().getTokenSource().getInputStream().toString().split("\n");
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        if (counterOn){
            counter++;
            System.out.print("Error #" + counter + (line > 0 ? " - " : "\n"));
        }
        if (line > 0) System.out.println("line " + line + ":" + charPositionInLine + " ");
        System.out.println(msg);
        if (line > 0 && line <= lines.length){
            String errorLine = lines[line - 1].stripLeading();
            System.out.println(errorLine);
            int start = ((Token) offendingSymbol).getStartIndex();
            int stop = ((Token) offendingSymbol).getStopIndex();
            if (start >= 0 && stop >= 0) {
                int pre = charPositionInLine - (lines[line - 1].length() - errorLine.length());
                int under = 1 + (stop - start);
                for (int i = 0; i < pre; i++) System.out.print(" ");
                for (int i = 0; i < under; i++) System.out.print("^");
                System.out.println("\n");
            }
        }
    }

    public void showCounter(boolean state){
        counterOn = state;
    }
}