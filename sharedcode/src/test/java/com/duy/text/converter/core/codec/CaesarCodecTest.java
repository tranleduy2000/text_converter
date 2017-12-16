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
 * Created by Duy on 08-Aug-17.
 */
public class CaesarCodecTest extends TestCase {
    public void testDecode() throws Exception {
        String inp = "Ifmq xf bsf cfjoh buubdlfe";
        String decode = new CaesarCodec().decode(inp);
        System.out.println("decode = " + decode);
        assertEquals(decode, "Help we are being attacked");
    }

    public void testEncode() throws Exception {
        String inp = "Help we are being attacked";
        String encode = new CaesarCodec().encode(inp);
        assertEquals(encode, "Ifmq xf bsf cfjoh buubdlfe");
    }

}