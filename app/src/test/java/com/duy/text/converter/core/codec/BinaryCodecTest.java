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
 * Created by Duy on 02-Aug-17.
 */
public class BinaryCodecTest {
    @Test
    public void testEncode() throws Exception {
        String encode = new BinaryCodec().encode("\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n");
        System.out.println(encode);
        assertEquals("0110110001001001 1000101111101101 00101111 0110111100100010 1000101010011110 00111011 00100000 01001000 11100000 01101110 01111001 0000000111010100 00100000 01101111 01110010 00100000 0100111000101101 0110010110000111 00111011 00100000 01011010 01101000 0000000101001101 01101110 01100111 01110111 11101001 01101110", encode);
    }

    @Test
    public void testDecode() throws Exception {
        String decode = new BinaryCodec().decode(
                new BinaryCodec().encode("\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n"));
        System.out.println(decode);
        assertEquals(decode, "\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n");
    }

    @Test
    public void testEncode2() {
        String encoded = new BinaryCodec().encode("2");
        assertEquals(encoded, "00110010");
    }

}