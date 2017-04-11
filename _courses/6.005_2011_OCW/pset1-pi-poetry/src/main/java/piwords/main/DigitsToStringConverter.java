package piwords.main;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class DigitsToStringConverter {
    /**
     * Given a list of digits, a base, and an mapping of digits of that base to
     * chars, convert the list of digits into a character string by applying the
     * mapping to each digit in the input.
     * 
     * If digits[i] >= base or digits[i] < 0 for any i, consider the input
     * invalid, and return null.
     * If alphabet.length != base, consider the input invalid, and return null.
     *
     * @param digits A list of digits to encode. This object is not mutated.
     * @param base The base the digits are encoded in.
     * @param alphabet The mapping of digits to chars. This object is not
     *                 mutated.
     * @return A String encoding the input digits with alphabet.
     */
    public static String convertDigitsToString(List<Integer> digits, int base,
                                               List<String> alphabet) {

        if (!digits.stream()
                .filter(d -> (d >= base || d < 0))
                .collect(toList()).isEmpty() ||
            alphabet.size() != base ) {
            throw new IllegalArgumentException();
        }

        Map<Integer, String> digitsMap = IntStream.range(0, base)
                .mapToObj(Integer::valueOf)
                .collect(toMap(Function.identity(),
                               digit -> alphabet.get(digit)));

//        System.out.println(digitsMap);

        return digits.stream()
                .map(digit -> digitsMap.get(digit))
                .collect(joining());
    }

    public static String convertDigitsToStringWithFreq(List<Integer> digits, List<Integer> digitsFreq,
                                                       int base, List<String> orderedAlphabet) {

        if (!digits.stream()
                .filter(d -> (d >= base || d < 0))
                .collect(toList()).isEmpty() ||
                orderedAlphabet.size() != base ) {
            throw new IllegalArgumentException();
        }

        Map<Integer, String> digitsMap = IntStream.range(0, base)
                .mapToObj(Integer::valueOf)
                .collect(toMap(Function.identity(),
                        digit -> orderedAlphabet.get(digitsFreq.indexOf(digit))));

//        System.out.println(digitsMap);

        return digits.stream()
                .map(digit -> digitsMap.get(digit))
                .collect(joining());
    }

}

















