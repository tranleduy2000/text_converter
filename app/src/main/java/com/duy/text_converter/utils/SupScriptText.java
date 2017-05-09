package com.duy.text_converter.utils;

import static com.duy.text_converter.utils.Style.NORMAL;
import static com.duy.text_converter.utils.Style.SUPPER_SCRIPT;

/**
 * Created by Duy on 05-May-17.
 */

public class SupScriptText {
    public static String textToSup(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? SUPPER_SCRIPT.charAt(a) : letter;
        }
        return result;
    }

    public static String supToText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = SUPPER_SCRIPT.indexOf(letter);
            result += (a != -1) ? NORMAL.charAt(a) : letter;
        }
        return result;
    }

    public static void main(String[] args) {
        String x = textToSup("abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        System.out.println(x);
        System.out.println(supToText(x));
    }

}
