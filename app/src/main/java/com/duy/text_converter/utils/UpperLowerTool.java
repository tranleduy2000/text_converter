package teach.duy.com.texttool.utils;

/**
 * Created by DUy on 06-Feb-17.
 */

public class UpperLowerTool {
    public static void main(String[] args) {
        String inp = "adgasdasd";
        System.out.println(inp);
        System.out.println(upperText(inp));
        System.out.println(lowerText(inp));
    }
    public static String upperText(String text) {
        return text.toUpperCase();
    }

    public static String lowerText(String text) {
        return text.toLowerCase();
    }
}
