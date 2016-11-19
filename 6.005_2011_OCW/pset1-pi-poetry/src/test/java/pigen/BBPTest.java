package pigen;

import org.junit.Test;
import piwords.TestUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by ilyarudyak on 11/19/16.
 */
public class BBPTest {

    @Test
    public void first1000DecimalDigits() throws IOException {

        String expected = TestUtils.readPiDecimalFirst10000().substring(0, 1000);
        String actual = new BBP().generatePi(1000).substring(0, 1000);

        assertEquals(expected, actual);
    }

    @Test
    public void first5000DecimalDigits() throws IOException {

        String expected = TestUtils.readPiDecimalFirst10000().substring(0, 5000);
        String actual = new BBP().generatePi(5000).substring(0, 5000);

        assertEquals(expected, actual);
    }

    @Test
    public void first10000DecimalDigits() throws IOException {

        String expected = TestUtils.readPiDecimalFirst10000();
        String actual = new BBP().generatePi(10000).substring(0, 10000);

        assertEquals(expected, actual);
    }

}




















