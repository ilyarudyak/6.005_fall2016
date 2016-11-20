package piwords.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 11/14/16.
 */
public class TestUtils {

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

    public static String readPiHexFirst100() {

        try {
            return Stream.of(Files.lines(Paths.get("src/test/resources/first-100-Hex.txt"))
                    .collect(Collectors.joining())
                    .split(""))
                    .filter(s -> !s.equals(" "))
                    .map(String::toLowerCase)
                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static Stream<Integer> readPiDecimalFirst100ToStream() {
        return Stream.of(readPiDecimalFirst10000().substring(0, 100).split(""))
                .map(s -> Integer.valueOf(s));
    }
    public static Stream<Integer> readPiDecimalToStream(int n) {
        return Stream.of(readPiDecimalFirst10000().substring(0, n).split(""))
                .map(s -> Integer.valueOf(s));
    }
    public static String readPiDecimalFirst10000() {

        try {
            return Files.lines(Paths.get("src/test/resources/first-10K-Decimal-v1.txt"))
                    .collect(Collectors.joining())
                    .substring(2);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String readPiHex8336() {
        try {
            return Files.lines(Paths.get("src/test/resources/first-8336-Hex.txt"))
                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
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

    public static Stream<Integer> readPiBase26ToStream(int n) {
        return Stream.of(readPiBase26First10000().substring(0, 100).split(""))
                .map(s -> Integer.valueOf(s));
    }
    public static String readPiBase26First10000() {

        try {
            return Files.lines(Paths.get("src/test/resources/first-10000-Base26.txt"))
                    .collect(Collectors.joining())
                    .substring(2);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> alphabet() {
        return Pattern.compile("")
                .splitAsStream("abcdefghijklmnopqrstuvwxyz")
                .collect(toList());
    }


}













