package minesweeper;

import java.util.Objects;


public class Point {
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Point objPoint = (Point) obj;
        return x == objPoint.x && y == objPoint.y;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
    
    
}
