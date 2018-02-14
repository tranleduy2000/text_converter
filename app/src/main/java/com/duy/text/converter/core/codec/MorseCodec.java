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

import java.util.HashMap;

/**
 * Created by Duy on 15-Jun-17.
 */

public class MorseCodec extends CodecImpl {
    private static final char ALPHABET[] = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final String MORSE_CODE[] = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...",
            "-", "..-", "...-", ".--", "-..-", "-.--", "--..", "/",
            "-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};

    private static final HashMap<Character, String> TEXT_TO_MORSE_CODES;
    private static final HashMap<String, Character> MORSE_CODE_TO_TEXT;

    static {
        TEXT_TO_MORSE_CODES = new HashMap<>();
        MORSE_CODE_TO_TEXT = new HashMap<>();
        for (int i = 0; i < ALPHABET.length; i++) {
            TEXT_TO_MORSE_CODES.put(ALPHABET[i], MORSE_CODE[i]);
            MORSE_CODE_TO_TEXT.put(MORSE_CODE[i], ALPHABET[i]);
        }
        if (TEXT_TO_MORSE_CODES.size() != MORSE_CODE_TO_TEXT.size()) {
            throw new RuntimeException("Wrong size");
        }
    }

    private String textToMorse(String text) {
        text = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        char[] chars = text.toCharArray();
        setMax(chars.length);
        for (int i = 0; i < chars.length; i++) {
            if (TEXT_TO_MORSE_CODES.get(chars[i]) != null) {
                result.append(TEXT_TO_MORSE_CODES.get(chars[i]));
                if (i != chars.length - 1) result.append(" ");
                incConfident();
            } else {
                result.append(chars[i]);
            }
        }
        return result.toString();
    }

    private String morseToText(String text) {
        text = text.toLowerCase();
        String[] chars = text.split("\\s+");
        StringBuilder converted = new StringBuilder();
        setMax(chars);
        for (String aChar : chars) {
            if (MORSE_CODE_TO_TEXT.get(aChar) != null) {
                converted.append(MORSE_CODE_TO_TEXT.get(aChar));
                incConfident();
            } else {
                converted.append(aChar);
            }
        }
        return converted.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return textToMorse(text);
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return morseToText(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "Morse code";
    }
}
