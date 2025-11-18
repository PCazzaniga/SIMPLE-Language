/*
 * Copyright (c) 2025 PCazzaniga (github.com)
 *
 *     valueVisitor.java is part of SIMPLE.
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

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

class valueVisitor extends simpleBaseVisitor<valueVisitor.Val> {

	private final Parser recognizer;

	private final Random rng = new Random();

	private final Map<String, Val> localVars;
	
	private final Deque<Map<String, valueVisitor.Val>> allVars;

	public interface Val {
		boolean equals(Val v);
	}
	
	interface structVal extends Val {
		Val getAt(int position);

		void setAt(int position, Val newVal);

		int size();

		structVal makeCopy();
	}
	
	record nothingVal() implements Val {

		@Override
		public boolean equals(Val v) {
			return v instanceof nothingVal;
		}

		@Override
		public String toString() {
			return "_";
		}

	}
	
	record numberVal(double val) implements Val {

		@Override
		public boolean equals(Val v) {
			return (v instanceof numberVal) && Math.abs(this.val - ((numberVal) v).val()) <= Math.ulp(this.val);
		}

		@Override
		public String toString() {
			return (val - (int) val == 0) ? Integer.toString((int) val) : Double.toString(val);
		}
	}
	
	record textVal(String val) implements Val {
		@Override
		public boolean equals(Val v) {
			return (v instanceof textVal) && this.val.equals(((textVal) v).val());
		}

		@Override
		public String toString() {
			return val;
		}
	}
	
	record booleanVal(boolean val) implements Val {
		@Override
		public boolean equals(Val v) {
			return (v instanceof booleanVal) && (this.val == ((booleanVal) v).val());
		}

		@Override
		public String toString() {
			if (val) return simpleLexer.VOCABULARY.getLiteralName(simpleParser.TRUE).replace("'", "");
			return simpleLexer.VOCABULARY.getLiteralName(simpleParser.FALSE).replace("'", "");
		}
	}
	
	record seqVal(Val[] vals) implements structVal {

		@Override
		public boolean equals(Val v) {
			if (!(v instanceof seqVal)) return false;
			if (this.size() != ((seqVal) v).size()) return false;
			for (int i = 0; i < this.size(); i++) {
				if (!this.vals[i].equals(((seqVal) v).vals[i])) return false;
			}
			return true;
		}

		public Val getAt(int position) {
			return vals[position];
		}

		public void setAt(int position, Val newVal) {
			if (!(vals[position] instanceof kitVal)) vals[position] = newVal;
			else ((kitVal) vals[position]).setAll(((kitVal) newVal).vals());
		}

		public int size() {
			return vals.length;
		}

		@Override
		public structVal makeCopy() {
			Val[] copyVals = new Val[vals.length];
			if(vals.length > 0){
				if (vals[0] instanceof structVal){
					for(int i = 0; i < vals.length; i++) copyVals[i] = ((structVal) vals[i]).makeCopy();
				} else System.arraycopy(vals, 0, copyVals, 0, vals.length);
			}
			return new seqVal(copyVals);
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder("[");
			for (Val v : vals) str.append(v.toString()).append(", ");
			str.replace(str.length() - 2, str.length(), "]");
			return str.toString();
		}
	}
	
	record listVal(List<Val> vals) implements structVal {

		@Override
		public boolean equals(Val v) {
			if (!(v instanceof listVal)) return false;
			if (this.size() != ((listVal) v).size()) return false;
			for (int i = 0; i < this.size(); i++) {
				if (!this.vals.get(i).equals(((listVal) v).vals().get(i))) return false;
			}
			return true;
		}

		public Val getAt(int position) {
			return vals.get(position);
		}

		public void setAt(int position, Val newVal) {
			if(vals.isEmpty() && position == 0) vals.add(newVal);
			else {
				if (!(vals.get(position) instanceof kitVal)) vals.set(position, newVal);
				else ((kitVal) vals.get(position)).setAll(((kitVal) newVal).vals());
			}
		}

		public int size() {
			return vals.size();
		}

		@Override
		public structVal makeCopy() {
			List<Val> copyVals = new ArrayList<>(vals.size());
			if(!vals.isEmpty()) {
				if (vals.get(0) instanceof structVal) {
					for (Val ogVal : vals) copyVals.add(((structVal) ogVal).makeCopy());
				} else copyVals.addAll(vals);
			}
			return new listVal(copyVals);
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder("|");
			for (Val v : vals) str.append(v.toString()).append("; ");
			if(!vals.isEmpty()) str.replace(str.length() - 2, str.length(), "|");
			else str.append(" |");
			return str.toString();
		}
	}
	
	record kitVal(List<String> fields, List<Val> vals) implements structVal {

		@Override
		public boolean equals(Val v) {
			if (!(v instanceof kitVal)) return false;
			if (this.size() != ((kitVal) v).size()) return false;
			for (int i = 0; i < this.size(); i++) {
				if (!this.vals.get(i).equals(((kitVal) v).vals().get(i))) return false;
			}
			return true;
		}

		public Val getAt(int position) {
			return vals.get(position);
		}

		public Val getAtField(String field) {
			return vals.get(fields.indexOf(field));
		}

		public void setAll(List<Val> newVals) {
			for (int i = 0; i < vals.size(); i++) setAt(i, newVals.get(i));
		}

		public void setAt(int position, Val newVal) {
			if (!(vals.get(position) instanceof kitVal)) vals.set(position, newVal);
			else ((kitVal) vals.get(position)).setAll(((kitVal) newVal).vals());
		}

		public void setAtField(String field, Val newVal) {
			setAt(fields.indexOf(field), newVal);
		}

		public void setFields(List<String> names){
			fields.clear();
			fields.addAll(names);
		}

		public int size() {
			return vals.size();
		}

		@Override
		public structVal makeCopy() {
			List<Val> copyVals = new ArrayList<>(vals.size());
			for(Val ogVal: vals) copyVals.add(ogVal instanceof structVal ? ((structVal) ogVal).makeCopy() : ogVal);
			return new kitVal(new ArrayList<>(fields), copyVals);
		}

		@Override
		public String toString() {
			StringBuilder str = new StringBuilder("{");
			for (Val v : vals) str.append(v.toString()).append(", ");
			str.replace(str.length() - 2, str.length(), "}");
			return str.toString();
		}
	}
	
	public valueVisitor(Parser recognizer, Map<String, Val> localVars, Deque<Map<String, Val>> allVars) {
		this.recognizer = recognizer;
		this.localVars = localVars;
		this.allVars = allVars;
	}

	@Override
	public Val visitAr_oprnd(simpleParser.Ar_oprndContext ctx) {
		if (ctx.variable() != null) return visitVariable(ctx.variable());
		if (ctx.number() != null) return new numberVal(Double.parseDouble(ctx.number().getText()));
		if (ctx.reserved() != null) return visitReserved(ctx.reserved());
		return visitArith_op(ctx.arith_op());
	}
	
	@Override
	public Val visitArith_op(simpleParser.Arith_opContext ctx) {
		numberVal first = (numberVal) visitAr_oprnd(ctx.ar_oprnd(0));
		numberVal second = (numberVal) visitAr_oprnd(ctx.ar_oprnd(1));
		return switch (ctx.opr.getType()) {
			case simpleParser.ADD -> new numberVal(first.val() + second.val());
			case simpleParser.SUB -> new numberVal(first.val() - second.val());
			case simpleParser.MULT -> new numberVal(first.val() * second.val());
			case simpleParser.DIV -> {
				if (second.equals(new numberVal(0))) {
					signalError(ctx.ar_oprnd(1), errorRuntimeMsg.divisionByZero());
					System.exit(1);
				}
				yield new numberVal(first.val() / second.val());
			}
			default -> {
				if (second.equals(new numberVal(0))) {
					signalError(ctx.ar_oprnd(1), errorRuntimeMsg.modulusByZero());
					System.exit(1);
				}
				yield new numberVal(first.val() % second.val());
			}
		};
	}

	@Override
	public Val visitComp_oprnd(simpleParser.Comp_oprndContext ctx) {
		if (ctx.direct_value() != null) return visitDirect_value(ctx.direct_value());
		if (ctx.reserved() != null) return visitReserved(ctx.reserved());
		return visitArith_op(ctx.arith_op());
	}

	@Override
	public Val visitCompare_op(simpleParser.Compare_opContext ctx) {
		if (ctx.EQ() != null) {
			return new booleanVal(visitComp_oprnd(ctx.comp_oprnd(0)).equals(visitComp_oprnd(ctx.comp_oprnd(1))));
		}
		numberVal first = (numberVal) visitAr_oprnd(ctx.ar_oprnd(0));
		numberVal second = (numberVal) visitAr_oprnd(ctx.ar_oprnd(1));
		return new booleanVal((ctx.GT() != null) ? (first.val() > second.val()) : (first.val() < second.val()));
	}
	
	@Override
	public Val visitCondition(simpleParser.ConditionContext ctx) {
		return ctx.logic_op() != null ? visitLogic_op(ctx.logic_op()) : visitCompare_op(ctx.compare_op());
	}
	
	@Override
	public Val visitDirect_value(simpleParser.Direct_valueContext ctx) {
		return ctx.literal() != null ? visitLiteral(ctx.literal()) : visitVariable(ctx.variable());
	}
	
	@Override
	public Val visitLiteral(simpleParser.LiteralContext ctx) {
		if (ctx.Text() != null) return new textVal(cleanUpTextLit(ctx.Text().getText()));
		if (ctx.number() != null) return new numberVal(Double.parseDouble(ctx.number().getText()));
		if (ctx.Boolean() != null) {
			String tr = simpleLexer.VOCABULARY.getLiteralName(simpleParser.TRUE).replace("'", "");
			return new booleanVal(ctx.Boolean().getText().equals(tr));
		}
		if (ctx.NULL() != null) return new nothingVal();
		return visitStruct_lit(ctx.struct_lit());
	}
	
	@Override
	public Val visitLog_oprnd(simpleParser.Log_oprndContext ctx) {
		if (ctx.Boolean() != null) {
			String tr = simpleLexer.VOCABULARY.getLiteralName(simpleParser.TRUE).replace("'", "");
			return new booleanVal(ctx.Boolean().getText().equals(tr));
		}
		if (ctx.variable() != null) return visitVariable(ctx.variable());
		if (ctx.compare_op() != null) return visitCompare_op(ctx.compare_op());
		return visitLogic_op(ctx.logic_op());
	}

	@Override
	public Val visitLogic_op(simpleParser.Logic_opContext ctx) {
		boolean first = ((booleanVal) visitLog_oprnd(ctx.log_oprnd(0))).val();
		if (ctx.NOT() != null) return new booleanVal(!first);
		if ((!first && ctx.AND() != null) || (first && ctx.OR() != null)) return new booleanVal(first);
		return new booleanVal(((booleanVal) visitLog_oprnd(ctx.log_oprnd(1))).val);
	}

	@Override
	public Val visitReserved(simpleParser.ReservedContext ctx) {
		if (ctx.COUNTER() != null) {
			return fetchValue(simpleLexer.VOCABULARY.getLiteralName(simpleParser.COUNTER).replace("'", ""));
		}
		if (ctx.SIZEOF() != null) return new numberVal(((structVal) visitVar_name(ctx.var_name())).size());
		return new numberVal((float) rng.nextInt(0, 100));
	}

	@Override
	public Val visitStruct_access(simpleParser.Struct_accessContext ctx) {
		structVal st = (structVal) fetchValue(ctx.var_name().NAME().getText());
		simpleParser.AccessContext acc = ctx.access();
		if (acc.NAME() != null) return ((kitVal) st).getAtField(acc.NAME().getText());
		int position;
		if (acc.NUM() != null) position = Integer.parseInt(acc.NUM().getText());
		else if (acc.var_name() != null) position = (int) ((numberVal) visitVar_name(acc.var_name())).val();
		else position = (int) ((numberVal) visitReserved(acc.reserved())).val();
		if (position < 1) {
			signalError(acc, errorRuntimeMsg.accessUnderSize(position));
			System.exit(1);
		}
		if (position > st.size()) {
			signalError(acc, errorRuntimeMsg.accessOverSize(position, st.size()));
			System.exit(1);
		}
		return st.getAt(position - 1);
	}
	
	@Override
	public Val visitStruct_lit(simpleParser.Struct_litContext ctx) {
		if (ctx.sequence() != null) {
			int size = ctx.sequence().literal().size();
			Val[] lvs = new Val[size];
			for (int i = 0; i < size; i++) lvs[i] = visitLiteral(ctx.sequence().literal(i));
			return new seqVal(lvs);
		}
		if (ctx.list() != null) {
			List<Val> lvs = new ArrayList<>(ctx.list().literal().size());
			for (simpleParser.LiteralContext lv : ctx.list().literal()) lvs.add(visitLiteral(lv));
			return new listVal(lvs);
		}
		List<Val> lvs = new ArrayList<>(ctx.kit().literal().size());
		for (simpleParser.LiteralContext lv : ctx.kit().literal()) lvs.add(visitLiteral(lv));
		return new kitVal(new ArrayList<>(), lvs);
	}
	
	@Override
	public Val visitVar_name(simpleParser.Var_nameContext ctx) {
		return fetchValue(ctx.NAME().getText());
	}

	@Override
	public Val visitVariable(simpleParser.VariableContext ctx) {
		return ctx.var_name() != null ? visitVar_name(ctx.var_name()) : visitStruct_access(ctx.struct_access());
	}
	
	private String cleanUpTextLit(String text){
		String clean = text.substring(1, text.length() - 1)
				.replace("\\\\", "\\")
				.replace("\\t", "\t")
				.replace("\\b", "\b")
				.replace("\\n", "\n")
				.replace("\\r", "\r")
				.replace("\\f", "\f")
				.replace("\\\"", "\"")
				.replace("\\x5C", "\\")
				.replace("\\x24", "$");
		Pattern asciiEsc = Pattern.compile("\\\\x([0-9a-fA-F]{2})");
		return asciiEsc.matcher(clean).replaceAll(match -> String.valueOf((char) Integer.parseInt(match.group(1), 16)));
	}

	private Val fetchValue(String name) {
		if (localVars.containsKey(name)) return localVars.get(name);
		for (Map<String, Val> higher : allVars) {
			if (higher.containsKey(name)) return higher.get(name);
		}
		return new nothingVal();
	}

	private void signalError(ParserRuleContext ctx, String msg){
		CommonToken ref = new CommonToken(ctx.start);
		ref.setStopIndex(ctx.stop.getStopIndex());
		recognizer.notifyErrorListeners(ref, msg, null);
	}
}
