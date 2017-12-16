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
import android.support.v4.util.Pair;

import com.duy.text.converter.core.codec.interfaces.CodecImpl;

import java.util.ArrayList;

/**
 * Created by Duy on 28-Aug-17.
 */

public class T9Codec extends CodecImpl {
    private static final ArrayList<Pair<String, String>> list = new ArrayList<>();

    static {
        list.clear();
        list.add(new Pair<>("1", ".,?1"));
        list.add(new Pair<>("2", "ABC2"));
        list.add(new Pair<>("3", "DEF3"));
        list.add(new Pair<>("4", "GHI4"));
        list.add(new Pair<>("5", "JKL5"));
        list.add(new Pair<>("6", "MNO6"));
        list.add(new Pair<>("7", "PQRS7"));
        list.add(new Pair<>("8", "TUV8"));
        list.add(new Pair<>("9", "WXYZ"));
        list.add(new Pair<>("0", " 0"));
        list.add(new Pair<>("*", "*"));
        list.add(new Pair<>("#", "#"));
    }

    public T9Codec() {

    }

    private static String duplicate(String input, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(input);
        }
        return result.toString();
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        StringBuilder decoded = new StringBuilder();
        loop:
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            for (Pair<String, String> pair : list) {
                if (pair.second.indexOf(Character.toUpperCase(character)) != -1) {
                    decoded.append(duplicate(pair.first, pair.second.indexOf(Character.toUpperCase(character)) + 1));
                    continue loop;
                }
            }
            decoded.append(character);
        }
        return decoded.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return null;
    }




}
