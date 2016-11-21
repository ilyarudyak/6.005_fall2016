package piwords.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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

    public static Stream<String> getSonnetWords() {
        try {
            return Files.lines(Paths.get("src/main/resources/sonnets/sonnet_words.txt"))
                    .map(String::toLowerCase);
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    // from here: http://stackoverflow.com/questions/960431/how-to-convert-listinteger-to-int-in-java
    public static int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }

    public static String readPiHex100K() {
        try {
            return Files.lines(Paths.get("src/test/resources/first-100K-Hex.txt"))
                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static List<Integer> readPiHex(int precision) {
        return Pattern.compile("").splitAsStream(readPiHex100K())
                .limit(precision)
                .map(s -> Integer.valueOf(s, 16))
                .collect(Collectors.toList());
    }

}














