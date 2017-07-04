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