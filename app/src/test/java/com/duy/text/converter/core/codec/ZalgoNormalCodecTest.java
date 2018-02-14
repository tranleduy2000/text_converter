/*
 * Copyright (c) 2018 by Tran Le Duy
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

import com.duy.text.converter.core.codec.interfaces.Codec;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Duy on 2/14/2018.
 */
public class ZalgoNormalCodecTest {
    private static final String TO_BE_ENCODE = "ZalgoBigCodecTest";
    private Codec codec = new ZalgoNormalCodec();

    @Test
    public void encode() {
        String encode = codec.encode(TO_BE_ENCODE);
        System.out.println("encode = " + encode);
        assertNotNull(encode);
    }

    @Test
    public void decode() {
        String encode = codec.encode(TO_BE_ENCODE);
        String decode = codec.decode(encode);
        System.out.println("decode = " + decode);
        assertEquals(TO_BE_ENCODE, decode);
    }
}