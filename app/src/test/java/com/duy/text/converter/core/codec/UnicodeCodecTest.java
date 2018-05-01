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