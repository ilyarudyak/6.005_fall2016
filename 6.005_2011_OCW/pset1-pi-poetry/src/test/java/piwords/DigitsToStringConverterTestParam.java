package piwords;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static piwords.main.DigitsToStringConverter.convertDigitsToString;

/**
 * Created by ilyarudyak on 11/19/16.
 */
@RunWith(Parameterized.class)
public class DigitsToStringConverterTestParam {

    private static final List<String> ALPHABET = Pattern.compile("")
            .splitAsStream("abcdefghijklmnopqrstuvwxyz")
            .collect(toList());

    public static final int BASE26 = 26;

    private String expected;
    private String input;

    public DigitsToStringConverterTestParam(String expected, String input) {
        this.expected = expected;
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<String[]> params() {
        return Arrays.asList(new String[][] {
                {"abcd", "0-1-2-3"},
                {"wxyz", "22-23-24-25"},
                {"a", "0"},
                {"z", "25"},
                {"", ""}
        });
    }

    @Test
    public void base26BasicTest() {

        assertEquals(expected, convertDigitsToString(inputToList(input),
                BASE26, ALPHABET));



    }

    private static List<Integer> inputToList(String input) {
        return Pattern.compile("-")
                .splitAsStream(input)
                .map(Integer::valueOf)
                .collect(toList());
    }
}
















