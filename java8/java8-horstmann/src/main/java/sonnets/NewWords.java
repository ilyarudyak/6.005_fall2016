package sonnets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by ilyarudyak on 11/4/16.
 */
public class NewWords {

//    static Set<String> sonnetWords = readWords("src/main/resources/sonnets/sonnet_words.txt");
    static Set<String> sonnetWords;

    static {
        try {
            sonnetWords = ExtractSonnetWords.extractWords("src/main/resources/sonnets/sonnet_words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Set<String> dictWords = readWords("src/main/resources/sonnets/sowpods.txt");

    public static void main(String[] args) {

        // check if a sonnet word is NOT in dictionary words
        Set<String> newWords = sonnetWords.stream()
                .filter(word -> !dictWords.contains(word))
                .collect(toSet());

        newWords.stream().sorted().forEach(System.out::println);
        System.out.println("total new words: " + newWords.size());

    }

    private static Set<String> readWords(String fileName) {
        try {
            return Files.lines(Paths.get(fileName))
                    .collect(toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }
}
