package expressivo.expression;

import expressivo.Expression;

public class Plus implements Expression {
    
    private final Expression left;
    private final Expression right;
    
    public Plus(Expression left, Expression right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Plus [left=" + left + ", right=" + right + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plus other = (Plus) obj;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        return true;
    }

}
