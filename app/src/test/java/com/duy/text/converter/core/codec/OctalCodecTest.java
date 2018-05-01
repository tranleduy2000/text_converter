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

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Duy on 2/10/2018.
 */
public class OctalCodecTest {
    private static final String TO_BE_ENCODE = "OctalCodecTest";
    private static final String TO_BE_DECODE = "117 143 164 141 154 103 157 144 145 143 124 145 163 164";
    private OctalCodec codec = new OctalCodec();

    @Test
    public void encode() throws Exception {
        Assert.assertEquals(codec.encode(TO_BE_ENCODE), TO_BE_DECODE);
    }

    @Test
    public void decode() throws Exception {
        Assert.assertEquals(codec.decode(TO_BE_DECODE), TO_BE_ENCODE);
    }

    @Test
    public void encodeUtf16() {
        String utf16Str = AsciiCodecTest.UTF_16_STR;
        String encode = codec.encode(utf16Str);
        assertEquals("372247", encode);
    }

    @Test
    public void decodeUtf16() {
        String utf16Str = AsciiCodecTest.UTF_16_STR;
        String encode = codec.encode(utf16Str);
        assertEquals(utf16Str, codec.decode(encode));
    }
}