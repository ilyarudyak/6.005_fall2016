package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/14/16.
 */
public class Utils {

    public static Stream<String> readWords() {
        Pattern pattern = Pattern.compile("\\W+");
        try {
            String wordStr = Files.lines(Paths.get("src/test/resources/words.txt"))
                    .collect(Collectors.joining());
            return pattern.splitAsStream(wordStr)
                    .filter(word -> !word.equals(""));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    public static String readAlice() {
        try {
            return Files.lines(Paths.get("src/test/resources/alice.txt"))
                    .collect(Collectors.joining(" "));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
