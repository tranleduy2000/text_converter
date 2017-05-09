package teach.duy.com.texttool.utils;

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
