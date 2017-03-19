package expressivo.expression;

import java.util.Map;

import expressivo.Expression;

public class Var implements Expression{
    private final String var;
    /**
     * 
     * @param var: The variable this contains (requires var to consist of uppercase and lowercase alphabets only)
     */
    public Var(String var) {
        this.var = var;
    }
    
    /**
     * 
     * @return the variable that this contains
     */
    public String getVar(){
        return var;
    }
    
    /**
     * @return a string representation for this enclosed in parenthesis
     */

    @Override
    public String toString() {
        return var;
    }
    
    
    /**
     * @param that: Object to be compared against
     * @return Boolean indicating whether this is equal to that
     */
    
    @Override
    public boolean equals(Object that) {
        if (that instanceof Var) {
            Var that_var = (Var) that;
            return (that_var.var).equals(var);
        }
        
        else return false;
    }
    
    @Override
    public int hashCode() {
        return var.hashCode();
    }

    /* du/dx = 1 if u == x else 0 */
    @Override
    public Expression differentiate(Var x) {
        if (this.equals(x)) return Num.one();
        else return Num.zero();
    }
    
    @Override
    public Expression simplify(Map<String, Double> env) {
      if (env.containsKey(var)) {
          double val = env.get(var);
          return new Num(val);
      }
      
      else return this;
      
    }

}