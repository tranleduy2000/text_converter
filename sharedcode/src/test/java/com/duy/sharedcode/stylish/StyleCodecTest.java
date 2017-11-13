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

package com.duy.sharedcode.stylish;

import com.duy.sharedcode.stylish.model.ReplaceEffect;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */
public class StyleCodecTest extends TestCase {
    public void testConvert() {
        ArrayList<Style> style = ReplaceEffect.createStyle();
        for (Style style1 : style) {
            System.out.println(style1.generate("package com.duy.text_converter.utils;"));
        }
    }

    public void test1() {
        String effect = ReplaceEffect.STYLES.get(0);
        for (int i = 0; i < effect.length(); i++) {
            System.out.println(i + " " + effect.charAt(i));
        }
    }

    public void testLength() {
        int size = ReplaceEffect.STYLES.get(0).length();
        System.out.println("size = " + size);
        for (String style : ReplaceEffect.STYLES) {
            System.out.println(style);
            if (style.length() != size) {
                System.out.println(style + " " + style.length());
                assertTrue(false);
            }
        }
        assertTrue(true);
    }
}