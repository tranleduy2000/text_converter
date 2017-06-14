package com.duy.text_converter.utils;

import junit.framework.TestCase;

/**
 * Created by Duy on 15-Jun-17.
 */
public class MorseToolTest extends TestCase {
    public void testMorseToText() throws Exception {
        String res = MorseTool.morseToText("-- --- .-. ... . / -.-. --- -.. . / - .-. .- -. ... .-.. .- - --- .-.");
        assertEquals(res, "Morse Code Translator".toLowerCase());
    }

    public void testTextToMorse() throws Exception {
        String res = MorseTool.textToMorse("Morse Code Translator");
        assertEquals(res, "-- --- .-. ... . / -.-. --- -.. . / - .-. .- -. ... .-.. .- - --- .-.");
    }

}