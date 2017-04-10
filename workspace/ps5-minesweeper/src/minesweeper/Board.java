/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import minesweeper.server.MinesweeperServer;

/**
 * TODO: Specification
 */
public class Board {
    
    private final int boardXSize;
    private final int boardYSize;
    private ConcurrentHashMap<Point, Boolean> bombs;
    private ConcurrentHashMap<Point, Square> board;
    
    public Board(int boardXSize, int boardYSize, List<Point> bombsList) {
        this.boardXSize = boardXSize;
        this.boardYSize = boardYSize;
        buildBombs(bombsList);
        buildBoard();
    }
    
    public static Board buildRandomBoard() {
        int defaultSize = MinesweeperServer.DEFAULT_SIZE;
        Random random = new Random(0);
        List<Point> bombsList = IntStream.range(0, defaultSize)
                .mapToObj(n -> new Point(random.nextInt(defaultSize), random.nextInt(defaultSize)))
                .collect(Collectors.toList());
        return new Board(defaultSize, defaultSize, bombsList);
    }
    
    private void buildBombs(List<Point> bombsList) {
        bombs = new ConcurrentHashMap<>();
        bombsList.forEach(point -> bombs.put(point, true));
    }
    
    private void buildBoard() {
        board = new ConcurrentHashMap<>();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(x, y);
                board.computeIfAbsent(point, p -> new Square(p, bombs.containsKey(p)));
            }
        }
    }

    public ConcurrentHashMap<Point, Square> getBoard() {
        return board;
    }

    public ConcurrentHashMap<Point, Boolean> getBombs() {
        return bombs;
    }

    public void setSquareDug(Point point) {
        board.computeIfPresent(point, (p, s) -> s.setDug());
    }
    
    public void setSquareFlagged(Point point, boolean isFlagged) {
        board.computeIfPresent(point, (p, s) -> s.setFlagged(isFlagged));
    }
    
    public void removeBomb(Point point) {
        board.computeIfPresent(point, (p, s) -> s.removeBomb());
    }
    
    @Override
    public String toString() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(x, y);
                boardStrBuf.append(board.get(point).toString());
            }
            boardStrBuf.append("\n");
        }
        return boardStrBuf.toString();
    }
    
    /** Debugging printing for board */
    public String printBoard() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(x, y);
                if (board.get(point).isContainBomb) {
                    boardStrBuf.append("B");
                } else {
                    boardStrBuf.append(board.get(point).toString());
                }
            }
            boardStrBuf.append("\n");
        }
        return boardStrBuf.toString();
    }
    
    static class Point {
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
    
    static class Square {
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
            if (!isUntouched) { assert isDug; }
        }
        
        // ----------- setters ------------
        
        public Square removeBomb() {
            this.isContainBomb = false;
            return this;
        }
        
        public Square setDug() {
            this.isDug = true;
            this.isUntouched = false;
            checkRep();
            return this;
        }
        
        public Square setFlagged(boolean isFlagged) {
            this.isFlagged = isFlagged;
            return this;
        }

        // ----------- getters ------------
        
        public Point getPoint() {
            return point;
        }
        
        public boolean isContainBomb() {
            return isContainBomb;
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
    
}

















