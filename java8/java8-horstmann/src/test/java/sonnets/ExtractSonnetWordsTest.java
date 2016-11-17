package sonnets;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by ilyarudyak on 11/17/16.
 */
public class ExtractSonnetWordsTest {

    private static final String FILENAME = "src/test/resources/sonnets/sonnet_words.txt";

    @Test
    public void ExtractSonnetWordsGivenWordList() throws IOException {

        Set<String> expexted = getGivenWordSet();
        Set<String> actual = ExtractSonnetWords.extractWords(FILENAME);

        assertEquals(expexted, actual);

    }

    private static Set<String> getGivenWordSet() throws IOException {
            return Files.lines(Paths.get(FILENAME))
                    .collect(Collectors.toSet());
    }
}












