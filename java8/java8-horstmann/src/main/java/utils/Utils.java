package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class Utils {

    public static final int LIMIT = 10;

    public static <T> void head(Stream<T> stream) {
        stream.limit(LIMIT).forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    public static <T> void head(Stream<T> stream, int limit) {
        stream.limit(limit).forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    public static String readFileToString(String fileName) {

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Stream<String> readAliceToWordsStream() {

        String alice = readFileToString("src/main/resources/alice.txt");
        return Pattern.compile("\\W+").splitAsStream(alice);
    }
}















