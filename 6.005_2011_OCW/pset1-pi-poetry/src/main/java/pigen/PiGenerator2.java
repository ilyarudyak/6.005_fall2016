package pigen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * code from here: https://rosettacode.org/wiki/Pi#Java
 */
public class PiGenerator2 {

    final static BigInteger TWO = BigInteger.valueOf(2) ;
    final static BigInteger THREE = BigInteger.valueOf(3) ;
    final static BigInteger FOUR = BigInteger.valueOf(4) ;
    final static BigInteger SEVEN = BigInteger.valueOf(7) ;

    static BigInteger q = BigInteger.ONE ;
    static BigInteger r = BigInteger.ZERO ;
    static BigInteger t = BigInteger.ONE ;
    static BigInteger k = BigInteger.ONE ;
    static BigInteger n = BigInteger.valueOf(3) ;
    static BigInteger l = BigInteger.valueOf(3) ;

    public static List<Integer> calcPiDigits(int precision) {
        List<Integer> pi = new ArrayList<>();
        BigInteger nn, nr;
        for (int i = 0; i < precision; ++i) {
            if (FOUR.multiply(q).add(r).subtract(t).compareTo(n.multiply(t)) == -1) {

                pi.add(n.intValue());

                nr = BigInteger.TEN.multiply(r.subtract(n.multiply(t)));
                n = BigInteger.TEN.multiply(THREE.multiply(q).add(r)).divide(t).subtract(BigInteger.TEN.multiply(n));
                q = q.multiply(BigInteger.TEN);
                r = nr;
                System.out.flush();
            } else {
                nr = TWO.multiply(q).add(r).multiply(l);
                nn = q.multiply((SEVEN.multiply(k))).add(TWO).add(r.multiply(l)).divide(t.multiply(l));
                q = q.multiply(k);
                t = t.multiply(l);
                l = l.add(TWO);
                k = k.add(BigInteger.ONE);
                n = nn;
                r = nr;
            }
        }
        return pi;
    }
}
