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

package com.duy.text.converter.core.stylish;

import com.duy.text.converter.core.stylish.model.ReplaceEffect;
import com.duy.text.converter.core.stylish.model.Style;

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