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


import android.support.annotation.NonNull;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

/**
 * Created by DUy on 06-Feb-17.
 */

public class UpsideDownTool extends CodecImpl {
    /**
     * upside down text
     */
    private static final String FLIP = "ɐqɔpǝɟbɥıɾʞlɯuodbɹsʇnʌʍxʎz‾'؛˙¿¡/\\,∀qϽᗡƎℲƃHIſʞ˥WNOԀὉᴚS⊥∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86";
    /**
     * original text
     */
    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    static {
        if (NORMAL.length() != FLIP.length()) {
            throw new RuntimeException();
        }
    }

    /**
     * up side down text
     * <p>
     * hello world, i'm dennis -> sıuuǝp ɯ,ı 'p1ɹoʍ o11ǝɥ
     */
    private String textToUpsideDown(String text) {
        setMax(text.length());
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            if (a != -1) {
                result.append(FLIP.charAt(a));
                incConfident();
            } else {
                result.append(letter);
            }
        }
        return new StringBuilder(result.toString()).reverse().toString();
    }

    private String upsideDownToText(String text) {
        setMax(text.length());
        StringBuilder result = new StringBuilder();
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = FLIP.indexOf(letter);
            if (a != -1) {
                result.append(NORMAL.charAt(a));
                incConfident();
            } else {
                result.append(letter);
            }
        }
        return new StringBuilder(result.toString()).reverse().toString();
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return upsideDownToText(text);
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToUpsideDown(text);
    }


}
