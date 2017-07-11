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

package org.apache.commons.codec.binary;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by Duy on 11-Jul-17.
 */
public class Base32Test extends TestCase {

    public void test1() {
        String str = "package org.apache.commons.codec.binary;";
        Base32 base32 = new Base32();
        byte[] encode = base32.encode(str.getBytes());
        System.out.println(Arrays.toString(encode));
        System.out.println(new String(encode));

        byte[] decode = base32.decode(encode);
        assertEquals(str, new String(decode));
    }
}