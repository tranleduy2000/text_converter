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

import java.util.Arrays;

/**
 * Created by Duy on 08-Aug-17.
 */

public class CaesarCodec implements Codec {
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase();
    private String key = "";

    public CaesarCodec() {
        this(1);
    }

    private CaesarCodec(int offset) {
        key = "";
        for (int i = 0; i < NORMAL.length(); i++) {
            key += NORMAL.charAt((i + offset) % NORMAL.length());
        }
    }


    @NonNull
    @Override
    public String decode(@NonNull String text) {
        StringBuilder encoded = new StringBuilder();
        String[] args = text.split(" ");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            for (char c : arg.toCharArray()) {
                encoded.append((char) (c - 1));
            }
            if (i != args.length - 1) {
                encoded.append(" ");
            }
        }
        return encoded.toString();
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        StringBuilder encoded = new StringBuilder();
        String[] args = text.split(" ");
        System.out.println("args = " + Arrays.toString(args));
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            for (char c : arg.toCharArray()) {
                encoded.append((char) (c + 1));
            }
            if (i != args.length - 1) {
                encoded.append(" ");
            }
        }
        return encoded.toString();
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
