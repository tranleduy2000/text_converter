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

import static com.duy.text.converter.core.codec.AsciiCodecTest.UTF_16_STR;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Duy on 02-Aug-17.
 */
public class BinaryCodecTest {
    BinaryCodec codec = new BinaryCodec();

    @Test
    public void testEncode() throws Exception {
        String encode = codec.encode("汉语/漢語; Hànyǔ or 中文; Zhōngwén");
        System.out.println(encode);
        assertEquals("0110110001001001 1000101111101101 00101111 0110111100100010 1000101010011110 00111011 00100000 01001000 11100000 01101110 01111001 0000000111010100 00100000 01101111 01110010 00100000 0100111000101101 0110010110000111 00111011 00100000 01011010 01101000 0000000101001101 01101110 01100111 01110111 11101001 01101110", encode);
    }

    @Test
    public void testDecode() throws Exception {
        String expected = "汉语/漢語; Hànyǔ or 中文; Zhōngwén";
        String decode = codec.decode(codec.encode(expected));
        assertEquals(expected, decode);
    }

    @Test
    public void testEncode2() {
        String encoded = new BinaryCodec().encode("2");
        assertEquals(encoded, "00110010");
    }

    @Test
    public void encodeUtf16() {
        String expected = UTF_16_STR;
        String encoded = codec.encode(expected);
        assertEquals("000000011111010010100111", encoded);
    }

    @Test
    public void decodeUtf16() {
        String expected = UTF_16_STR;
        String decode = codec.decode("000000011111010010100111");
        assertEquals(expected, decode);
    }

}