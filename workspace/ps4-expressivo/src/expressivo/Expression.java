package expressivo;

import expressivo.expression.Multiply;
import expressivo.expression.Number;
import expressivo.expression.Plus;
import expressivo.expression.Variable;
import lib6005.parser.*;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //Expression = Number(n: int) + Variable(s: String) 
    //+ Plus(left: Expression, right: Expression) 
    //+ Multiply (left: Expression, right: Expression)
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        throw new RuntimeException("unimplemented");
    }
    
    // ----------------- building up an expression -----------------
    
    public static Expression make(double num){
        return new Number(num);
    }
    
    public static Expression make(String var){
        return new Variable(var);
    }
    
    public static Expression sum(Expression left, Expression right){
        Number zero = new Number(0);
        if(left.equals(zero)){
            return right;
        }
        if(right.equals(zero)){
            return left;
        }
        return new Plus(left,right);
    }
    
   public static Expression times(Expression left, Expression right){
       Number zero = new Number(0);
       Number one = new Number(1);
       if(left.equals(zero)||right.equals(zero)){
           return new Number(0);
       }
       if(left.equals(one)){
           return right;
       }
       if(right.equals(one)){
           return left;
       }
       return new Multiply(left,right);
   }
   
    // ----------------- standard observers -----------------
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
