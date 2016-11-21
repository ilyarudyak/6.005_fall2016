package piwords.extra;

import org.junit.Test;
import piwords.main.DigitsToStringConverter;
import piwords.utils.TestUtils;
import piwords.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static piwords.extra.DigitsFrequency.*;
import static piwords.main.DigitsToStringConverter.convertDigitsToString;
import static piwords.utils.TestUtils.readPiBase26First10000;

/**
 * Created by ilyarudyak on 11/21/16.
 */
public class DigitsFrequencyTest {

    @Test
    public void getFreqMapBasic() {

        List<Integer> piList = asList(1,4,1,5,9, 2,6,5,3,5, 8,9,7,9,3, 2,3,8,4,6);
        Map<Integer, Long> freqMap = getFreqMap(piList);

        System.out.println(freqMap);

    }

    @Test
    public void getFreqMap10K() {

        // http://thestarman.pcministry.com/math/pi/RandPI.html
        Map<Integer, Long> expected = new HashMap<>();

        expected.put(0, 968L);
        expected.put(1, 1026L);
        expected.put(2, 1021L);
        expected.put(3, 974L);
        expected.put(4, 1012L);
        expected.put(5, 1046L);
        expected.put(6, 1021L);
        expected.put(7, 970L);
        expected.put(8, 948L);
        expected.put(9, 1014L);

        List<Integer> pilist = TestUtils.readPiDecimalFirst10KIncluding3();
        Map<Integer, Long> actual = getFreqMap(pilist);

        assertEquals(expected, actual);

    }

    @Test
    public void getPiBase26First1K() {

        String expected = readPiBase26First10000().substring(0, 1000);
        String actual = convertDigitsToString(getPiBase26(1000), 26, TestUtils.alphabet());

        assertEquals(expected, actual);
    }

    @Test
    public void getPiBase26First10K() {

        String expected = readPiBase26First10000().substring(0, 9999);
        String actual = convertDigitsToString(getPiBase26(9999), 26, TestUtils.alphabet());

        assertEquals(expected, actual);
    }

    @Test
    public void temp() {

        System.out.println(getPiBase26WithFreq(10000).length());
    }

}
















