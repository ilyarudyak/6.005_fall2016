package expressivo.expression;

import expressivo.Expression;

public class Variable implements Expression {
    
    private final String var;

    public Variable(String var) {
        if (!var.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Variable must contain only letters.");
        }
        this.var = var;
    }

    @Override
    public String toString() {
        return "Variable [var=" + var + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((var == null) ? 0 : var.hashCode());
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
        Variable other = (Variable) obj;
        if (var == null) {
            if (other.var != null)
                return false;
        } else if (!var.equals(other.var))
            return false;
        return true;
    }
    
    

}
