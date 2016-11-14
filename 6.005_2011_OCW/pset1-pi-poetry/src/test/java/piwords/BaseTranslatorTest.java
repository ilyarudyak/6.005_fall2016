package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseTranslatorTest {
    @Test
    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        int[] input = {0, 1};
        int[] expectedOutput = {2, 5};
//        assertArrayEquals(expectedOutput,
//                          BaseTranslator.convertBase(input, 2, 10, 2));
    }

    @Test
    public void convertBaseBigDecimalFirst100Hex() {

        Stream<Integer> decimalFirst200 = Utils.readPiDecimalToStream(200);
        String actualHexFirst100 = BaseTranslator
                .convertBaseBigDecimal(decimalFirst200, "16", 200)
                .stream()
                .collect(Collectors.joining())
                .substring(0, 100);
        String expectedFirst100 = Utils.readPiHexFirst100();

        assertEquals(expectedFirst100, actualHexFirst100);
    }

    @Test
    public void convertBaseBigDecimalFirst100Base26() {

        String expectedBase26First100 = Utils.readPiBase26First10000().substring(0, 100);

        Stream<Integer> decimalFirst200 = Utils.readPiDecimalToStream(200);
        List<Integer> actualBase26First200List = BaseTranslator
                .convertBaseBigDecimal(decimalFirst200, "26", 200)
                .stream()
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());

        String actualBase26First100 = DigitsToStringConverter
                .convertDigitsToString(actualBase26First200List, 26, Utils.alphabet())
                .substring(0, 100);

        assertEquals(expectedBase26First100, actualBase26First100);

    }
}

















