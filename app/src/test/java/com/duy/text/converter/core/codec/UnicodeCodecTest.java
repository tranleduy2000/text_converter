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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Duy on 2/10/2018.
 */
public class UnicodeCodecTest {
    private UnicodeCodec codec = new UnicodeCodec();
    private String toBeEncode = "برنامه نویسی";
    private String toBeDecode = "\\u0628\\u0631\\u0646\\u0627\\u0645\\u0647 \\u0646\\u0648\\u06CC\\u0633\\u06CC";

    @Test
    public void decode() throws Exception {
        String decode = codec.decode(toBeDecode);
        assertEquals(decode, toBeEncode);
    }

    @Test
    public void encode() throws Exception {
        String result = codec.encode(toBeEncode);
        assertEquals(result, toBeDecode);
    }

    @Test
    public void encode_error() throws Exception {
        String result = codec.encode(Character.toString((char) 1));
        assertEquals(result, "\\u0001");
    }

    @Test
    public void decode_error() throws Exception {
        String result = codec.decode("\u0001");
        assertEquals(result, "\u0001");
    }

}