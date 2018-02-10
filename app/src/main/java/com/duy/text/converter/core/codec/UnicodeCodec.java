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

import com.duy.text.converter.core.codec.interfaces.Codec;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by Duy on 2/10/2018.
 */

public class UnicodeCodec implements Codec {
    @NonNull
    @Override
    public String getName(Context context) {
        return "Unicode";
    }

    @Override
    public int getMax() {
        return 0;
    }

    @Override
    public int getConfident() {
        return 0;
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        return StringEscapeUtils.unescapeJava(text);
    }

    @NonNull
    @Override
    public String encode(@NonNull String text) {
        return StringEscapeUtils.escapeJava(text);
    }
}
