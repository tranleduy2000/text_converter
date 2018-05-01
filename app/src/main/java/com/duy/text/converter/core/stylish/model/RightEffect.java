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

/**
 * Created by Duy on 13-Jul-17.
 */

public class RightEffect implements Style {

    private String character;

    public RightEffect(String text) {
        this.character = text;
    }

    @NonNull
    @Override
    public String generate(@NonNull String input) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == ' ') {
                    result.append(" ");
                } else {
                    result.append(input.charAt(i)).append(character);
                }
            }
            return result.toString();
        } catch (OutOfMemoryError e) {
            return "";
        }
    }

    @Override
    public int hashCode() {
        return character.hashCode();
    }
}
