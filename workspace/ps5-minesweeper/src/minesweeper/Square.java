package minesweeper;

public class Square {
    private final Point point;
    
    private boolean isContainBomb;
    
    private boolean isUntouched = true;
    private boolean isDug = false;
    private boolean isFlagged = false;
    
    
    public Square(Point point, boolean isContainBomb) {
        this.point = point;
        this.isContainBomb = isContainBomb;
    }
    
    private void checkRep() {
        if (isUntouched) { assert !isDug; }
        if (isDug) { assert !isUntouched && !isFlagged; }
    }
    
    // ----------- setters ------------
    
    public Square removeBomb() {
        this.isContainBomb = false;
        return this;
    }
    
    public Square setDug() {
        this.isDug = true;
        this.isUntouched = false;
        this.isFlagged = false;
        checkRep();
        return this;
    }
    
    public Square setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
        checkRep();
        return this;
    }

    // ----------- getters ------------
    
    public Point getPoint() {
        return point;
    }
    
    public boolean isContainBomb() {
        return isContainBomb;
    }
    
    public int getBombCount() {
        return isContainBomb ? 1 : 0;
    }
    
    public boolean isUntouched() {
        return isUntouched;
    }
    
    public boolean isDug() {
        return isDug;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    // ----------- other ------------

    @Override
    public String toString() {
        if (isFlagged) return "F";
        else if (isDug && isContainBomb) return "B";
        else if (isDug && !isContainBomb) return " ";
        else if (isUntouched && !isFlagged) return "-";
        else throw new RuntimeException("wrong Square");
        
    }
    
}
