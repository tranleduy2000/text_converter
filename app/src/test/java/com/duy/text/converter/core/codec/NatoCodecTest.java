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

import junit.framework.TestCase;

/**
 * Created by Duy on 11/21/2017.
 */
public class NatoCodecTest extends TestCase {

    public void testDecode() throws Exception {
        NatoCodec natoCodec = new NatoCodec();

        String decoded = natoCodec.decode("Alpha One Zero Three Zulu Nine Nine One");
        assertEquals(decoded, "A103Z991");
    }

    public void testEncode() throws Exception {
        NatoCodec natoCodec = new NatoCodec();

        String encoded = natoCodec.encode("A103Z991");
        assertEquals(encoded, "Alpha One Zero Three Zulu Nine Nine One");
    }

}