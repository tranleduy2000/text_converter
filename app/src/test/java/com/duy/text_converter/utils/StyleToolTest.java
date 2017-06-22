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