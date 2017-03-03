package stream.optional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static utils.Utils.readAliceToWordsStream;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class OptionalDemo {

    public static final String RED = "red";
    public static final String FRED = "fred";

    private static Stream<String> aliceWords = readAliceToWordsStream();

    public static void findWord(String word) {

        Optional<String> containsWord = aliceWords.filter(s -> s.contains(word)).findFirst();

        // (1) we can check if Optional is present
        if(containsWord.isPresent()) {
            System.out.println(containsWord.get());
        } else {
            System.out.println("no words containing '" + word + "'");
        }

    }

    public static void findWord2(String word) {

        Optional<String> containsWord = aliceWords.filter(s -> s.contains(word)).findFirst();

        // (2) we can process optional value: if it's present it will be printed; if not - nothing happens
        containsWord.ifPresent(System.out::println);

    }

    public static void findWord3(String word) {

        Optional<String> containsWord = aliceWords.filter(s -> s.contains(word)).findFirst();

        // (3) we can produce an alternative
        System.out.println(containsWord.orElse("no words containing " + word));

    }


    public static void main(String[] args) {

        Optional<String> optionalValue = aliceWords.filter(s -> s.contains(RED)).findFirst();

        Set<String> results = new HashSet<>();
        results.add("tired");
//        optionalValue.ifPresent(results::add);
//        System.out.println(results.size());

        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);
        System.out.println(results + " " + results.size());

    }
}
















