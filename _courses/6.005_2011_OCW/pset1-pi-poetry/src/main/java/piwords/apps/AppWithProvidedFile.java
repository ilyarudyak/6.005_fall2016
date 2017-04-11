package piwords.apps;

import piwords.extra.DigitsFrequency;
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
    private static String base26First10000WithFreq = DigitsFrequency.getPiBase26WithFreq(10000);


    private static void printLongWords(String piStr, Source source) {

        Map<String, Integer> wordsInPi = new HashMap<>();

        switch (source) {
            case WORDS:
                wordsInPi = WordFinder.getSubstrings(piStr, words);
                break;
            case SONNETS:
                wordsInPi = WordFinder.getSubstrings(piStr, sonnetWords);
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
//        printLongWords(base26First10000, Source.WORDS);

        // hate comb sail rule smoke boat
//        printLongWords(base26First10000WithFreq, Source.WORDS);


        //hell want steel dear mind laid hold sees away best sour herd
//        printLongWords(base26First10000, Source.SONNETS);

        // rank hate being wane spot hurt hems born boat hide mend ruth sail toil smoke
        printLongWords(base26First10000WithFreq, Source.SONNETS);


    }
}















