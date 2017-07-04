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

/**
 * Created by DUy on 06-Feb-17.
 */

public class ReverserTool {
    public static void main(String[] args) {
        String inp = "Reverse String Word by Word in Java";
        System.out.println(inp);
        System.out.println(reverseText(inp));
        System.out.println(reverseText(reverseText(inp)));
        System.out.println(reverseWord(inp));
        System.out.println(reverseWord(reverseWord(inp)));
    }

    /**
     * reverse Text
     * abc def -> fed cba
     */
    public static String reverseText(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    public static String reverseWord(String text) {
        StringBuilder result = new StringBuilder("");
        int end = text.length();
        int counter = text.length() - 1;
        for (int i = text.length() - 1; i >= 0; i--) {
            if (text.charAt(i) == ' ' || i == 0) {
                if (i != 0) {
                    result.append(text.substring(i + 1, end));
                    result.append(" ");
                } else {
                    result.append(text.substring(i, end));
                }
                end = counter;
            }
            counter--;
        }
        return result.toString();
    }


}
