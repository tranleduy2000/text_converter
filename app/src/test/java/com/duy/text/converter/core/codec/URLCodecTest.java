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
import com.duy.text.converter.core.codec.interfaces.CodecMethod;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Duy on 11-Jul-17.
 */
public class URLCodecTest {
    private static final String TO_BE_ENCODE = "https://commons.apache.org/proper/commons-codec/apidocs/org/apache/commons/codec/net/URLCodec.html";
    private Codec codec = CodecMethod.URL.getCodec();

    @Test
    public void encode() {
        String encode = codec.encode(TO_BE_ENCODE);
        String decode = codec.decode(encode);

        Assert.assertEquals(decode, TO_BE_ENCODE);
    }

}