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

package com.duy.sharedcode.utils;

/**
 * Created by DUy on 06-Feb-17.
 */

public class ASCIITool {
    public static String asciiToText(String text) {
        String[] arr = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String arg : arr) {
            try {
                char c = (char) Integer.parseInt(arg);
                result.append(c);
            } catch (Exception e) {
                result.append(arg);
            }
        }
        return result.toString();
    }

    public static String textToAscii(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append((int) text.charAt(i)).append(" ");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String original = "Converting ASCII code to char in Java";
        System.out.println(original);
        System.out.println(textToAscii(original));
        System.out.println(asciiToText(textToAscii(original)));
    }
}
