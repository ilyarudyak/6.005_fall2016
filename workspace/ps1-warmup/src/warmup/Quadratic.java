package warmup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        
        System.out.println("a=" + a + " b=" + b + " c=" + c);
        
        if (a == 0 && b == 0 && c == 0) {
            throw new IllegalArgumentException("a, b, and c are ALL zero");
        } 
        
        Set<Integer> rootSet = new HashSet<>();
        
        // degenerate case
        if (a == 0 && b != 0) {
            Double solution = Double.valueOf(-c) / Double.valueOf(b);
            if (isInteger(solution)) {
                rootSet.add(solution.intValue());
                return rootSet;
            }
        }
        
        Double discriminant = Math.pow(b, 2) - 4 * Double.valueOf(a) * Double.valueOf(c);
        System.out.println("discriminant=" + discriminant + " sqrt=" + Math.sqrt(discriminant));
        
        if (discriminant < 0) {
            return rootSet;
        }
        
        // we don't check explicitly case == 0, we use sets
        Set<Double> rootDoubleSet = new HashSet<>();
        rootDoubleSet.add((-b + Math.sqrt(discriminant)) / (2 * a));
        rootDoubleSet.add((-b - Math.sqrt(discriminant)) / (2 * a));
        
        System.out.println(rootDoubleSet);
        
        for (Double root : rootDoubleSet) {
            if (isInteger(root)) {
                rootSet.add(root.intValue());
            }
        }
        
        return rootSet;
    }
    
    /*
     * adapted from here: http://stackoverflow.com/questions/9898512/how-to-test-if-a-double-is-an-integer
     */
    private static boolean isInteger(Double variable) {
        if (   variable.equals(Math.floor(variable)) && 
                !Double.isInfinite(variable)         &&
                !Double.isNaN(variable)              &&
                variable <= Integer.MAX_VALUE        &&
                variable >= Integer.MIN_VALUE) {
            return true;
        } else {
            return false;
        }
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
//        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
//        Set<Integer> result = roots(1, -4, 3);
//        System.out.println(result);
        
        int r1 =  45000;
        int r2 =  -45000;
        int a = 1;
        int c = r1 * r2;
        System.out.println("r1*r2=" + r1*r2);
        System.out.println("       4 * a * c   = "   +   (-4 * a * c));
        System.out.println("1.0  + 4 * a * c   = "   +   (1.0 - 4 * a * c));
        System.out.println("1.0  + 4.0 * a * c = "   +   (1.0 - 4.0 * a * c));
        
        
    }

}























