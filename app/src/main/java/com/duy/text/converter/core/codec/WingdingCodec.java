/*
 * Copyright (c) 2018 by Tran Le Duy
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
import com.duy.text.converter.utils.CodePointUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duy on 2/13/2018.
 */

public class WingdingCodec extends CodecImpl {
    static final HashMap<Character, Integer> WING_DINGS_MAP = new HashMap<>();

    static {
        WING_DINGS_MAP.put('!', 9999);
        WING_DINGS_MAP.put('"', 9986);
        WING_DINGS_MAP.put('#', 9985);
        WING_DINGS_MAP.put('$', 128083);
        WING_DINGS_MAP.put('%', 128365);
        WING_DINGS_MAP.put('&', 128366);
        WING_DINGS_MAP.put('\'', 128367);
        WING_DINGS_MAP.put('(', 9742);
        WING_DINGS_MAP.put(')', 9990);
        WING_DINGS_MAP.put('*', 128386);
        WING_DINGS_MAP.put('+', 128387);
        WING_DINGS_MAP.put(',', 128234);
        WING_DINGS_MAP.put('-', 128235);
        WING_DINGS_MAP.put('.', 128236);
        WING_DINGS_MAP.put('/', 128237);
        WING_DINGS_MAP.put('0', 128193);
        WING_DINGS_MAP.put('1', 128194);
        WING_DINGS_MAP.put('2', 128196);
        WING_DINGS_MAP.put('3', 128463);
        WING_DINGS_MAP.put('4', 128464);
        WING_DINGS_MAP.put('5', 128452);
        WING_DINGS_MAP.put('6', 8987);
        WING_DINGS_MAP.put('7', 128430);
        WING_DINGS_MAP.put('8', 128432);
        WING_DINGS_MAP.put('9', 128434);
        WING_DINGS_MAP.put(':', 128435);
        WING_DINGS_MAP.put(';', 128436);
        WING_DINGS_MAP.put('<', 128427);
        WING_DINGS_MAP.put('=', 128428);
        WING_DINGS_MAP.put('>', 9991);
        WING_DINGS_MAP.put('?', 9997);
        WING_DINGS_MAP.put('@', 128398);
        WING_DINGS_MAP.put('A', 9996);
        WING_DINGS_MAP.put('B', 128076);
        WING_DINGS_MAP.put('C', 128077);
        WING_DINGS_MAP.put('D', 128078);
        WING_DINGS_MAP.put('E', 9756);
        WING_DINGS_MAP.put('F', 9758);
        WING_DINGS_MAP.put('G', 9757);
        WING_DINGS_MAP.put('H', 9759);
        WING_DINGS_MAP.put('I', 9995);
        WING_DINGS_MAP.put('J', 9786);
        WING_DINGS_MAP.put('K', 128528);
        WING_DINGS_MAP.put('L', 9785);
        WING_DINGS_MAP.put('M', 128163);
        WING_DINGS_MAP.put('N', 9760);
        WING_DINGS_MAP.put('O', 9872);
        WING_DINGS_MAP.put('P', 127985);
        WING_DINGS_MAP.put('Q', 9992);
        WING_DINGS_MAP.put('R', 9788);
        WING_DINGS_MAP.put('S', 128167);
        WING_DINGS_MAP.put('T', 10052);
        WING_DINGS_MAP.put('U', 128326);
        WING_DINGS_MAP.put('V', 10014);
        WING_DINGS_MAP.put('W', 128328);
        WING_DINGS_MAP.put('X', 10016);
        WING_DINGS_MAP.put('Y', 10017);
        WING_DINGS_MAP.put('Z', 9770);
        WING_DINGS_MAP.put('[', 9775);
        WING_DINGS_MAP.put(']', 9784);
        WING_DINGS_MAP.put('^', 9800);
        WING_DINGS_MAP.put('_', 9801);
        WING_DINGS_MAP.put('`', 9802);
        WING_DINGS_MAP.put('{', 10048);
        WING_DINGS_MAP.put('|', 10047);
        WING_DINGS_MAP.put('}', 10077);
        WING_DINGS_MAP.put('~', 10078);
        WING_DINGS_MAP.put(' ', (int) ' ');
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        setMax(text.length());
        setConfident(0);

        StringBuilder decoded = new StringBuilder();
        loop:
        for (Integer codePoint : CodePointUtil.codePoints(text)) {
            for (Map.Entry<Character, Integer> entry : WING_DINGS_MAP.entrySet()) {
                if (codePoint.equals(entry.getValue())) {
                    decoded.append(entry.getKey());
                    incConfident();
                    continue loop;
                }
            }
            decoded.append(Character.toChars(codePoint));
        }
        return decoded.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        System.out.println("WingdingCodec.encode");
        System.out.println("text = " + text);
        System.out.println(text.length());

        setMax(text.length());
        setConfident(0);
        StringBuilder encoded = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++) { //Iterating through string
            if (WING_DINGS_MAP.get(text.charAt(i)) != null) { //If that char of string can be found,
                int temp = WING_DINGS_MAP.get(text.charAt(i)); //get the number
                //and convert to real char, add to ArrayList //adding char[], because whole char[] needed for UTF-16 Unicode
                encoded.append((Character.toChars(temp)));
                incConfident();
            } else {
                encoded.append(text.charAt(i));
            }
        }
        return encoded.toString();
    }

    @NonNull
    @Override
    public String getName(Context context) {
        return "Wingding";
    }

}
