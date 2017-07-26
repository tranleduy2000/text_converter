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

package com.duy.sharedcode.codec;


/**
 * Created by DUy on 06-Feb-17.
 */

public class UpsideDownTool {
    /**
     * original text
     */
    public static final String NORMAL = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * upside down text
     */
    public static final String FLIP = "ɐqɔpǝɟbɥıظʞlɯuodbɹsʇnʌʍxʎz‾'؛˙¿¡/\\,∀qϽᗡƎℲƃHIſʞ˥WNOԀὉᴚS⊥∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86";




    /**
     * up side down text
     * <p>
     * hello world, i'm dennis -> sıuuǝp ɯ,ı 'p1ɹoʍ o11ǝɥ
     */
    public static String textToUpsideDown(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? FLIP.charAt(a) : letter;
        }
        return new StringBuilder(result).reverse().toString();
    }

    public static String upsideDownToText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = FLIP.indexOf(letter);
            result += (a != -1) ? NORMAL.charAt(a) : letter;
        }
        return new StringBuilder(result).reverse().toString();
    }

}
