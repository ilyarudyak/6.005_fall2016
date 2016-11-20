package piwords.main;


import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class WordFinder {
    /**
     * Given a String (the haystack) and an array of Strings (the needles),
     * return a Map<String, Integer>, where keys in the map correspond to
     * elements of needles that were found as substrings of haystack, and the
     * value for each key is the lowest index of haystack at which that needle
     * was found. A needle that was not found in the haystack should not be
     * returned in the output map.
     * 
     * @param piStr The string to search into.
     * @param words The array of strings to search for. This array is not
     *                mutated.
     * @return The list of needles that were found in the haystack.
     */
    public static Map<String, Integer> getSubstrings(final String piStr,
                                                     Stream<String> words) {

        return words
                .filter(word -> bruteForceSearch(word, piStr) != -1)
                .collect( toMap(word -> word, word -> bruteForceSearch(word, piStr)) );
    }

    // code from here: http://algs4.cs.princeton.edu/53substring/Brute.java.html
    private static int bruteForceSearch(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++)
        {
            int j;
            for (j = 0; j < M; j++)
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            if (j == M) return i;
        }
        return -1;
    }

    // TODO: add Rabin-Karp
}

















