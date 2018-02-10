/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
