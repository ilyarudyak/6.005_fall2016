package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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

        printLongWords(Source.WORDS);
        printLongWords(Source.SONNETS);

    }
}















