package teach.duy.com.texttool.utils;

import java.math.BigInteger;

/**
 * Created by DUy on 06-Feb-17.
 */

public class HexTool {

    /**
     * convert text to hex
     */
    public static String textToHex(String text) {
        String tmp = String.format("%x", new BigInteger(1, text.getBytes(/*YOUR_CHARSET?*/)));
        StringBuilder result = new StringBuilder();
        if (text.length() == 0) return "";
        for (int i = 0; i < tmp.length(); i += 2) {
            String str = tmp.substring(i, i + 2);
            result.append(str).append(" ");
        }
        return result.toString();
    }

    /**
     * convert text to hex
     */
    public static String hexToTex(String text) {
        StringBuilder result = new StringBuilder();
        String[] arr = text.split(" ");

        for (String arg : arr) {
            try {
                result.append((char) Integer.parseInt(arg, 16));
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
    }
}
