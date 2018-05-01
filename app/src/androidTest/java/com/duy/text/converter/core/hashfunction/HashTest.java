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

package com.duy.text.converter.core.hashfunction;

import org.junit.Test;

/**
 * Created by Duy on 01-May-18.
 */

public class HashTest {

    @Test
    public void md5() {
        new Md5Hash().encode("Hello");
    }

    @Test
    public void sha1() {
        new Sha1Hash().encode("Hello");
    }

    @Test
    public void sha256() {
        new Sha256Hash().encode("Hello");
    }

    @Test
    public void sha384() {
        new Sha384Hash().encode("Hello");
    }

    @Test
    public void sha512() {
        new Sha512Hash().encode("Hello");
    }
}
