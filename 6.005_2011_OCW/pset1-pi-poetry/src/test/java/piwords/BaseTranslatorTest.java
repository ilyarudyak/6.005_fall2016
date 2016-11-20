package piwords;

import static org.junit.Assert.*;

import org.junit.Test;
import pigen.PiGenerator2;
import piwords.main.BaseTranslator;
import piwords.main.DigitsToStringConverter;
import piwords.main.PiGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BaseTranslatorTest {


    // ------ base16 -> base10 ------

    @Test
    public void convertBaseDecimal1000() {

        String expected = TestUtils.readPiDecimalFirst10000().substring(0, 1000);
        String actual = convertedDecimal(1000);

        assertEquals(expected, actual);
    }

    @Test
    public void convertBaseDecimal5000() {

        String expected = TestUtils.readPiDecimalFirst10000().substring(0, 5000);
        String actual = convertedDecimal(5000);

        assertEquals(expected, actual);
    }

    private static String convertedDecimal(int precision) {

        int baseA = 16;
        int baseB = 10;

        int[] digitsInHex = toIntArray(PiGenerator.piInHex()
                .limit(precision)
                .collect(Collectors.toList()));

        int[] convertedArray = Arrays.copyOf(BaseTranslator.convertBase(
                digitsInHex, baseA, baseB, precision), precision);

        String convertedString = IntStream.of(convertedArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        return convertedString;
    }

    // ----- base16 ------

    @Test
    public void convertBaseDecimalToHex10K() {

        String expected = TestUtils.readPiHex100K().substring(0, 10000);
        String actual = convertedDecimalToHex(12000).substring(0, 10000);

        assertEquals(expected, actual);
    }

    private static String convertedDecimalToHex(int precision) {

        int baseA = 10;
        int baseB = 16;

        int[] digitsInDecimal = Arrays.copyOf(toIntArray(PiGenerator2
                .calcPiDigits(50000)), 10000);


        int[] convertedArray = Arrays.copyOf(BaseTranslator.convertBase(
                digitsInDecimal, baseA, baseB, precision), 10000);

        String convertedString = IntStream.of(convertedArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());

        return convertedString;
    }

    // ------ base26 ------

    @Test
    public void convertBaseBase26First100() {

        String expectedBase26First100 = TestUtils.readPiBase26First10000().substring(0, 100);

        assertEquals(expectedBase26First100, convertedStr(100));

    }

    @Test
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

    // ------ big decimal ------

    @Test
    public void convertBaseBigDecimalFirst100Hex() {

        Stream<Integer> decimalFirst200 = TestUtils.readPiDecimalToStream(200);
        String actualHexFirst100 = BaseTranslator
                .convertBaseBigDecimal(decimalFirst200, "16", 200)
                .stream()
                .collect(Collectors.joining())
                .substring(0, 100);
        String expectedFirst100 = TestUtils.readPiHexFirst100();

        assertEquals(expectedFirst100, actualHexFirst100);
    }

    @Test
    public void convertBaseBigDecimalFirst100Base26() {

        String expectedBase26First100 = TestUtils.readPiBase26First10000().substring(0, 100);

        Stream<Integer> decimalFirst200 = TestUtils.readPiDecimalToStream(200);
        List<Integer> actualBase26First200List = BaseTranslator
                .convertBaseBigDecimal(decimalFirst200, "26", 200)
                .stream()
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());

        String actualBase26First100 = DigitsToStringConverter
                .convertDigitsToString(actualBase26First200List, 26, TestUtils.alphabet())
                .substring(0, 100);

        assertEquals(expectedBase26First100, actualBase26First100);

    }
}

















