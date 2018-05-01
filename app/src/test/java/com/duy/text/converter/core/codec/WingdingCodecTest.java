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

import com.duy.text.converter.core.codec.interfaces.Codec;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Duy on 2/13/2018.
 */
public class WingdingCodecTest {
    private static final String TO_BE_ENCODE = "WINGDINGCODECTEST";
    private static final String TO_BE_DECODE = "🕈✋☠☝👎✋☠☝👍⚐👎☜👍❄☜💧❄";
    private Codec codec = new WingdingCodec();

    @Test
    public void encode() throws Exception {
        String encode = codec.encode(TO_BE_ENCODE);
        Assert.assertEquals(encode, TO_BE_DECODE);
    }

    @Test
    public void decode() throws Exception {
        String decode = codec.decode(TO_BE_DECODE);
        Assert.assertEquals(decode, TO_BE_ENCODE);
    }


}