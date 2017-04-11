package piwords.misc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/20/16.
 */
public class BaseTranslatorBigNum {

    public static List<String> convertBaseBigDecimal(Stream<Integer> digits, String baseBStr, int precisionB) {

        String digitStr = "0." + digits.limit(precisionB + 5)
                .map(n -> Integer.toString(n))
                .collect(Collectors.joining(""));
        BigDecimal digit = new BigDecimal(digitStr);
        BigDecimal baseB = new BigDecimal(baseBStr);

        List<String> converted = new ArrayList<>();

        for (int i = 0; i < precisionB; i++) {
            digit = digit.multiply(baseB);
            BigDecimal intValueStr = new BigDecimal(Integer.toString(digit.intValue()));
            digit = digit.subtract(intValueStr);
            if (baseBStr.equals("16")) {
                converted.add(String.format("%x", intValueStr.intValue()));
            } else if (baseBStr.equals("26")) {
                converted.add(intValueStr.toString());
            }
        }

        return converted;
    }
}
