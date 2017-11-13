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

package com.duy.sharedcode.codec;

import junit.framework.TestCase;

/**
 * Created by Duy on 28-Aug-17.
 */
public class T9CodecTest extends TestCase {
    public void testDecode() throws Exception {
        String result = new T9Codec().decode("hello");
        System.out.println(result);
        assertEquals("4433555555666", result);
    }

    public void testEncode() throws Exception {

    }

}