package teach.duy.com.texttool.utils;

/**
 * Created by DUy on 06-Feb-17.
 */

public class BinaryTool {

    /**
     * convert text to binary
     * foo ->  01100110 01101111 01101111
     */
    public static String textToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    /**
     * convert binary to text
     * 01100110 01101111 01101111 -> foo
     *
     * @param text
     * @return
     */
    public static String binaryToText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] arr = text.split(" ");
        for (String arg : arr) {
            try {
                int charCode = Integer.parseInt(arg, 2);
                stringBuilder.append((char) charCode);
            } catch (Exception e) {
                stringBuilder.append(" ").append(arg).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String inp = "tranleduy";
        String binary = textToBinary(inp);
        System.out.println("Text to binary: " + inp + " ->> " + binary);
        System.out.println("Binary to text: " + binary + " ->> " + binaryToText(binary));
    }
}
