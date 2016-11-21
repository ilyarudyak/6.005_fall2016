package piwords.extra;

import pigen.BBPHex;
import piwords.main.BaseTranslator;
import piwords.utils.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 11/21/16.
 */
public class DigitsFrequency {

    // https://en.wikipedia.org/wiki/Letter_frequency
    private static final String ORDERED_ALPHABET = "etaoinshrdlcumwfgypbvkjxqz";

    public static Map<Integer, Long> getFreqMap(List<Integer> pi) {

        return pi.stream()
                .collect(Collectors.groupingBy(
                       Function.identity(),
                       Collectors.counting()
                ));

    }


    /**
     * Read hex digits from file (not generate them) and convert them
     * into list of digits in base26.
     *
     * @param precision number of digits *after* dot
     * @return List of digits after dot in base26.
     */
    public static List<Integer> getPiBase26(int precision) {

        int baseA = 16;
        int baseB = 26;
        int precisionToUse = (int) (precision * 1.5);

        Stream<Integer> piStream = BBPHex.piInHexStream();

        int[] digitsInHex = Utils.toIntArray(Utils.readPiHex(precisionToUse));

        int[] convertedArray = BaseTranslator.convertBase(
                digitsInHex, baseA, baseB, precisionToUse);

        List<Integer> convertedList = IntStream.of(convertedArray)
                .mapToObj(Integer::valueOf)
                .limit(precision)
                .collect(Collectors.toList());

        return convertedList;
    }

    /**
     * We order digits based on their frequency in Pi (first 1K).
     */
    public static List<Integer> getOrderedDigitsFromBase26(int precision) {

        Map<Integer, Long> freqMap = getFreqMap(getPiBase26(precision));
        List<Integer> orderedDigits = new ArrayList<>(freqMap.keySet());
        Comparator<Integer> byFrequency = (d1, d2) -> freqMap.get(d1).intValue() - freqMap.get(d2).intValue();
        orderedDigits.sort(byFrequency);
        return orderedDigits;
    }
}




















