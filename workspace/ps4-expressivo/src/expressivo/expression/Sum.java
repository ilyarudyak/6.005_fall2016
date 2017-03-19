package expressivo.expression;

import expressivo.Expression;

public class Sum extends BinaryOp implements Expression{

    public Sum(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
    }

    // ----------------- problems 3-4 -----------------
    
    /** We use this method only in simplify().
     * factory method for generating expressions 
     * @returns an Expression formed using the following rules:
     * a+b = a if b = 0 || a+b = b if a = 0
     * a+b = 2*a if a = b
     * also simplifies a*x + b*x as (a+b)*x if a and b are both numbers
     * */
    public static Expression createSum(Expression leftOp, Expression rightOp) {
    	boolean isNumLeft = leftOp instanceof Num;
    	boolean isNumRight = rightOp instanceof Num;
    	final Num zero = Num.zero();
    	final Num one = Num.one();
    	/* return a single number if both are numbers */
    	if (isNumLeft && isNumRight) {
    		return Num.add((Num) leftOp, (Num) rightOp);

    	}
    	else if (leftOp.equals(zero)) return rightOp;
    	else if (rightOp.equals(zero)) return leftOp;
    	else if (leftOp.equals(rightOp)) return Product.createProduct(new Num(2), leftOp);
    	else{
    	    
    	    boolean isLeftProduct = leftOp instanceof Product;
    	    boolean isRightProduct = rightOp instanceof Product;
    	    
    	    if (isLeftProduct || isRightProduct) {
    	        final Expression RightOfLeft = isLeftProduct ? ((Product) leftOp).getRight() : leftOp;
                final Expression RightofRight =  isRightProduct? ((Product) rightOp).getRight(): rightOp;
                final Expression leftOfLeft =  isLeftProduct? ((Product) leftOp).getLeft() : one;
                final Expression leftOfRight =  isRightProduct? ((Product) rightOp).getLeft() : one;

                
                if (RightOfLeft.equals(RightofRight)) {
                    if (leftOfLeft instanceof Num && leftOfRight instanceof Num)
                        return Product.createProduct(Num.add((Num) leftOfLeft, (Num) leftOfRight), RightOfLeft);
                    
                    else return new Sum(leftOp, rightOp);
                }
                
                else if (RightOfLeft.equals(rightOp) && leftOfLeft instanceof Num) return Product.createProduct(Num.add( (Num) leftOfLeft, one), rightOp);
                else if (RightofRight.equals(leftOp) && leftOfRight instanceof Num) return Product.createProduct(Num.add((Num) leftOfRight, one), leftOp);
                
                else return new Sum(leftOp, rightOp);

    	    }
    	    
    	    
    	    
    	    else return new Sum(leftOp, rightOp);
    	}
    }
    

	/* d(u+v)/dx = du/dx + dv/dx */	
	public Expression differentiate(Var x) {
	    final Expression left = getLeft();
	    final Expression right = getRight();
	    final Expression leftDeriv = left.differentiate(x);
	    final Expression rightDeriv = right.differentiate(x);
	    
	    return new Sum(leftDeriv, rightDeriv);	
	} 
}