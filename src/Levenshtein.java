/*
 * Copyright (c) 2025 -2026 PCazzaniga (github.com)
 *
 *     Levenshtein.java is part of SIMPLE.
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

import static java.lang.Math.abs;

//import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;

class Levenshtein {

	public static boolean distanceLoE(String first, String second, int max){
		if (max < 0) return false;
		if (first.equals(second)) return true;
		int lenF = first.length();
		int lenS = second.length();
		if(abs(lenF - lenS) > max) return false;
		if (lenF == 0) return lenS <= max;
		if (lenS == 0) return lenF <= max;
		if (lenF < lenS) {
			int tempLen = lenF;
			lenF = lenS;
			lenS = tempLen;
			String tempStr = first;
			first = second;
			second = tempStr;
		}
		int[] cost = new int[lenS + 1];
		for (int i = 0; i <= lenS; i++) cost[i] = i;
		for (int i = 1; i <= lenF; i++) {
			cost[0] = i;
			int prev = i-1;
			int min = prev;
			for (int j = 1; j <= lenS; j++) {
				int act = prev;
				if (first.charAt(i-1) != second.charAt(j-1)) act++;
				prev = cost[j];
				cost[j] = min(prev + 1, cost[j-1] + 1, act);
				if (prev < min) min = prev;
			}
			if (min > max) return false;
		}
		return cost[lenS] <= max;
	}

    /*public static int distance(String first, String second) {
        if (first.equals(second)) return 0;
        int lenF = first.length();
        int lenS = second.length();
        if (lenF == 0) return lenS;
        if (lenS == 0) return lenF;
        if (lenF < lenS) {
            int tempL = lenF;
            lenF = lenS;
            lenS = tempL;
            String tempS = first;
            first = second;
            second = tempS;
        }
        int[] cost = new int[lenS+1];
        for (int i=0; i<=lenS; i+=1) cost[i] = i;
        for (int i=1; i<=lenF; i+=1) {
            cost[0] = i;
            int prev = i-1;
            int min = prev;
            for (int j=1; j<=lenS; j+=1) {
                int act = prev;
                if (first.charAt(i-1) != second.charAt(j-1)) act++;
                prev = cost[j];
                cost[j] = min(cost[j] + 1, cost[j-1] + 1, act);
                if (prev < min) min = prev;
            }
        }
        return cost[lenS];
    }*/

	private static int min(int a, int b, int c) {
		int min = a;
		if (b < min) min = b;
		if (c < min) min = c;
		return min;
	}

	public static List<String> filterByMaxDistance(String matcher, List<String> targets, int max){
		return targets.stream().filter(str -> distanceLoE(matcher, str, max)).toList();
	}

	/*public static List<String> sortByDistance(String matcher, List<String> targets){
		List<String> copy = new ArrayList<>(targets);
		copy.sort(Comparator.comparingInt(str -> distance(matcher, str)));
		return copy;
	}*/
}