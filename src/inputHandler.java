/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     inputHandler.java is part of SIMPLE.
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

final class inputHandler{

	private inputHandler() {}

	public static valueVisitor.Val valOfLiteral(simpleRTLitParser.LiteralContext ctx) {
		if (ctx.Number() != null) return new valueVisitor.numberVal(Double.parseDouble(ctx.Number().getText()));
		if (ctx.Boolean() != null) {
			String tr = simpleLexer.VOCABULARY.getLiteralName(simpleParser.TRUE).replace("'", "");
			return new valueVisitor.booleanVal(ctx.Boolean().getText().equals(tr));
		}
		return new valueVisitor.textVal(ctx.Text().getText());
	}

	public static typeVisitor.dataType typeOfLiteral(simpleRTLitParser.LiteralContext ctx){
		if (ctx.Number() != null) return new typeVisitor.numberType();
		if (ctx.Boolean() != null) return new typeVisitor.booleanType();
		return new typeVisitor.textType();
	}
}
