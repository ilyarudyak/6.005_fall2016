package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    private static Stream<String> words = Utils.getWords();
    public static String base26First10000 = Utils.readPiBase26First10000();


    public static void main(String[] args) {

        Map<String, Integer> wordsInPi = WordFinder.getSubstrings(base26First10000, words);

        wordsInPi.forEach( (word, index) -> {
            if (word.length() >= 4) {
                System.out.print(word + " ");
            }
        });
        System.out.println();

    }
}















