package teach.duy.com.texttool.utils;

import static teach.duy.com.texttool.utils.Style.FLIP;
import static teach.duy.com.texttool.utils.Style.NORMAL;

/**
 * Created by DUy on 06-Feb-17.
 */

public class UpsideDownTool {

    /**
     * up side down text
     * <p>
     * hello world, i'm dennis -> sıuuǝp ɯ,ı 'p1ɹoʍ o11ǝɥ
     */
    public static String upsideDownText(String text) {
        String result = "";
        char letter;
        for (int i = 0; i < text.length(); i++) {
            letter = text.charAt(i);
            int a = NORMAL.indexOf(letter);
            result += (a != -1) ? FLIP.charAt(a) : letter;
        }
        return new StringBuilder(result).reverse().toString();
    }

    public static void main(String[] args) {
        String inp = "\"upside down\" will create the illusion of an upside down page/text via string reversal and character substitution for letters a to z.";
        System.out.println("Test case 1: ");
        System.out.println(inp);
        System.out.println(upsideDownText(inp));
        System.out.println(upsideDownText(upsideDownText(inp)));

        System.out.println("Test case 2: ");
        inp = "abcdefghijklmnopqrstuvwxyz'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        System.out.println(inp);
        System.out.println(upsideDownText(inp));
        System.out.println(upsideDownText(upsideDownText(inp)));
    }
}
