package pigen;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.IntStream;

/**
 * This class computes PI in *decimal* using Bailey-Borwein-Plouffe (see link to wiki
 * below). Tested to be correct for 10,000 digits.
 * from here: https://github.com/fredwilby/Math/blob/master/src/com/fredwilby/math/misc/BBP.java
 */
public class BBPDecimal {

    /**
     * Calculates pi using the Bailey-Borwein-Plouffe formula to the max
     * precision allowed by the specified MathContext.
     * @param digits number of PI's digits to compute
     * @return fractional part of PI as a string
     */
    public String generatePiOriginal(int digits) {

        MathContext mc = new MathContext(digits + 1, RoundingMode.HALF_EVEN);

        BigDecimal pi = BigDecimal.ZERO, tn = BigDecimal.ONE;

        pi.setScale(mc.getPrecision(), mc.getRoundingMode());
        tn.setScale(mc.getPrecision(), mc.getRoundingMode());

		/* empirical data suggests that iterations -> correct decimal digits is roughly linear (r-sq ~= 100%)
         * the coefficients given on my small data set (up to 3400 iterations) were
		 * correct_digits = 1.20515 * it + 6.56522. Since the latter half of the data exceeded the regression
		 * made from the first half, I'm going to over iterate and simply use correct_digits = it. This will
		 * lead to suboptimal performance, but the difference should be fairly negligible, and it'll hopefully
		 * be sufficient for any imaginable number of digits.
		 */
        for (int k = 0; k < mc.getPrecision(); k++) {
            tn = term(k, 1, 16, 8, new int[]{4, 0, 0, -2, -1, -1, 0, 0}, mc);
            pi = pi.add(tn);
        }

        return pi.toString().substring(2);
    }

    public String generatePi(int digits) {

        MathContext mc = new MathContext(digits + 5, RoundingMode.HALF_EVEN);
        BigDecimal pi = IntStream.range(0, mc.getPrecision())
                .mapToObj(Integer::valueOf)
                // using parallel stream reduced execution time x2
                .parallel()
                .map(k -> term(k, mc))
                .reduce(BigDecimal.ZERO, (x, y) -> x.add(y, mc));

        return pi.toString().substring(2);
    }

    /**
     * Computes a single term of the general BBP-like formula with the given parameters.
     * see: http://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula#Specializations
     *
     * @param mc the MathContext to use for the calculation
     */
    private static BigDecimal term(int k, int s, int b, int m, int[] A, MathContext mc) {
        BigDecimal result = new BigDecimal("0", mc);

		/* Sum Ai / (m*k + (i+1))^s */
        for (int j = 0; j < m; j++) {
            BigDecimal a = new BigDecimal(Integer.toString(A[j]), mc);
            BigDecimal c = new BigDecimal(Long.toString(m * k + j + 1), mc);

            result = result.add(a.divide(c.pow(s), mc));
        }

		/* sum = sum * 1 / b^k*/
        result = result.multiply(new BigDecimal(Integer.toString(b)).pow(-k, mc));

        return result;
    }
    private static BigDecimal term(int k, MathContext mc) {
        return term(k, 1, 16, 8, new int[]{4, 0, 0, -2, -1, -1, 0, 0}, mc);
    }


}























