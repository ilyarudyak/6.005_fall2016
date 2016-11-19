package pigen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * from here: https://github.com/fredwilby/Math/blob/master/src/com/fredwilby/math/misc/BBP.java
 */
public class BBP {

    /* Progress update vars */
    private volatile int prog;
    private int total;
    private volatile boolean run;

    /**
     * Calculates pi using the Bailey-Borwein-Plouffe formula to the max precision allowed by the specified MathContext.
     */
    public BigDecimal calcPi(MathContext mc) {
        BigDecimal pi = BigDecimal.ZERO, tn = BigDecimal.ONE;

        pi.setScale(mc.getPrecision(), mc.getRoundingMode());
        tn.setScale(mc.getPrecision(), mc.getRoundingMode());

        prog = 0;
        total = mc.getPrecision();
        run = true;

        // Update progress every 5 seconds (don't ask why I'm doing it this way)
        Thread progress = new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        long t0 = System.nanoTime();

                        while (run) {
                            long dt = System.nanoTime() - t0;
                            System.out.println("Elapsed time:" + dt + "ns\tTerm " + prog + "/" + total + " (" + ((double) prog / (double) total) + "%)");

                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

        progress.start();

		/* empirical data suggests that iterations -> correct decimal digits is roughly linear (r-sq ~= 100%)
         * the coefficients given on my small data set (up to 3400 iterations) were
		 * correct_digits = 1.20515 * it + 6.56522. Since the latter half of the data exceeded the regression
		 * made from the first half, I'm going to overiterate and simply use correct_digits = it. This will
		 * lead to suboptimal perforamnce, but the difference should be fairly negligible, and it'll hopefully
		 * be sufficient for any imaginable number of digits.
		 */
        for (int x = 0; x < mc.getPrecision(); x++) {
            tn = term(x, 1, 16, 8, new int[]{4, 0, 0, -2, -1, -1, 0, 0}, mc);
            pi = pi.add(tn);
            prog = x;
        }

        run = false;

        return pi;
    }

    /**
     * Computes a single term of the general BBP-like formula with the given parameters.
     * see: http://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula#Specializations
     *
     * @param mc the MathContext to use for the calculation
     */
    public static BigDecimal term(int k, int s, int b, int m, int[] A, MathContext mc) {
        BigDecimal result = new BigDecimal("0", mc);

		/* Sum Ai / (m*k + (i+1))^s */
        for (int i = 0; i < m; i++) {
            BigDecimal a = new BigDecimal(Integer.toString(A[i]), mc);
            BigDecimal c = new BigDecimal(Long.toString(m * k + i + 1), mc);

            result = result.add(a.divide(c.pow(s), mc));
        }

		/* sum = sum * 1 / b^k*/
        result = result.multiply(new BigDecimal(Integer.toString(b)).pow(-k, mc));

        return result;
    }

    public static void main(String[] args) {

        int digits = 10;
        MathContext mc = new MathContext(digits + 1, RoundingMode.HALF_EVEN);

        BBP bbp = new BBP();
        BigDecimal pi = bbp.calcPi(mc);
        System.out.println(pi);

    }
}
