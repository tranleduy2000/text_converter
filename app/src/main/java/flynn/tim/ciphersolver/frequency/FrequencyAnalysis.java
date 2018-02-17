package flynn.tim.ciphersolver.frequency;

import java.util.HashMap;

/**
 * @author Tim
 */
class FrequencyAnalysis {

    HashMap analyze(String fa) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        for (int i = 0; i < fa.length(); i++) {
            char c = fa.charAt(i);
            Integer val = map.get(c);
            if (val != null) {
                map.put(c, val + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }
}
