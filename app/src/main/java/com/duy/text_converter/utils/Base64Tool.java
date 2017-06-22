package com.duy.text_converter.utils;

import android.util.Base64;

import java.nio.charset.Charset;

/**
 * Created by Duy on 22-Jun-17.
 */

public class Base64Tool {

    public static String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes(), Base64.DEFAULT);
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }


    public static String base64Decode(String token) {
        byte[] decodedBytes = Base64.decode(token.getBytes(), Base64.DEFAULT);
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }
}
