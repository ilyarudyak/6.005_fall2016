package piwords;

import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

public class WordFinderTest {

    @Test
    public void basicGetSubstringsTest() {
        String haystack = "abcde";
        Stream<String> needles = Stream.of("ab", "abc", "de", "fg");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("ab", 0);
        expected.put("abc", 0);
        expected.put("de", 3);

        assertEquals(expected,
                     WordFinder.getSubstrings(haystack, needles));

    }

    // tests from here: http://algs4.cs.princeton.edu/53substring/KMP.java.html
    @Test
    public void abraGetSubstringsTest() {

        String haystack = "abacadabrabracabracadabrabrabracad";
        Supplier<Stream<String>> needlesSupplier = () -> Stream.of(
                "abracadabra",
                "rab",
                "bcara",
                "rabrabracad",
                "abacad"
        );

        Map<String, Integer> expected = needlesSupplier.get()
                .filter(word -> haystack.indexOf(word) != -1)
                .collect(toMap(
                        word -> word,
                        word -> haystack.indexOf(word)
                ));
        Map<String, Integer> actual = WordFinder.getSubstrings(haystack,
                needlesSupplier.get());

        System.out.println(expected);
        System.out.println(actual);


        assertEquals(expected, actual);

    }

}
