package sonnets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ilyarudyak on 11/5/16.
 */
public class ExtractSonnetWords {

    public static Set<String> extractWords(String filename) throws IOException {

        Stream<String> lines = Files.lines(Paths.get(filename))
                .map(line -> line.replaceAll("-", " ").trim())
                .filter(line -> line.length() > 1);

        Set<String> sonnetWords = lines.map(line -> line.split("\\s"))
                .flatMap(words -> stripPunctuation(words))
                .map(String::toUpperCase)
                .collect(toSet());

        return sonnetWords;
    }

    private static Stream<String> stripPunctuation(String[] words) {
        return Stream.of(words)
                .filter(word -> !word.contains("'"))
                .map(word -> word.replaceAll("\\W", ""));
    }

    public static void main(String[] args) throws IOException {

        extractWords("src/main/resources/sonnets/one_sonnet.txt")
                .stream()
                .sorted()
                .forEach(System.out::println);

    }
}













