package piwords.main;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class PiGenerator {
    /**
     * Returns infinite stream of the fractional part of pi.
     * Returns digits in most significant to least significant order.
     *
     *
     * @return precision digits of pi in hexadecimal.
     */
    public static Stream<Integer> piInHex() {

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
     * @param x
     * @param y
     * @param p
     * @return x^y mod m
     */
    public static int powerMod2(int x, int y, int p) {

        if (x < 0 || y < 0 || p <= 0) { return -1; }

        if (x == 0) { return 0; }
        if (y == 0) { return 1; }

        long res = 1;
        x = x % p;

        while (y > 0) {

            if (y % 2 != 0) {
                res = (res * x) % p;
            }

            y = y / 2;
            x = (x * x) % p;
        }
        return (int) res;
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
        x = x - Math.floor(x);
        
        return (int)(x * 16);
    }
    
    private static double piTerm(int j, int n) {
        // Calculate the left sum
        double s = 0;
        for (int k = 0; k <= n; ++k) {
            int r = 8 * k + j;
            s += powerMod(16, n-k, r) / (double) r;
            s = s - Math.floor(s);
        }
        
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
        
        return s+t;
    }

}














