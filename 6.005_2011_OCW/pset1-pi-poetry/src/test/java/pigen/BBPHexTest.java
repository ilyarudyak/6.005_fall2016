package pigen;

import org.junit.Test;
import piwords.utils.TestUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by ilyarudyak on 11/19/16.
 */
public class BBPHexTest {

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




















