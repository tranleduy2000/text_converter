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

/**
 * Created by Duy on 05-May-17.
 */

public class SupscriptCodec implements Codec {
    public static final String SUPPER_SCRIPT = "ᵃᵇᶜᵈᵉᶠᵍʰⁱʲᵏˡᵐⁿᵒᵖqʳˢᵗᵘᵛʷˣʸᶻ_,;.?!/\\'ᴬᴮᶜᴰᴱᶠᴳᴴᴵᴶᴷᴸᴹᴺᴼᴾQᴿˢᵀᵁⱽᵂˣʸᶻ⁰¹²³⁴⁵⁶⁷⁸⁹";
    public static final String NORMAL = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String textToSup(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? SUPPER_SCRIPT.charAt(a) : letter;
        }
        return result;
    }

    public static String supToText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = SUPPER_SCRIPT.indexOf(letter);
            result += (a != -1) ? NORMAL.charAt(a) : letter;
        }
        return result;
    }

    public static void main(String[] args) {
        String x = textToSup("abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        System.out.println(x);
        System.out.println(supToText(x));
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        try {
            return textToSup(text);
        } catch (Exception e) {
            return text;
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        try {
            return supToText(text);
        } catch (Exception e) {
            return text;
        }
    }

    @Override
    public String getName(Context context) {
        return null;
    }

    @Override
    public int getMax() {
        return 0;
    }

    @Override
    public int getConfident() {
        return 0;
    }
}
