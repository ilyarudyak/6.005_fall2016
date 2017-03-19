package expressivo;


public class SumExpression extends BinaryOp implements Expression{

    public SumExpression(Expression leftOp, Expression rightOp) {
        super(leftOp, rightOp, '+');
        // TODO Auto-generated constructor stub
    }

    /* factory method for generating expressions 
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
    	else if (leftOp.equals(rightOp)) return MultiplyExpression.createProduct(new Num(2), leftOp);
    	else{
    	    
    	    boolean isLeftProduct = leftOp instanceof MultiplyExpression;
    	    boolean isRightProduct = rightOp instanceof MultiplyExpression;
    	    
    	    if (isLeftProduct || isRightProduct) {
    	        final Expression RightOfLeft = isLeftProduct ? ((MultiplyExpression) leftOp).getRight() : leftOp;
                final Expression RightofRight =  isRightProduct? ((MultiplyExpression) rightOp).getRight(): rightOp;
                final Expression leftOfLeft =  isLeftProduct? ((MultiplyExpression) leftOp).getLeft() : one;
                final Expression leftOfRight =  isRightProduct? ((MultiplyExpression) rightOp).getLeft() : one;

                
                if (RightOfLeft.equals(RightofRight)) {
                    if (leftOfLeft instanceof Num && leftOfRight instanceof Num)
                        return MultiplyExpression.createProduct(Num.add((Num) leftOfLeft, (Num) leftOfRight), RightOfLeft);
                    
                    else return new SumExpression(leftOp, rightOp);
                }
                
                else if (RightOfLeft.equals(rightOp) && leftOfLeft instanceof Num) return MultiplyExpression.createProduct(Num.add( (Num) leftOfLeft, one), rightOp);
                else if (RightofRight.equals(leftOp) && leftOfRight instanceof Num) return MultiplyExpression.createProduct(Num.add((Num) leftOfRight, one), leftOp);
                
                else return new SumExpression(leftOp, rightOp);

    	    }
    	    
    	    
    	    
    	    else return new SumExpression(leftOp, rightOp);
    	}
    }
    

	/* d(u+v)/dx = du/dx + dv/dx */	
	public Expression differentiate(Var x) {
	    final Expression left = getLeft();
	    final Expression right = getRight();
	    final Expression leftDeriv = left.differentiate(x);
	    final Expression rightDeriv = right.differentiate(x);
	    
	    return new SumExpression(leftDeriv, rightDeriv);	
	} 
}