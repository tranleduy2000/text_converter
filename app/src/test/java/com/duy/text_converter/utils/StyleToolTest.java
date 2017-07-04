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

package com.duy.text_converter.utils;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */
public class StyleToolTest extends TestCase {
    public void testConvert() {
        ArrayList<String> converted = StyleTool.convert("package com.duy.text_converter.utils;");
        for (String s : converted) {
            System.out.println(s);
        }
    }

    public void test1() {
        String effect = StyleTool.STYLES.get(0);
        for (int i = 0; i < effect.length(); i++) {
            System.out.println(i + " " + effect.charAt(i));
        }
    }

    public void test2() {
        ArrayList<String> converted = StyleTool.convert("How developers work\n" +
                "Support your workflow with lightweight tools and features. Then work how you work best—we'll follow your lead.");
        for (String s : converted) {
            System.out.println(s);
        }
    }


    public void testLength() {
        int size = StyleTool.STYLES.get(0).length();
        for (String style : StyleTool.STYLES) {
            if (style.length() != size) {
                System.out.println(style + " " + size);
                assertTrue(false);
            }
        }
        assertTrue(true);
    }
}