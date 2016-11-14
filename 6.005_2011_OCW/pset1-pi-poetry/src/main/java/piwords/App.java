package piwords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 11/14/16.
 */
public class App {

    private static List<String> words = new ArrayList<>();

    public App() {
        Pattern pattern = Pattern.compile("\\W+");
        try {
            String wordStr = Files.lines(Paths.get("src/main/resources/words.txt"))
                    .collect(Collectors.joining());
            words = pattern.splitAsStream(wordStr)
                    .filter(word -> !word.equals(""))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        App app = new App();
        System.out.println(app.words.toString().substring(0, 500));

    }
}















