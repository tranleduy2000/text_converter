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