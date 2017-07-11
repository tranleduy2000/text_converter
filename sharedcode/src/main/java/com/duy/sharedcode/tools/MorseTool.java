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

package com.duy.sharedcode.tools;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Duy on 15-Jun-17.
 */

public class MorseTool implements Encoder, Decoder {
    public static final char ALPHABET[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};

    public static final String MORSE_CODE[] = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...",
            "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "/"};
    public static final HashMap<Character, String> TEXT_TO_MORSE_CODES;
    public static final HashMap<String, Character> MORSE_CODE_TO_TEXT;

    static {
        TEXT_TO_MORSE_CODES = new HashMap<>();
        MORSE_CODE_TO_TEXT = new HashMap<>();
        for (int i = 0; i < ALPHABET.length; i++) {
            TEXT_TO_MORSE_CODES.put(ALPHABET[i], MORSE_CODE[i]);
            MORSE_CODE_TO_TEXT.put(MORSE_CODE[i], ALPHABET[i]);
        }
    }

    public static String textToMorse(String text) {
        text = text.toLowerCase();
        StringBuilder converted = new StringBuilder();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (TEXT_TO_MORSE_CODES.get(chars[i]) != null) {
                converted.append(TEXT_TO_MORSE_CODES.get(chars[i]));
                if (i != chars.length - 1) {
                    converted.append(" ");
                }
            } else {
                converted.append(chars[i]);
            }
        }
        return converted.toString();
    }

    public static String morseToText(String text) {
        text = text.toLowerCase();
        String[] chars = text.split("\\s+");
        StringBuilder converted = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            if (MORSE_CODE_TO_TEXT.get(chars[i]) != null) {
                converted.append(MORSE_CODE_TO_TEXT.get(chars[i]));
            } else {
                converted.append(chars[i]);
            }
        }
        return converted.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        try {
            return textToMorse(text);
        } catch (Exception e) {
            return text;
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        try {
            return morseToText(text);
        } catch (Exception e) {
            return text;
        }
    }
}
