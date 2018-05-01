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
 * Created by DUy on 06-Feb-17.
 */

public class UpsideDownCodec extends CodecImpl {
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


    @NonNull
    @Override
    public String getName(Context context) {
        return "Upside down";
    }
}
