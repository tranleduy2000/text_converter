package com.duy.text_converter.utils;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Duy on 07-Jun-17.
 */
public class EffectToolTest extends TestCase {
    public void testConvert() {
        ArrayList<String> converted = EffectTool.convert("package com.duy.text_converter.utils;");
        for (String s : converted) {
            System.out.println(s);
        }
    }

    public void test1() {
        String effect = EffectTool.EFFECTS[0];
        for (int i = 0; i < effect.length(); i++) {
            System.out.println(i + " " + effect.charAt(i));
        }
    }
}