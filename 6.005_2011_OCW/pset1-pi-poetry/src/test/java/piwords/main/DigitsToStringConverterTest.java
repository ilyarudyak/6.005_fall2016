package piwords.main;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static piwords.main.DigitsToStringConverter.convertDigitsToString;

import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class DigitsToStringConverterTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalInputDigitsOutOfRange() {
        List<Integer> digits = asList(0, 1, 2, 4);
        List<String> alphabet = asList("a", "b", "c", "d");
        int base = 4;
        convertDigitsToString(digits, base, alphabet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalInputAlphabetBaseDifferent() {
        List<Integer> digits = asList(0, 1, 2, 3);
        List<String> alphabet = asList("a", "b", "c", "d", "e");
        int base = 4;
        convertDigitsToString(digits, base, alphabet);
    }

    @Test
    public void basicNumberSerializerTest() {
        List<Integer> digits = asList(0, 1, 2, 3);
        List<String> alphabet = asList("a", "b", "c", "d");
        int base = 4;

        String expected = "abcd";
        String actual = convertDigitsToString(digits, base, alphabet);

        assertEquals(expected, actual);
    }

    @Test
    public void basicNumberSerializerTestSpecificAlphabet() {
        List<Integer> digits = asList(0, 1, 2, 3);
        List<String> alphabet = asList("d", "b", "c", "a");
        int base = 4;

        String expected = "dbca";
        String actual = convertDigitsToString(digits, base, alphabet);

        assertEquals(expected, actual);
    }

}




















