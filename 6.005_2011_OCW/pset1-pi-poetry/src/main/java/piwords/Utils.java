package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/17/16.
 */
public class Utils {

    public static Stream<String> getWords() {
        Pattern pattern = Pattern.compile("\\W+");
        try {
            String wordStr = Files.lines(Paths.get("src/main/resources/words.txt"))
                    .collect(Collectors.joining());
            return pattern.splitAsStream(wordStr)
                    .filter(word -> !word.equals(""));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    public static String readPiBase26First10000() {

        try {
            return Files.lines(Paths.get("src/main/resources/first-10000-Base26.txt"))
                    .collect(Collectors.joining())
                    .substring(2);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
