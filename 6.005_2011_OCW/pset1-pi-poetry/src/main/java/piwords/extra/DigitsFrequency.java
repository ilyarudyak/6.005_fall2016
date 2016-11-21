package piwords.extra;

import pigen.BBPHex;
import piwords.main.BaseTranslator;
import piwords.utils.Utils;

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

    public static Map<Integer, Long> getFreqMap(List<Integer> piList) {

        return piList.stream()
                .collect(Collectors.groupingBy(
                       Function.identity(),
                       Collectors.counting()
                ));

    }


    /**
     * This method uses pi generator in hex BBPHex and then
     * translates into base 26. We calculate more than 10K - as per tests.
     * @return 10K digits after dot in base 26.
     */
    public static List<Integer> getPiListBase26First10K(int precision) {

        int baseA = 16;
        int baseB = 26;
        int presionToUse = precision + precision / 2;

        Stream<Integer> piStream = BBPHex.piInHexStream();

        int[] digitsInHex = Utils.toIntArray(piStream.limit(presionToUse)
                .collect(toList()));

        int[] convertedArray = BaseTranslator.convertBase(
                digitsInHex, baseA, baseB, presionToUse);

        List<Integer> convertedList = IntStream.of(convertedArray)
                .mapToObj(Integer::valueOf)
                .limit(precision)
                .collect(Collectors.toList());

        return convertedList;
    }
}




















