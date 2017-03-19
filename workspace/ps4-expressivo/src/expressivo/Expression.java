package expressivo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import expressivo.expression.Product;
import expressivo.expression.Num;
import expressivo.expression.Sum;
import expressivo.expression.Var;
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
    
    enum ExpressivoGrammar {ROOT, SUM, PRODUCT, TOKEN, PRIMITIVE_1, PRIMITIVE_2, 
        NUMBER, INT, DECIMAL, WHITESPACE, VARIABLE};
    
    public static Expression buildAST(ParseTree<ExpressivoGrammar> concreteSymbolTree) {
        
        if (concreteSymbolTree.getName() == ExpressivoGrammar.DECIMAL) {
            /* reached a double terminal */
            return new Num(Double.parseDouble(concreteSymbolTree.getContents()));            
        }

        else if (concreteSymbolTree.getName() == ExpressivoGrammar.INT) {
            /* reached an int terminal */
            return new Num(Integer.parseInt(concreteSymbolTree.getContents()));
        }
        
        else if (concreteSymbolTree.getName() == ExpressivoGrammar.VARIABLE) {
            /* reached a terminal */
            return new Var(concreteSymbolTree.getContents());
        }
        
        else if (concreteSymbolTree.getName() == ExpressivoGrammar.ROOT        || 
                 concreteSymbolTree.getName() == ExpressivoGrammar.TOKEN       || 
                 concreteSymbolTree.getName() == ExpressivoGrammar.PRIMITIVE_1 || 
                 concreteSymbolTree.getName() == ExpressivoGrammar.PRIMITIVE_2 || 
                 concreteSymbolTree.getName() == ExpressivoGrammar.NUMBER) {
            
            /* non-terminals with only one child */
            for (ParseTree<ExpressivoGrammar> child: concreteSymbolTree.children()) {
                if (child.getName() != ExpressivoGrammar.WHITESPACE) 
                    return buildAST(child);
            }
            
            // should never reach here
            throw new IllegalArgumentException("error in parsing");
        }
        
        else if (concreteSymbolTree.getName() == ExpressivoGrammar.SUM || concreteSymbolTree.getName() == ExpressivoGrammar.PRODUCT) {
            /* a sum or product node can have one or more children that need to be accumulated together */
            return accumulator(concreteSymbolTree, concreteSymbolTree.getName());   
         }
        
        else {
            throw new IllegalArgumentException("error in input: should never reach here");
        }
       
    }
    
    /**
     * (1) Create parser using lib6005.parser from grammar file
     * (2) Parse string input into CST
     * (3) Build AST from this CST using buildAST()
     * @param input
     * @return Expression (AST)
     */
    public static Expression parse(String input) {
        
        try {
            Parser<ExpressivoGrammar> parser = GrammarCompiler.compile(
                    new File("src/expressivo/Expression.g"), ExpressivoGrammar.ROOT);
            ParseTree<ExpressivoGrammar> concreteSymbolTree = parser.parse(input);
            
//            tree.display();
            
            return buildAST(concreteSymbolTree);
            
        }
        
        catch (UnableToParseException e) {
            throw new IllegalArgumentException("Can't parse the expression...");
        }
        catch (IOException e) {
            System.out.println("Cannot open file Expression.g");
            throw new RuntimeException("Can't open the file with grammar...");
        }
    }
    
    // helper methods
    public static Expression accumulator(ParseTree<ExpressivoGrammar> tree, ExpressivoGrammar grammarObj) {
        Expression expr = null;
        boolean first = true;
        List<ParseTree<ExpressivoGrammar>> children = tree.children();
        int len = children.size();
        for (int i = len-1; i >= 0; i--) {
            /* the first child */
            ParseTree<ExpressivoGrammar> child = children.get(i);
            if (first) {
                expr = buildAST(child);
                first = false;
            }
            
            /* accumulate this by creating a new binaryOp object with
             *  expr as the leftOp and the result as rightOp
             **/
            
            else if (child.getName() == ExpressivoGrammar.WHITESPACE) continue;
            else {
                if (grammarObj == ExpressivoGrammar.SUM)
                    expr = new Sum(buildAST(child), expr);
                else
                    expr = new Product(buildAST(child), expr);
            }
        }
        
        return expr;
        
    }
    
    // ----------------- problems 3-4 -----------------
    
    public static Expression create(Expression leftExpr, Expression rightExpr, char op) {
        if  (op == '+')
            return Sum.createSum(leftExpr, rightExpr);
        else
            return Product.createProduct(leftExpr, rightExpr);
    }

    public Expression differentiate(Var x);
    
    public Expression simplify(Map<String, Double> env);

}
