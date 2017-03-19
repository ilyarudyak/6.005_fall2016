package expressivo.expression;

import expressivo.Expression;

public class Product extends BinaryOp implements Expression{

    public Product(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '*');
    }
    
    // ----------------- problems 3-4 -----------------

    /** We use this method only in simplify().
     * factory method for generating expressions 
     * @returns: An Expression formed using the following rules:
     * 1: a*b = 0 if either a or b is 0
     * 2: a*b = a if b = 1 || a*b = b if a = 1
     * 3: the Number always comes first.
     * */
    public static Expression createProduct(Expression leftOp, Expression rightOp) {
    	boolean isNumLeft = leftOp instanceof Num;
    	boolean isNumRight = rightOp instanceof Num;
    	final Expression one = Num.one();
    	final Expression zero = Num.zero();
    	
    	if (isNumLeft) {
            if (leftOp.equals(zero)) return zero;
            else if (leftOp.equals(one)) return rightOp;
            else if (isNumRight) {
                return Num.product((Num)leftOp, (Num)rightOp);
           }
            else if (rightOp instanceof Product) {
    	        final Expression leftOfRightOp = ((Product) rightOp).getLeft();
    	        final Expression rigtOfRightOp = ((Product) rightOp).getRight();
    	        if (leftOfRightOp instanceof Num) {
    	            return createProduct( Num.product((Num) leftOp, (Num) leftOfRightOp) , rigtOfRightOp);
    	        }
    	        
    	        else return new Product(leftOp, rightOp);
    	    }
            
            else return new Product(leftOp, rightOp);
    	   
    	}
    	
    	else if (rightOp instanceof Product) {
    	     final Expression leftOfRightOp = ((Product) rightOp).getLeft();
             final Expression rigtOfRightOp = ((Product) rightOp).getRight();
             if (leftOfRightOp instanceof Num) {
                 return createProduct(leftOfRightOp, createProduct(leftOp, rigtOfRightOp));
             }
             
             else return new Product(leftOp, rightOp);
    	}
    	

    	else if (rightOp.equals(zero)) return zero;
    	else if (rightOp.equals(one)) return leftOp;
    
    	else if (isNumRight) return new Product(rightOp, leftOp);
    	else return new Product(leftOp, rightOp);
    }

    /* duv/dx = udv/dx + vdu/dx */
    public Expression differentiate(Var x) {
        final Expression left = getLeft();
        
        final Expression right = getRight();
        final Expression leftDeriv = left.differentiate(x);
        final Expression rightDeriv = right.differentiate(x);
               
        return new Sum(new Product(left, rightDeriv), new Product(right, leftDeriv));	
	} 
}