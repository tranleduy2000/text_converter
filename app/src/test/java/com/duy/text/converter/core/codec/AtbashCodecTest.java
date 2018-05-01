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
 * Created by Duy on 10-Aug-17.
 */
public class AtbashCodecTest extends TestCase {
    public void testDecode() throws Exception {
        String abcdef = new AtbashCodec().encode("ABCDEF");
        System.out.println(abcdef);
        String decode = new AtbashCodec().decode(abcdef);
        System.out.println("decode = " + decode);
        assertEquals("ABCDEF", decode);
    }

    public void testEncode() throws Exception {

    }

}