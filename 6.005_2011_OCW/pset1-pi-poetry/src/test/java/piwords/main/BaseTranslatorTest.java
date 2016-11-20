package piwords.main;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import piwords.misc.PiGenerator;
import piwords.utils.TestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseTranslatorTest {


    // ------ base16 -> base10 ------

    /**
     * Test convertBase() from base16 to base10 using data from files
     * for both bases (we don't test pi generating here).
     */
    @Test
    public void convertBaseHexToDecimal1000() {

        String expected = TestUtils.readPiDecimalFirst10K().substring(0, 1000);
        String actual = convertHexToDecimal(1000);

        assertEquals(expected, actual);
    }

    @Test
    public void convertBaseHexToDecimal5000() {

        String expected = TestUtils.readPiDecimalFirst10K().substring(0, 5000);
        String actual = convertHexToDecimal(5000);

        assertEquals(expected, actual);
    }

    @Test
    public void convertBaseHexToDecimal10000() {

        String expected = TestUtils.readPiDecimalFirst10K().substring(0, 10000);
        String actual = convertHexToDecimal(10000);

        assertEquals(expected, actual);
    }

    private static String convertHexToDecimal(int precision) {

        int baseA = 16;
        int baseB = 10;

        int[] digitsInHex = toIntArray(Pattern.compile("")
                .splitAsStream(TestUtils.readPiHex100K())
                .map(s -> Integer.valueOf(s, baseA))
                .limit(precision)
                .collect(Collectors.toList()));

        int[] convertedArray = Arrays.copyOf(BaseTranslator.convertBase(
                digitsInHex, baseA, baseB, precision), precision);

        String convertedString = IntStream.of(convertedArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        return convertedString;
    }

    // ----- base10 -> base16 ------

    /**
     * To get correct result we need to set precision more than tested:
     * 2000 instead of 1000 (we don't check if we can use less precision).
     */
    @Test
    public void convertBaseDecimalToHex1000() {

        String expected = TestUtils.readPiHex100K().substring(0, 1000);
        String actual = convertedDecimalToHex(2000).substring(0, 1000);

        assertEquals(expected, actual);
    }

    @Test
    public void convertBaseDecimalToHex5000() {

        String expected = TestUtils.readPiHex100K().substring(0, 5000);
        String actual = convertedDecimalToHex(7000).substring(0, 5000);

        assertEquals(expected, actual);
    }

    @Test
    public void convertBaseDecimalToHex10000() {

        String expected = TestUtils.readPiHex100K().substring(0, 10000);
        String actual = convertedDecimalToHex(15000).substring(0, 10000);

        assertEquals(expected, actual);
    }

    private static String convertedDecimalToHex(int precision) {

        int baseA = 10;
        int baseB = 16;

        int[] digitsInDecimal = toIntArray(Pattern.compile("")
                .splitAsStream(TestUtils.readPiDecimalFirst100K())
                .limit(precision)
                .map(s -> Integer.valueOf(s, baseA))
                .collect(Collectors.toList()));


        int[] convertedArray = BaseTranslator.convertBase(
                digitsInDecimal, baseA, baseB, precision);

        String convertedString = IntStream.of(convertedArray)
                .mapToObj(i -> Integer.toString(i, baseB).toUpperCase())
                .collect(Collectors.joining());

        return convertedString;
    }

    // ------ base26 ------

    @Test @Ignore
    public void convertBaseBase26First100() {

        String expectedBase26First100 = TestUtils.readPiBase26First10000().substring(0, 100);

        assertEquals(expectedBase26First100, convertedStr(100));

    }

    @Test @Ignore
    public void convertBaseBase26First1000() {
        String expectedBase26First1000 = TestUtils.readPiBase26First10000().substring(0, 1000);

        assertEquals(expectedBase26First1000, convertedStr(1000));
    }

    private static String convertedStr(int precision) {

        int baseA = 16;
        int baseB = 26;
        int[] digitsInHex = toIntArray(PiGenerator.piInHex()
                .limit(precision * 2)
                .collect(Collectors.toList()));

        int[] convertedArray = BaseTranslator.convertBase(
                digitsInHex, baseA, baseB, precision);

        List<Integer> convertedList = IntStream.of(convertedArray)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        String convertedStr = DigitsToStringConverter.convertDigitsToString(
                convertedList, baseB, TestUtils.alphabet());

        return convertedStr;
    }

    // from here: http://stackoverflow.com/questions/960431/how-to-convert-listinteger-to-int-in-java
    private static int[] toIntArray(List<Integer> list) {
            int[] ret = new int[list.size()];
            for(int i = 0;i < ret.length;i++)
                ret[i] = list.get(i);
            return ret;
    }

}

















