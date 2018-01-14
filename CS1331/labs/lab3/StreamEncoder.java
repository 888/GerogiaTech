import java.util.Arrays;
import java.util.stream.Stream;

/**
 * See PDF for instructions.
 *
 * @author Noam Lerner
 * @version 6:00-7:30 Section
 */
public class StreamEncoder {

    /* Modify this method */
    public static String decodeSentence(String sentence) {
        return getWordStream(sentence).map(e -> decodeWord(e))
            .reduce("", (a, b) -> a + b + " ");
    }

    /** Provided main method for testing */
    public static void main(String[] args) {
        System.out.println(decodeSentence("s@?VE rC@DD %96 $EC62>DP"));
    }

    /** DO NOT MODIFY THIS METHOD */
    private static Stream<String> getWordStream(String sentence) {
        return Arrays.stream(sentence.split("\\s"));
    }

    /** DO NOT MODIFY THIS METHOD */
    private static String decodeWord(String s) {
        char[] characters = s.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            characters[i] = (char) ((characters[i] - 33 + 47) % 94 + 33);
        }
        return String.valueOf(characters);
    }
}
