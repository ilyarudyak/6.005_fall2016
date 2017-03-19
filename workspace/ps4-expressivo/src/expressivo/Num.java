package expressivo;

import java.util.Map;

public class Num implements Expression{
    private final Number number;
    private boolean isInt;
    /**
     * 
     * @param num: The number this contains
     */
    public Num(double num) {
        this.number = num;
        isInt = false;
    }
    
    public Num(int num) {
        this.number = num;
        isInt = true;
    }
     
    /**
     * 
     * @return the number that this contains
     */
    public double getNum(){
        if (isInt)
            return ((Integer) number).doubleValue();
        else
            return ((Double) number).doubleValue();
        
    }
    

    public boolean isInt(){
        return isInt;
    }
    
    
    public static Num add(Num left, Num right) {
        if (left.isInt() && right.isInt()) 
          //  int sum = (int) left.getNum() + (int) right.getNum();
            return new Num((int) (left.getNum() + right.getNum()));
        
       else
            return new Num(left.getNum() + right.getNum());
    }
    
    
    public static Num zero() {
        return new Num(0);
    }
    
    public static Num one() {
        return new Num(1);
    }
    
        
    public static Num product(Num left, Num right){
        if (left.isInt() && right.isInt()) 
            return new Num((int)(left.getNum()*right.getNum()));     
        else 
            return new Num(left.getNum()*right.getNum());
    }
    
    /**
     * @return a string representation for this enclosed in parenthesis
     */
    @Override
    public String toString() {
         return number.toString();
    }
    
    
    @Override
    public int hashCode() {
        return number.hashCode();
    }
    /**
     * 
     * @param that: Object to be check equality against
     * @return boolean indicating whether this == that
     */

    @Override
    public boolean equals(Object that) {
        if (that instanceof Num) {
            Num that_number = (Num) that;
            return that_number.getNum() == getNum();
        }
        
        else return false;
    }

    /* da/dx = 0 if a is a constant */
    @Override
    public Expression differentiate(Var x) {
        return zero();    
    } 
    
    @Override
    public Expression simplify(Map<String, Double> env) {
        return this;
    }
}