package flynn.tim.ciphersolver.frequency;

/**
 * Created by Tim on 8/19/2015.
 */
public class FrequencyPair {
    private String character;
    private int value;

    public FrequencyPair(String character, int value) {
        this.character = character;
        this.value = value;
    }

    public String getCharacter() {
        return this.character;
    }

    public int getValue() {
        return this.value;
    }
}
