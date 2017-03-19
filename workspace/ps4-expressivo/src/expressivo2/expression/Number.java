package expressivo2.expression;

import expressivo2.Expression;

public class Number implements Expression {
    
    private final int n;
    
    public Number(int n) {
        this.n = n;
    }
    
    @Override public String toString() {
        return String.valueOf(n);
    }

}
