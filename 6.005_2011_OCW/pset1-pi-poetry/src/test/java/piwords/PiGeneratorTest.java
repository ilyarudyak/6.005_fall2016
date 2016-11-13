package piwords;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PiGeneratorTest {

    private static final int ITERATIONS = 100;
    private static final int RANGE = 1000;

    @Test
    public void first10Digits() {
        List<Integer> expected = asList(0x2, 0x4, 0x3, 0xF, 0x6, 0xA, 0x8, 0x8, 0x8, 0x5);
        List<Integer> actual = PiGenerator.piInHex().limit(10).collect(toList());
        assertEquals(expected, actual);
    }

    @Test
    public void first20Digits() throws IOException {

        assertEquals(actualFirst20Digits().collect(toList()),
                     PiGenerator.piInHex().limit(20).collect(toList()));
    }

    @Test
    public void basicPowerMod() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
    }

    @Test
    public void randomPowerMod() {

        Random random = new Random(Instant.now().getEpochSecond());
        for (int i = 0; i < ITERATIONS; i++) {

            BigInteger x = new BigInteger(Integer.toString(random.nextInt(RANGE)));
            BigInteger y = new BigInteger(Integer.toString(random.nextInt(RANGE)));
            // ensure p != 0
            BigInteger p = new BigInteger(Integer.toString(random.nextInt(RANGE) + 1));

            BigInteger expected = x.modPow(y, p);
            BigInteger actual = new BigInteger(Integer.toString(
                    PiGenerator.powerMod(x.intValue(), y.intValue(), p.intValue())
            ));

            assertEquals(expected, actual);
        }

    }

    private Stream<Integer> actualFirst20Digits() throws IOException {

        return Files.lines(Paths.get("src/test/resources/first-20-Digits.txt"))
                .map(d -> Integer.valueOf(d, 16));
    }
}
