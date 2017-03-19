package expressivo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    // Expression = Number(num: double) + Var(var: [A-Za-z]+) 
    // + Add(leftOp: Expression, rightOp: Expression) 
    // + Multiply(leftOp: Expression, rightOp: Expression)
    enum Grammar {ROOT, SUM, PRODUCT, TOKEN, PRIMITIVE_1, PRIMITIVE_2, NUMBER, INT, DECIMAL, WHITESPACE, VARIABLE};

    public static Expression create(Expression leftExpr, Expression rightExpr, char op) {
        if  (op == '+')
            return SumExpression.createSum(leftExpr, rightExpr);
        else
            return MultiplyExpression.createProduct(leftExpr, rightExpr);
    }
    public static Expression accumulator(ParseTree<Grammar> tree, Grammar grammarObj) {
        Expression expr = null;
        boolean first = true;
        List<ParseTree<Grammar>> children = tree.children();
        int len = children.size();
        for (int i = len-1; i >= 0; i--) {
            /* the first child */
            ParseTree<Grammar> child = children.get(i);
            if (first) {
                expr = buildAST(child);
                first = false;
            }
            
            /* accumulate this by creating a new binaryOp object with
             *  expr as the leftOp and the result as rightOp
             **/
            
            else if (child.getName() == Grammar.WHITESPACE) continue;
            else {
                if (grammarObj == Grammar.SUM)
                    expr = new SumExpression(buildAST(child), expr);
                else
                    expr = new MultiplyExpression(buildAST(child), expr);
            }
        }
        
        return expr;
        
    }
    
    /**
     * This function is a DFS on the concrete syntax tree (CST)
     * @param tree: the CST to be parsed into an Expression (AST)
     * @return the AST corresponding to this tree
     */
    public static Expression buildAST(ParseTree<Grammar> tree) {
        if (tree.getName() == Grammar.DECIMAL) {
            /* reached a double terminal */
            return new Num(Double.parseDouble(tree.getContents()));            
        }

        else if (tree.getName() == Grammar.INT) {
            /* reached an int terminal */
            return new Num(Integer.parseInt(tree.getContents()));
        }
        
        else if (tree.getName() == Grammar.VARIABLE) {
            /* reached a terminal */
            return new Var(tree.getContents());
        }
        
        else if (tree.getName() == Grammar.ROOT || tree.getName() == Grammar.TOKEN || tree.getName() == Grammar.PRIMITIVE_1 || tree.getName() == Grammar.PRIMITIVE_2 || tree.getName() == Grammar.NUMBER) {
            /* non-terminals with only one child */
            for (ParseTree<Grammar> child: tree.children()) {
                if (child.getName() != Grammar.WHITESPACE) 
                    return buildAST(child);
            }
            
            // should never reach here
            throw new IllegalArgumentException("error in parsing");
        }
        
        else if (tree.getName() == Grammar.SUM || tree.getName() == Grammar.PRODUCT) {
            /* a sum or product node can have one or more children that need to be accumulated together */
            return accumulator(tree, tree.getName());   
         }
        
        else {
            throw new IllegalArgumentException("error in input: should never reach here");
        }
       
    }
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input , Note that the expression is parsed from right to left: x*x*x => (x*(x*x)) & x+y+z => (x+(y+z))
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        try {
            Parser<Grammar> parser = GrammarCompiler.compile(new File("/Users/apple/Documents/workspace/ps1-expressivo/src/expressivo/Expression.g"), Grammar.ROOT);
            ParseTree<Grammar> tree = parser.parse(input);
            return buildAST(tree);
            
        }
        
        catch (UnableToParseException e) {
            throw new IllegalArgumentException("Cannot parse the expression.");
        }
        catch (IOException e) {
            System.out.println("cannot open file Expression.g");
            return null;
        }
    }
    

    /**
     * @param x: Var object to be differentiated with respect to
     * @return derivative of this wrt x
     */
    
    public Expression differentiate(Var x);
    
    
    /**
     * @param env: Map<String, double> mapping variables to values: 
     * Keys (must be non-empty, case-sensitive) in env will be used to create Var objects. 
     * 
     * @return expr: Expression obtained by substituting each var in this (such that it has a mapping in env) and simplifying. 
     */
    
    public Expression simplify(Map<String, Double> env);
    
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
    
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
