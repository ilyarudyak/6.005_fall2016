package misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.Utils.head;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class StreamMethods {

    private static final int TO_START = 41;
    public static final int FIRST_3_PARAGRAPHS = 23;

    public static String readFileToString(String fileName) {

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines
                    .skip(TO_START)
                    .limit(FIRST_3_PARAGRAPHS)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static Stream<String> stringToWordsStream(String str) {
        return Pattern.compile("\\W+").splitAsStream(str);
    }

    public static void main(String[] args) {

        String alice = readFileToString("src/main/resources/alice.txt");
        head(stringToWordsStream(alice), 100);
    }
}
















