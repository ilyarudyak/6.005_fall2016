package expressivo.expression;

import expressivo.Expression;

public class Number implements Expression {
    
    private final double num;
    
    

    public Number(double num) {
        if (num < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        this.num = num;
    }
    
    @Override
    public String toString() {
        return "Number [num=" + num + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(num);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Number other = (Number) obj;
        if (Double.doubleToLongBits(num) != Double.doubleToLongBits(other.num))
            return false;
        return true;
    }


    
    

}
