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

package com.duy.text.converter.core.codec;


import android.content.Context;
import android.support.annotation.NonNull;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

/**
 * Created by Duy on 05-May-17.
 */

public class SubscriptCodec extends CodecImpl {
    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SUB_SCRIPT = "ₐbcdₑfgₕᵢⱼₖₗₘₙₒₚqᵣₛₜᵤᵥwₓyz_,;.?!/\\'ₐBCDₑFGₕᵢⱼₖₗₘₙₒₚQᵣₛₜᵤᵥWₓYZ₀₁₂₃₄₅₆₇₈₉";

    static {
        if (SUB_SCRIPT.length() != NORMAL.length()) {
            throw new RuntimeException();
        }
    }

    private String textToSub(String text) {
        setMax(text.length());
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int indexOf = NORMAL.indexOf(letter);
            if (indexOf != -1) {
                result.append(SUB_SCRIPT.charAt(indexOf));
                incConfident();
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }

    private String subToText(String text) {
        setMax(text.length());
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = SUB_SCRIPT.indexOf(letter);
            if (a != -1) {
                result.append(NORMAL.charAt(a));
                incConfident();
            } else {
                result.append(letter);
            }

        }
        return result.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToSub(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return subToText(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Subscript";
    }
}
