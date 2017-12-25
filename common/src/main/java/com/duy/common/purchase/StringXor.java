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

package com.duy.common.purchase;

import android.support.annotation.NonNull;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Duy on 23-Jul-17.
 */

public class StringXor {
    @NonNull
    public static String encode(String s, String key) {
        return new String(Base64.encodeBase64(xor(s.getBytes(), key.getBytes())));
    }

    @NonNull
    public static String encode(String s) {
        return new String(Base64.encodeBase64((s.getBytes())));
    }

    @NonNull
    public static String decode(String s, String key) {
        return new String(xor(Base64.decodeBase64(s.getBytes()), key.getBytes()));
    }

    @NonNull
    public static String decode(String s) {
        return new String((Base64.decodeBase64(s.getBytes())));
    }

    private static byte[] xor(byte[] src, byte[] key) {
        byte[] out = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            out[i] = (byte) (src[i] ^ key[i % key.length]);
        }
        return out;
    }

}
