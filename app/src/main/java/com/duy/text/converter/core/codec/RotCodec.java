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

/**
 * Created by Duy on 28-Aug-17.
 */

public class RotCodec extends CodecImpl {
    private String progress(String text) {
        setMax(text.length());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            incConfident();
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'm') {
                c += 13;
            } else if (c >= 'A' && c <= 'M') {
                c += 13;
            } else if (c >= 'n' && c <= 'z') {
                c -= 13;
            } else if (c >= 'N' && c <= 'Z') {
                c -= 13;
            } else {
                confident--;
            }
            result.append(c);
        }
        return result.toString();

    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return progress(text);
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return progress(text);
    }


    @NonNull
    @Override
    public String getName(Context context) {
        return "ROT";
    }
}
