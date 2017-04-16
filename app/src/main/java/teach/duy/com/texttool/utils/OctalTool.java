package teach.duy.com.texttool.utils;

import android.util.Log;

/**
 * Created by DUy on 06-Feb-17.
 */

public class OctalTool {
    private static final String TAG = "OctalTool";

    public static void main(String[] args) {
        String ori = "Octal to text in Java";
        System.out.println(ori);
        System.out.println(textToOctal(ori));
        System.out.println(octalToText(textToOctal(ori)));
    }

    public static String textToOctal(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            result.append(Integer.toOctalString(c));
            result.append(" ");
        }
        Log.d(TAG, "textToOctal: " + result.toString());
        return result.toString();
    }

    public static String octalToText(String text) {
        String[] arr = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String arg : arr) {
            try {
                char c = (char) Integer.parseInt(arg, 8);
                result.append(c);
            } catch (Exception e) {
                result.append(" ").append(arg).append(" ");
            }
        }
        return result.toString();
    }
}
