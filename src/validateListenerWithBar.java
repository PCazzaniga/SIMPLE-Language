/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     validateListenerWithBar.java is part of SIMPLE.
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

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

public class validateListenerWithBar extends validateListener{

    private final progressBar bar;

    public validateListenerWithBar(Parser recognizer, int totalTrack) {
        super(recognizer);
        bar = new progressBar(totalTrack, 5);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if(!(ctx instanceof simpleParser.FileContext)){
            bar.progress();
            if (bar.isVisibleProgress()) {
                System.out.print("\r" + bar);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void exitFile(simpleParser.FileContext ctx) {
        bar.progress();
        String finalBar = bar.toString();
        System.out.print("\r" + finalBar);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("\r" + " ".repeat(finalBar.length()) + "\r");
        super.exitFile(ctx);
    }
}