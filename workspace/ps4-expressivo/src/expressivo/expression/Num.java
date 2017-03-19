package expressivo.expression;

import java.util.Map;

import expressivo.Expression;

public class Num implements Expression{
    
    private final Number num;
    private boolean isInt;
    
    public Num(double num) {
        this.num = num;
        isInt = false;
    }
    
    public Num(int num) {
        this.num = num;
        isInt = true;
    }
     
    public double getNum(){
        if (isInt)
            return ((Integer) num).doubleValue();
        else
            return ((Double) num).doubleValue();
        
    }
    
    public boolean isInt(){
        return isInt;
    }
    
    @Override
    public String toString() {
         return num.toString();
    }
    
    // -------- equals() and hashCode() ---------------
    
    @Override
    public int hashCode() {
        return num.hashCode();
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

    // ----------------- problems 3-4 -----------------
    
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