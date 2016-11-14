package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 11/14/16.
 */
public class App {

    private static Stream<String> words = Stream.empty();

    public App() {
        readWords();
    }

    private void readWords() {
        Pattern pattern = Pattern.compile("\\W+");
        try {
            String wordStr = Files.lines(Paths.get("src/main/resources/words.txt"))
                    .collect(Collectors.joining());
            words = pattern.splitAsStream(wordStr)
                    .filter(word -> !word.equals(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        App app = new App();
        System.out.println(app.words.toString().substring(0, 500));

    }
}















