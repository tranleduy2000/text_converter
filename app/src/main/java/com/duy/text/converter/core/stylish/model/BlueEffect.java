/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.text.converter.core.stylish.model;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by Duy on 13-Jul-17.
 */

public class BlueEffect implements Style {
    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String[] EFFECTS;

    static {
        String blue =
                "\uD83C\uDDE6 \uD83C\uDDE7 \uD83C\uDDE8 \uD83C\uDDE9 \uD83C\uDDEA \uD83C\uDDEB " +
                        "\uD83C\uDDEC \uD83C\uDDED \uD83C\uDDEE \uD83C\uDDEF \uD83C\uDDF0 \uD83C\uDDF1 " +
                        "\uD83C\uDDF2 \uD83C\uDDF3 \uD83C\uDDF4 \uD83C\uDDF5 \uD83C\uDDF6 \uD83C\uDDF7 " +
                        "\uD83C\uDDF8 \uD83C\uDDF9 \uD83C\uDDFA \uD83C\uDDFB \uD83C\uDDFC \uD83C\uDDFD " +
                        "\uD83C\uDDFE \uD83C\uDDFF \uD83C\uDDE6 \uD83C\uDDE7 \uD83C\uDDE8 \uD83C\uDDE9 " +
                        "\uD83C\uDDEA \uD83C\uDDEB \uD83C\uDDEC \uD83C\uDDED \uD83C\uDDEE \uD83C\uDDEF " +
                        "\uD83C\uDDF0 \uD83C\uDDF1 \uD83C\uDDF2 \uD83C\uDDF3 \uD83C\uDDF4 \uD83C\uDDF5 " +
                        "\uD83C\uDDF6 \uD83C\uDDF7 \uD83C\uDDF8 \uD83C\uDDF9 \uD83C\uDDFA \uD83C\uDDFB " +
                        "\uD83C\uDDFC \uD83C\uDDFD \uD83C\uDDFE \uD83C\uDDFF ";
        EFFECTS = new String[NORMAL.length()];
        int length = NORMAL.length();
        int step = blue.length() / length;
        for (int j = 0; j < length; j++) {
            EFFECTS[j] = blue.substring(j * step, (j + 1) * step);
        }
    }

    @NonNull
    @Override
    public String generate(@NonNull String text) {
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int index = NORMAL.indexOf(letter);
            try {
                result.append((index != -1) ? EFFECTS[index] : letter);
            } catch (Exception e) {
                result.append(letter);
            }
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(EFFECTS);
    }
}
