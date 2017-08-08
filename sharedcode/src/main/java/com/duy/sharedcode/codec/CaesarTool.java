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

import android.support.annotation.NonNull;

/**
 * Created by Duy on 08-Aug-17.
 */

public class CaesarTool implements Encoder, Decoder {
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase();
    private String key = "";
    private int offset = 1;

    public CaesarTool() {
        this(1);
    }

    public CaesarTool(int offset) {
        this.offset = offset;
        key = "";
        for (int i = 0; i < NORMAL.length(); i++) {
            key += NORMAL.charAt((i + offset) % NORMAL.length());
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = key.indexOf(Character.toUpperCase(c));
            if (index > -1) {
                char cc = NORMAL.charAt(index % NORMAL.length());
                if (Character.isUpperCase(c)) {
                    encoded.append(Character.toUpperCase(cc));
                } else {
                    encoded.append(Character.toLowerCase(cc));
                }
            } else {
                encoded.append(c);
            }
        }
        return encoded.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = NORMAL.indexOf(Character.toUpperCase(c));
            if (index > -1) {
                char cc = key.charAt(index % key.length());
                if (Character.isUpperCase(c)) {
                    encoded.append(Character.toUpperCase(cc));
                } else {
                    encoded.append(Character.toLowerCase(cc));
                }
            } else {
                encoded.append(c);
            }
        }
        return encoded.toString();
    }
}
