package piwords;

import org.junit.Test;

import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

/**
 * Created by ilyarudyak on 11/18/16.
 */
public class PiGenerator2Test {

    @Test
    public void calcPiDigits5() {
        String expected = TestUtils.readPiDecimalFirst10000();
        String actual = PiGenerator2.calcPiDigits(50000)
                .stream()
                .map(i -> Integer.toString(i))
                .collect(Collectors.joining())
                .substring(1, 10000);

        assertEquals(expected, actual);
    }
}
