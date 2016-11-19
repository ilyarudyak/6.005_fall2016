package pigen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class produces correct first 10,000 hex digits.
 */
public class BBPHexBigNum {

    private static final int PRECISION = 20;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.FLOOR;
    private static final MathContext MATH_CONTEXT = new MathContext(PRECISION, ROUNDING_MODE);

    public static String piInHexStr(int size) {
        return Stream.iterate(1, n -> n + 1)
                .limit(size)
                .parallel()
                .map(n -> String.format("%X", piDigit(n)))
                .collect(Collectors.joining());
    }

    public static int piDigit(int n) {

        if (n < 0) return -1;

        n -= 1;

        BigDecimal x = piTerm(1, n).multiply(BigDecimal.valueOf(4), MATH_CONTEXT);
                   x = x.subtract(piTerm(4, n).multiply(BigDecimal.valueOf(2), MATH_CONTEXT));
                   x = x.subtract(piTerm(5, n), MATH_CONTEXT);
                   x = x.subtract(piTerm(6, n), MATH_CONTEXT);

//        System.out.println(x);

        BigDecimal floor = BigDecimal.valueOf(Math.floor(x.doubleValue()));
        x = x.subtract(floor, MATH_CONTEXT);
//        System.out.println(x);

        x = x.multiply(BigDecimal.valueOf(16), MATH_CONTEXT);
//        System.out.println(x);

        return x.intValue();
    }

    private static BigDecimal piTerm(int j, int n) {

        // Calculate the left sum
        BigDecimal s = BigDecimal.ZERO;
        for (int k = 0; k <= n; ++k) {
            int r = 8 * k + j;

            BigInteger modPow = BigInteger.valueOf(16).modPow(
                    BigInteger.valueOf(n - k), BigInteger.valueOf(r));

            s = s.add(new BigDecimal(modPow, MATH_CONTEXT)
                    .divide(BigDecimal.valueOf(r), MATH_CONTEXT));
            s = s.subtract(BigDecimal.valueOf(s.intValue()), MATH_CONTEXT);
        }

//        System.out.println("s=" + s);

        // Calculate the right sum
        BigDecimal t = BigDecimal.ZERO;

        int k = n + 1;
        // Keep iterating until t converges (stops changing)
        while (true) {
            int r = 8 * k + j;

            BigDecimal pow = BigDecimal.valueOf(16).pow(n - k, MATH_CONTEXT);
            BigDecimal newt = t.add(pow.divide(BigDecimal.valueOf(r), MATH_CONTEXT));

            if (t.setScale(PRECISION, ROUNDING_MODE)
                    .compareTo(newt.setScale(PRECISION, ROUNDING_MODE)) == 0) {
                break;
            } else {
                t = newt;
            }
            ++k;
        }

//        System.out.println("t=" + t.setScale(PRECISION, ROUNDING_MODE));

        return s.add(t).setScale(PRECISION, ROUNDING_MODE);
    }

    public static void main(String[] args) {

        System.out.println(piDigit(1));
    }
}





















