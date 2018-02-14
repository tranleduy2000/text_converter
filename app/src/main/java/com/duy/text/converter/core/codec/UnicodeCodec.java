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

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by Duy on 2/10/2018.
 */

public class UnicodeCodec extends CodecImpl {
    @NonNull
    @Override
    public String getName(Context context) {
        return "Unicode";
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        setMax(1);
        setConfident(0);
        try {
            String result = StringEscapeUtils.unescapeJava(text);
            incConfident();
            return result;
        } catch (Exception e) {
            return text;
        }
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        setMax(1);
        setConfident(0);
        try {
            String result = StringEscapeUtils.escapeJava(text);
            incConfident();
            return result;
        } catch (Exception e) {
            return text;
        }
    }
}
