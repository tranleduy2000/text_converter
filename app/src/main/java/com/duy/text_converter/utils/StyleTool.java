package com.duy.text_converter.utils;

import java.util.ArrayList;

import static com.duy.text_converter.utils.Style.NORMAL;
import static com.duy.text_converter.utils.Style.SIZE_OF_STYLE;

/**
 * Created by DUy on 07-Feb-17.
 */

public class StyleTool {


    public static String convert(String text, int to) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? Style.get(a, to) : letter;
        }
        return result;
    }

    public static ArrayList<String> convert(String text) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < SIZE_OF_STYLE; i++) {
            arrayList.add(convert(text, i));
        }
        return arrayList;
    }

    public static void main(String[] args) {

    }
}
