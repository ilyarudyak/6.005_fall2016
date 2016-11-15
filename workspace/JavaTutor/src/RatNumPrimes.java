import java.util.HashMap;
import java.util.Map;

public class RatNumPrimes {
    private final Map<Integer, Integer> numer, denom;
    // We represent numerator and denominator as prime factorizations, or maps from prime factors to their exponents.
    // When the exponent would be 0, we omit that prime from the map.
    
    private static Map<Integer, Integer> one = new HashMap<Integer, Integer>();
    
    private enum Sign {
        NEGATIVE,
        ZERO,
        POSITIVE
    }
    
    private final Sign sign;
  
    private RatNumPrimes(Sign sign, Map<Integer, Integer> numer, Map<Integer, Integer> denom) {
        this.sign = sign;
        this.numer = numer;
        this.denom = denom;
    }
    
    /** Increment the exponent of a prime in a map by a given amount. */
    private static void incExp(Map<Integer, Integer> map, int prime, int by) {
        if (by > 0) {
            Integer n = map.get(prime);
            if (n == null)
                map.put(prime, by);
            else
                map.put(prime, n+by);
        }
    }
    
    /** Decrement the exponent of a prime in a map by a given amount.
     * @return if by is greater than the starting exponent, return how much greater it was
     * Throw ArithmeticException if the exponent is already 0. */
    private static int decExp(Map<Integer, Integer> map, int prime, int by) {
        Integer n = map.get(prime);
        if (n == null)
            throw new ArithmeticException();
        else if (by >= n) {
            map.remove(prime);
            return by - n;
        } else {
            map.put(prime, n-by);
            return 0;
        }
    }
    
    /** Compute the prime factors of an int. */
    private static Map<Integer, Integer> primeFactorization(int n) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for (int i = 2; i <= n; ++i) {
            int exponent = 0;
            
            while (n % i == 0) {
                ++exponent;
                n /= i;
            }
            
            if (exponent > 0)
                map.put(i, exponent);
        }
        
        return map;
    }
    
    /** Make a new RatnumPrimes == n. */
    public RatNumPrimes(int n) {
        denom = one;
        
        if (n == 0) {
            sign = Sign.ZERO;
            numer = one;
        } else if (n < 0) {
            sign = Sign.NEGATIVE;
            numer = primeFactorization(-n);
        } else {
            sign = Sign.POSITIVE;
            numer = primeFactorization(n);
        }
    }

    public RatNumPrimes inverse() {
        if (sign == Sign.ZERO) {
            throw new ArithmeticException();
        } else {
            return new RatNumPrimes(sign, denom, numer);
        }
    }


    private static int slowExp(int n, int m) {
        int res = 1;
        
        while (m > 0) {
            res *= n;
            --m;
        }
        
        return res;
    }
    
    public RatNumPrimes multiply(RatNumPrimes n) {
        if (sign == Sign.ZERO || n.sign == Sign.ZERO) {
            return new RatNumPrimes(Sign.ZERO, one, one);
        } else {
            Sign newSign;
            
            if (sign == Sign.POSITIVE)
                newSign = n.sign;
            else if (n.sign == Sign.POSITIVE)
                newSign = Sign.NEGATIVE;
            else
                newSign = Sign.POSITIVE;

            Map<Integer, Integer> newNumer = new HashMap<Integer, Integer>(), newDenom = new HashMap<Integer, Integer>();
            newNumer.putAll(numer);
            newDenom.putAll(denom);
            
            for (int k : n.numer.keySet()) {
                if (newDenom.containsKey(k))
                    incExp(newNumer, k, decExp(newDenom, k, n.numer.get(k)));
                else
                    incExp(newNumer, k, n.numer.get(k));
            }
            
            for (int k : n.denom.keySet()) {
                if (newNumer.containsKey(k))
                    incExp(newDenom, k, decExp(newNumer, k, n.denom.get(k)));
                else
                    incExp(newDenom, k, n.denom.get(k));
            }
            
            return new RatNumPrimes(newSign, newNumer, newDenom);
        }
    }
    
    public RatNumPrimes(int numer, int denom) {
        RatNumPrimes res = new RatNumPrimes(numer).multiply(new RatNumPrimes(denom).inverse());
        sign = res.sign;
        this.numer = res.numer;
        this.denom = res.denom;
    }
    
    private static String mapToString(Map<Integer, Integer> map) {
        int res = 1;
        
        for (Integer k : map.keySet()) {
            res *= slowExp(k, map.get(k));
        }
        
        return Integer.toString(res);
    }
    
    /**
     * @return a string representation of this rational number
     */
    public String toString() {
        switch(sign) {
        case ZERO:
            return "0";
        case POSITIVE:
            if (denom.size() == 0)
                return mapToString(numer);
            else
                return mapToString(numer) + "/" + mapToString(denom);
        case NEGATIVE:
            if (denom.size() == 0)
                return "-" + mapToString(numer);
            else
                return "-" + mapToString(numer) + "/" + mapToString(denom);
        default:
            throw new AssertionError("impossible case!");
        }
    }
}
