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
 * Created by Duy on 08-Aug-17.
 */

public class AtbashCodec extends CodecImpl {
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase();

    public AtbashCodec() {
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = NORMAL.indexOf(Character.toUpperCase(c));
            if (index > -1) {
                char cc = NORMAL.charAt(NORMAL.length() - index - 1);
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
        return decode(text);
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
