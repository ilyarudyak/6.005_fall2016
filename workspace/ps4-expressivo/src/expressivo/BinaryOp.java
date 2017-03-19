package expressivo;

import java.util.Map;

public class BinaryOp{
    private final Expression left;
    private final Expression right;
    private char op;
    /**
     * Constructor for the BinaryOp class
     * @param leftOp forms the left side of the expression
     * @param rightOp forms the right side of the expression
     */
    public BinaryOp(Expression leftOp, Expression rightOp, char op) {
        this.left = leftOp;
        this.right = rightOp;
        this.op = op;
    }

    /**
     * @return the left part of this
     */
    public Expression getLeft(){
        return left;
    }
    /**
     * @return the right part of this
     */
    public Expression getRight(){
        return right;
    }
    
    @Override
    public boolean equals(Object that){
        if (that instanceof BinaryOp) {
            BinaryOp that_casted = (BinaryOp) that;
            if (that_casted.op == op)
                return (that_casted.left).equals(left) && (that_casted.right).equals(right);
            else return false;
        }
        
        else return false;
    }

    @Override
    public int hashCode() {
        int leftcode = left.hashCode();
        int rightcode = right.hashCode();
        if (op == '*') {
            return (leftcode*23) + rightcode;
        }

        else {
            return (leftcode*17) + rightcode;
        }
    }
    
    public Expression simplify(Map<String, Double> env) {
        final Expression simplifiedLeftExpr = getLeft().simplify(env);
        final Expression simplifiedRightExpr = getRight().simplify(env);
        return Expression.create(simplifiedLeftExpr, simplifiedRightExpr, op);
    }
    
    @Override
    public String toString() {
        return '(' + left.toString() + ')' + op + '(' + right.toString() + ')';
    }
}