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

package com.duy.text.converter.core.hashfunction;

import org.junit.Test;

/**
 * Created by Duy on 01-May-18.
 */

public class HashTest {
    @Test
    public void md2() {
        new Md2Hash().encode("Hello");
    }

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
