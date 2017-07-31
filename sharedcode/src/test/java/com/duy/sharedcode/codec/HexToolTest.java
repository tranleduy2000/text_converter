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

package com.duy.sharedcode.codec;

import junit.framework.TestCase;

/**
 * Created by Duy on 31-Jul-17.
 */
public class HexToolTest extends TestCase {

    public void testEncode() {
        String res = new HexTool().encode("Hello");
        System.out.println(res);
        assertEquals(res, "48 65 6c 6c 6f ");
    }

    public void testDecode() {
        String input = "Hello";
        String res = new HexTool().encode(input);
        System.out.println(res);
        assertEquals(new HexTool().decode(res), input);
    }

    public void testChinese() {
        String input = "汉字/漢字";
        String res = new HexTool().encode(input);
        System.out.println("encode " + res);

        String decode = new HexTool().decode(res);
        System.out.println("decode " + decode);
        assertEquals(decode, input);
    }
}