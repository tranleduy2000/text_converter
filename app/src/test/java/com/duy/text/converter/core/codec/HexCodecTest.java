/*
 * Copyright (C)  2017-2018 Tran Le Duy
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
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