/*
 * Copyright (c) 2025 - 2026 PCazzaniga (github.com)
 *
 *     progressBar.java is part of SIMPLE.
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

public class progressBar {

    private final int total;

    private final int ratio;

    private boolean visibleProgress = false;

    private int current = 0;

    private int percentage = 0;

    progressBar(int total, int ratio){
        this.total = total;
        this.ratio = ratio;
    }

    void progress(){
        current++;
        int curr_percentage = (current * 100) / total;
        if (curr_percentage != percentage) {
            percentage = curr_percentage;
            visibleProgress = true;
        }
    }

    public boolean isVisibleProgress() {
        return visibleProgress;
    }

    @Override
    public String toString() {
        visibleProgress = false;
        int progress = percentage / ratio;
        return "[" + "#".repeat(progress) + " ".repeat((100 / ratio) - progress) + "] " + percentage + "%";
    }
}