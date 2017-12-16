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

import junit.framework.TestCase;

/**
 * Created by Duy on 02-Aug-17.
 */
public class BinaryCodecTest extends TestCase {
    public void testEncode() throws Exception {
        String encode = new BinaryCodec().encode("\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n");
        System.out.println(encode);
        assertEquals("110110001001001 1000101111101101 101111 110111100100010 1000101010011110 111011 100000 1001000 11100000 1101110 1111001 111010100 100000 1101111 1110010 100000 100111000101101 110010110000111 111011 100000 1011010 1101000 101001101 1101110 1100111 1110111 11101001 1101110 ", encode);
    }

    public void testDecode() throws Exception {
        String decode = new BinaryCodec().decode(
                new BinaryCodec().encode("\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n"));
        System.out.println(decode);
        assertEquals(decode, "\u6c49\u8bed/\u6f22\u8a9e; H\u00e0ny\u01d4 or \u4e2d\u6587; Zh\u014dngw\u00e9n");
    }

}