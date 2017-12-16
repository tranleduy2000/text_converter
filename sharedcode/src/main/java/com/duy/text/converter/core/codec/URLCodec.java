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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/**
 * Created by Duy on 11-Jul-17.
 */

public class URLCodec extends CodecImpl {
    @NonNull
    @Override
    public String encode(@NonNull String text) {
        org.apache.commons.codec.net.URLCodec urlCodec = new org.apache.commons.codec.net.URLCodec();
        try {
            return urlCodec.encode(text);
        } catch (EncoderException e) {
            e.printStackTrace();
            return text;
        }
    }

    @NonNull
    @Override
    public String decode(@NonNull String text) {
        org.apache.commons.codec.net.URLCodec urlCodec = new org.apache.commons.codec.net.URLCodec();
        try {
            return urlCodec.decode(text);
        } catch (DecoderException e) {
            e.printStackTrace();
            return text;
        }
    }

    @Override
    public String getName(Context context) {
        return null;
    }


}
