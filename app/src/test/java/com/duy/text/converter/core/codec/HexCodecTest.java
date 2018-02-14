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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Duy on 31-Jul-17.
 */
public class HexCodecTest {
    private HexCodec codec = new HexCodec();

    @Test
    public void testEncode() {
        String res = new HexCodec().encode("Hello");
        System.out.println(res);
        assertEquals(res, "48 65 6C 6C 6F");
    }

    @Test
    public void testDecode() {
        String input = "Hello";
        String res = new HexCodec().encode(input);
        System.out.println(res);
        assertEquals(new HexCodec().decode(res), input);
    }

    @Test
    public void hex_encode_chinese() {
        String input = "汉字/漢字";
        String res = new HexCodec().encode(input);
        System.out.println("encode " + res);

        String decode = new HexCodec().decode(res);
        System.out.println("decode " + decode);
        assertEquals(decode, input);
    }

    @Test
    public void encodeUtf16() {
        String utf16Str = AsciiCodecTest.UTF_16_STR;
        String encode = codec.encode(utf16Str);
        assertEquals("1F4A7", encode);
    }

    @Test
    public void decodeUtf16() {
        String utf16Str = AsciiCodecTest.UTF_16_STR;
        String encode = codec.encode(utf16Str);
        assertEquals(utf16Str, codec.decode(encode));
    }
}