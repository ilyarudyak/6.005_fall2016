package piwords.misc;

import org.junit.Test;
import piwords.utils.TestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by ilyarudyak on 11/18/16.
 */
public class DataTest {

    public static final String V1 = "src/test/resources/first-10K-Decimal-v1.txt";
    public static final String V2 = "src/test/resources/first-10K-Decimal-v2.txt";
    public static final String V3 = "src/test/resources/first-10K-Decimal-v3.txt";

    @Test
    public void providedDataEqual() throws IOException {

        assertEquals(actualFirst10000Digits(V1),
                actualFirst10000Digits(V2));

        assertEquals(actualFirst10000Digits(V1),
                actualFirst10000Digits(V3));
    }

    @Test
    public void provided100KCorrectUpTo10K() {

        assertEquals(TestUtils.readPiDecimalFirst10K(),
                TestUtils.readPiDecimalFirst100K().substring(0, 10000));
    }


    private String actualFirst10000Digits(String filename) throws IOException {

        return Stream.of(Files.lines(Paths.get(filename))
                .collect(Collectors.joining())
                .substring(2)
                .split(""))
                .filter(s -> !s.equals(" "))
                .collect(Collectors.joining());
    }
}
