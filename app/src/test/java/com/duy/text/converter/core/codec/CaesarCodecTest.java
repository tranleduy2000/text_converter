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

import com.duy.text.converter.core.codec.interfaces.Codec;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Duy on 08-Aug-17.
 */
public class CaesarCodecTest {
    private static final String TO_BE_ENCODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String TO_BE_DECODE = "XYZABCDEFGHIJKLMNOPQRSTUVW";
    private Codec codec = new CaesarCodec();

    @Test
    public void testDecode() throws Exception {
        String inp = "Ifmq xf bsf cfjoh buubdlfe";
        String decode = codec.decode(inp);
        System.out.println("decode = " + decode);
        assertEquals(decode, "Help we are being attacked");
    }

    @Test
    public void testDecode2() throws Exception {
        String inp = "! !";
        String decode = codec.decode(inp);
        System.out.println("decode = '" + decode + "'");
        assertEquals(decode, "   ");
    }

    @Test
    public void testDecode3() throws Exception {
        String inp = (char) 19 + "";
        String decode = codec.decode(inp);
        System.out.println("decode = '" + decode + "'");
        assertEquals(decode, "\u0013");
    }

    @Test
    public void testEncode() throws Exception {
        String inp = "Help we are being attacked";
        String encode = codec.encode(inp);
        assertEquals(encode, "Ifmq xf bsf cfjoh buubdlfe");
    }

    @Test
    public void testEncodeOverflow() throws Exception {
        String inp = "~";
        String encode = codec.encode(inp);
        assertEquals(encode, "~");
    }

    @Test
    public void encode() {
        String encode = codec.encode(TO_BE_ENCODE);
        assertEquals(TO_BE_DECODE, encode);
    }

    @Test
    public void decode() {

    }

}