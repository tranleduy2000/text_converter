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

import com.duy.text.converter.core.codec.interfaces.Codec;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Duy on 08-Aug-17.
 */
public class CaesarCodecTest {
    private static final String TO_BE_ENCODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String TO_BE_DECODE = "BCDEFGHIJKLMNOPQRSTUVWXYZA";
    private Codec codec = new CaesarCodec(1);

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
        assertEquals(decode, "! !");
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