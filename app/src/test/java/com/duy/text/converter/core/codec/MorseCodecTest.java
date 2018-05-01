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

import static org.junit.Assert.assertEquals;

/**
 * Created by Duy on 15-Jun-17.
 */
public class MorseCodecTest {
    private static final String TO_BE_ENCODE = "morsecodectest0123456789";
    private static final String TO_BE_DECODE = "-- --- .-. ... . -.-. --- -.. . -.-. - . ... - ----- .---- ..--- ...-- ....- ..... -.... --... ---.. ----.";
    private MorseCodec codec = new MorseCodec();

    @Test
    public void encode() throws Exception {
        assertEquals(codec.encode(TO_BE_ENCODE), TO_BE_DECODE);
    }

    @Test
    public void decode() throws Exception {
        assertEquals(codec.decode(TO_BE_DECODE), TO_BE_ENCODE);
    }

    @Test
    public void decode2() {
        assertEquals("-- --- .-. ... . -.-. --- -.. . -.-. - . ... - ----- .---- ..--- ...-- ....- ..... -.... --... ---.. ----.",
                codec.encode(TO_BE_ENCODE));
    }

    @Test
    public void encode2() {
        assertEquals(codec.encode("qwertyuiopasdfghjklzxcvbnm1234567890"),
                "--.- .-- . .-. - -.-- ..- .. --- .--. .- ... -.. ..-. --. .... .--- -.- .-.." +
                        " --.. -..- -.-. ...- -... -. -- .---- ..--- ...-- ....- ..... -.... --... -" +
                        "--.. ----. -----");
    }

    @Test
    public void decode3(){
        assertEquals("--.- .-- . .-. - -.-- ..- .. --- .--. .- ... -.. ..-. --. .... .--- -" +
                        ".- .-.. --.. -..- -.-. ...- -... -. -- .---- ..--- ...-- ....- ..... -.... -" +
                        "-... ---.. ----. -----",
                codec.encode("qwertyuiopasdfghjklzxcvbnm1234567890"));
    }
}