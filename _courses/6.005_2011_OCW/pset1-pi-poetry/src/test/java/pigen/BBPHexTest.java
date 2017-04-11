package pigen;

import org.junit.Test;
import piwords.utils.TestUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by ilyarudyak on 11/19/16.
 */
public class BBPHexTest {

    /**
     * This is the test of method provided in 6.005 with correct
     * implementation of powerMod().
     * Test first 1000 digits of PI (after dot) in HEX by
     * comparing with string from file (see sources of data in readme.txt).
     * @throws IOException
     */
    @Test
    public void piInHexStr1000() throws IOException {

        String actual = TestUtils.readPiHex100K().substring(0, 1000);
        String expected = BBPHex.piInHexStr(1000);

        assertEquals(actual, expected);
    }

    @Test
    public void piInHexStr5000() throws IOException {

        String actual = TestUtils.readPiHex100K().substring(0, 5000);
        String expected = BBPHex.piInHexStr(5000);

        assertEquals(actual, expected);
    }

    @Test
    public void piInHexStr10000() throws IOException {

        String actual = TestUtils.readPiHex100K().substring(0, 10000);
        String expected = BBPHex.piInHexStr(10000);

        assertEquals(actual, expected);
    }

    // ------ powerMod2 ------

    @Test
    public void powerMod2Overflow() {

        int base = 16;
        int exponent = 4199;
        int p = 46409;

        int expected = BBPHex.powerMod(base, exponent, p);
        int actual = BBPHex.powerMod2(base, exponent, p);

        assertEquals(expected, actual);
    }

    @Test
    public void reproduceBugInPowerMod2() {

        int base = 16;
        int n = 10000;
        int j = 1;
        boolean isDifferent = false;

        for (int k = 0; k < n; k++) {
            int expected = BBPHex.powerMod(base, n-k, 8*k+j);
            int actual = BBPHex.powerMod2(base, n-k, 8*k+j);
            if (expected != actual) {
                isDifferent = true;
                break;
            }
        }
        assertFalse(isDifferent);

    }

    @Test
    public void temp() {
        int p = 46409;
        int m = p * p;
        System.out.println(m);
    }

    /**
     * This method shows differences when we're using incorrect implementation
     * of powerMod(). Errors are after 5K digits due to overflow.
     */
    @Test
    public void showWhereWrong() {
        String actual = TestUtils.readPiHex100K().substring(0, 8000);
        String expected = BBPHex.piInHexStr(6000);

        int wrongIndex = actual.indexOf("A9DC09662D09A1C4");
        System.out.println("wrongIndex=" + wrongIndex);

        printWrongPlace(actual, wrongIndex);
        printWrongPlace(expected, wrongIndex);
    }

    private void printWrongPlace(String s, int wrongIndex) {
        System.out.println("..." + s.substring(wrongIndex - 10, wrongIndex) + "|" +
                            s.substring(wrongIndex, wrongIndex + 10) + "...");
    }
}




















