package piwords.apps;

import piwords.utils.Utils;
import piwords.main.WordFinder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/14/16.
 */
public class AppWithProvidedFile {

    private enum Source {WORDS, SONNETS}

    private static Stream<String> words = Utils.getWords();
    private static Stream<String> sonnetWords = Utils.getSonnetWords();
    private static String base26First10000 = Utils.readPiBase26First10000();


    private static void printLongWords(Source source) {

        Map<String, Integer> wordsInPi = new HashMap<>();

        switch (source) {
            case WORDS:
                wordsInPi = WordFinder.getSubstrings(base26First10000, words);
                break;
            case SONNETS:
                wordsInPi = WordFinder.getSubstrings(base26First10000, sonnetWords);
                break;
            default:
                throw new IllegalArgumentException();
        }

        wordsInPi.forEach( (word, index) -> {
            if (word.length() >= 4) {
                System.out.print(word + " ");
            }
        });
        System.out.println();
    }

    public static void main(String[] args) {

        //wash steel dear mind tail knee
        printLongWords(Source.WORDS);

        //hell want steel dear mind laid hold sees away best sour herd
        printLongWords(Source.SONNETS);

    }
}















