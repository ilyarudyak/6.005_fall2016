package pigen;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * From 6.005 PSET1 files. See formula here:
 * https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula#BBP_digit-extraction_algorithm_for_.CF.80
 */
public class BBPHex {

    /**
     * Returns infinite stream of the fractional part of pi.
     * Returns digits in most significant to least significant order.
     *
     *
     * @return precision digits of pi in hexadecimal.
     */
    public static Stream<Integer> piInHexStream() {

        return Stream.iterate(1, n -> n + 1)
                .map(n -> piDigit(n));
    }
    public static String piInHexStr(int size) {
        return Stream.iterate(1, n -> n + 1)
                .map(n -> String.format("%X", piDigit(n)))
                .limit(size)
                .collect(Collectors.joining());
    }

    /**
     * Computes a^b mod m
     *
     * If a < 0, b < 0, or m < 0, return -1.
     *
     * @param base
     * @param exp
     * @param p
     * @return x^y mod m
     */
    public static int powerMod2(long base, int exp, int p) {

        int baseInt = (int) base;
        int resInt = 1;

        if (base < 0 || exp < 0 || p <= 0) { return -1; }
        assert p * p <= Long.MAX_VALUE;
        if (p == 1) { return 0; }

        long res = 1;
        base = base % p;

        baseInt = baseInt % p;

        while (exp > 0) {

            if (exp % 2 != 0) {
                res = ((res % p) * (base % p)) % p;

                resInt = (resInt * baseInt) % p;
                System.out.print("exp=" + exp + " res=" + res + " resInt=" + resInt);
            }

            exp = exp / 2;
            base = (base * base) % p;

            baseInt = (baseInt * baseInt) % p;
            System.out.println(" exp=" + exp + " base=" + base + " baseInt=" + baseInt);

        }
        return (int)res;
    }

    public static int powerMod(int x, int y, int p) {
        BigInteger X = BigInteger.valueOf(x);
        BigInteger Y = BigInteger.valueOf(y);
        BigInteger P = BigInteger.valueOf(p);
        return X.modPow(Y, P).intValueExact();

    }

    /**
     * Computes the nth digit of Pi in base-16.
     *
     * If n < 0, return -1.
     *
     * @param n The digit of Pi to retrieve in base-16.
     * @return The nth digit of Pi in base-16.
     */
    public static int piDigit(int n) {
        if (n < 0) return -1;

        n -= 1;
        double x = 4 * piTerm(1, n) - 2 * piTerm(4, n) -
                piTerm(5, n) - piTerm(6, n);
//        System.out.println(x);

//        System.out.println("floor=" + Math.floor(x));
        x = x - Math.floor(x);
//        System.out.println(x);

//        double y = x * 16;
//        System.out.println(y);

        return (int)(x * 16);
    }

    private static double piTerm(int j, int n) {
        // Calculate the left sum
        double s = 0;
        for (int k = 0; k <= n; ++k) {
            int r = 8 * k + j;
            s += powerMod(16, n-k, r) / (double) r;

            if (powerMod(16, n-k, r) != powerMod2(16, n-k, r)) {
                System.out.println(n-k + " " + r);
            }

            s = s - Math.floor(s);
        }

//        System.out.println("s=" + s);

        // Calculate the right sum
        double t = 0;
        int k = n+1;
        // Keep iterating until t converges (stops changing)
        while (true) {
            int r = 8 * k + j;
            double newt = t + Math.pow(16, n-k) / r;

            if (t == newt) {
                break;
            } else {
                t = newt;
            }
            ++k;
        }

//        System.out.println("t=" + t);

        return s+t;
    }

    public static void main(String[] args) {

        System.out.println(piDigit(2));
    }
}














