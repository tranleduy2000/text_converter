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

import junit.framework.TestCase;

/**
 * Created by Duy on 01-May-18.
 */
public class Md2HashTest extends TestCase {
    public void testEncode() throws Exception {
        Md2Hash hash = new Md2Hash();
        assertEquals(hash.encode("Hello"), "b27af65e6a4096536dd1252e308c2427");
    }

}