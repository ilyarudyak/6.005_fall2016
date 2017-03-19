package expressivo.expression;

import java.util.Map;

import expressivo.Expression;

public class BinaryOp{
    private final Expression left;
    private final Expression right;
    private char op;

    public BinaryOp(Expression leftOp, Expression rightOp, char op) {
        this.left = leftOp;
        this.right = rightOp;
        this.op = op;
    }

    public Expression getLeft(){
        return left;
    }

    public Expression getRight(){
        return right;
    }
    
    @Override
    public String toString() {
        // TODO something wrong here 
        return '(' + left.toString() + ')' + op + '(' + right.toString() + ')';
    }
    
    // -------- equals() and hashCode() ---------------
    
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
    
    // ----------------- problems 3-4 -----------------
    
    public Expression simplify(Map<String, Double> env) {
        final Expression simplifiedLeftExpr = getLeft().simplify(env);
        final Expression simplifiedRightExpr = getRight().simplify(env);
        return Expression.create(simplifiedLeftExpr, simplifiedRightExpr, op);
    }
    

}