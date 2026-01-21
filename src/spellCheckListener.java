/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     spellCheckListener.java is part of SIMPLE.
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

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.VocabularyImpl;

import java.util.Arrays;
import java.util.List;

class spellCheckListener extends simpleSpellCheckBaseListener{

	private final simpleSpellCheckParser recognizer;
	private final List<String> literals;
	private final int TOLERANCE = 3;
	private int errors = 0;

	spellCheckListener(simpleSpellCheckParser recognizer) {
		this.recognizer = recognizer;
		List<String> temp = Arrays.stream(((VocabularyImpl) recognizer.getVocabulary()).getLiteralNames()).toList();
		this.literals = temp.stream().filter(l -> l != null && l.length() > 3).toList();
	}

	@Override
	public void enterMistake(simpleSpellCheckParser.MistakeContext ctx) {
		errors++;
		Token mis = ctx.UNKNOWN().getSymbol();
		String got = "'" + mis.getText() + "'";
		String msg = "Misspelled or extraneous input " + got + ".";
		if (got.length() > 3) {
			List<String> alts = Levenshtein.filterByMaxDistance(got, literals, Math.min(got.length() / 2, TOLERANCE));
			alts = Levenshtein.sortByDistance(got, alts);
			if (!alts.isEmpty()){
				StringBuilder msg2 = new StringBuilder(" Did you mean ");
				msg2.append(alts.remove(0));
				if(!alts.isEmpty()){
					for (String s : alts.subList(0, alts.size() - 1)) {
						msg2.append(", ").append(s);
					}
					msg2.append(" or ").append(alts.get(alts.size() - 1));
				}
				msg2.append(" ?");
				msg += msg2.toString();
			}
		}
		recognizer.notifyErrorListeners(mis, msg, null);
	}

	public boolean foundErrors() {
		return errors > 0;
	}

}