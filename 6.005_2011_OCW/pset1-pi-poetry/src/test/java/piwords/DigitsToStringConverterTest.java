package piwords;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static piwords.DigitsToStringConverter.convertDigitsToString;

import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public void base26BasicTest() {

        List<Integer> digits0 = asList(0, 1, 2, 3);
        List<Integer> digits1 = asList(22, 23, 24, 25);
        List<Integer> digits2 = asList(0);
        List<Integer> digits3 = asList(25);
        List<Integer> digits4 = asList();

        List<String> alphabet = Pattern.compile("")
                .splitAsStream("abcdefghijklmnopqrstuvwxyz")
                .collect(toList());

        int base = 26;

        assertEquals("abcd", convertDigitsToString(digits0, base, alphabet));
        assertEquals("wxyz", convertDigitsToString(digits1, base, alphabet));
        assertEquals("a", convertDigitsToString(digits2, base, alphabet));
        assertEquals("z", convertDigitsToString(digits3, base, alphabet));
        assertEquals("", convertDigitsToString(digits4, base, alphabet));


    }

}




















