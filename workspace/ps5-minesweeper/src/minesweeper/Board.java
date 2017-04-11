/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
        return buildRandomBoard(Instant.now().getEpochSecond());
    }
    
    public static Board buildRandomBoard(Long seed) {
        int defaultSize = MinesweeperServer.DEFAULT_SIZE;
        Random random = new Random(seed);
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
                Point point = new Point(y, x);
                board.computeIfAbsent(point, p -> new Square(p, bombs.containsKey(p)));
            }
        }
    }

    // -------------- getters ----------------------------

    public int getBoardXSize() {
        return boardXSize;
    }

    public int getBoardYSize() {
        return boardYSize;
    }
    
    public ConcurrentHashMap<Point, Square> getBoard() {
        return board;
    }

    public ConcurrentHashMap<Point, Boolean> getBombs() {
        return bombs;
    }

    // -------------- operations on the board ------------
    
    public void setSquareDug(Point point) {
        board.computeIfPresent(point, (p, s) -> s.setDug());
    }
    
    public void setSquareFlagged(Point point, boolean isFlagged) {
        board.computeIfPresent(point, (p, s) -> s.setFlagged(isFlagged));
    }
    
    public void removeBomb(Point point) {
        board.computeIfPresent(point, (p, s) -> s.removeBomb());
    }
    
    // ------------- calculate number of bombs -----------
    
    public int getBombCount(Point point) {
        Square square = board.get(point);
        return getAdjSquares(point)
                .stream()
                .mapToInt(s -> s.getBombCount())
                .sum();
    }
    
    private List<Square> getAdjSquares(Point point) {
        List<Point> adjPoints = new CopyOnWriteArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point p = new Point(point.getX() + i, point.getY() + j);
                if (!p.equals(point) && isOnBoard(p)) {
                    adjPoints.add(p);
                }
            }
        }
        return adjPoints.stream().map(p -> board.get(p)).collect(Collectors.toList());
    }
    
    public boolean isOnBoard(Point point) {
        return 0 <= point.getX() && point.getX() < boardXSize &&
               0 <= point.getY() && point.getY() < boardYSize; 
    }
    
    // -------------- printing --------------------------
    
    @Override
    public String toString() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(y, x);
                Square square = board.get(point);
                int bombCount = getBombCount(point);
                if (square.isDug() && bombCount > 0) {
                    boardStrBuf.append(Integer.toString(bombCount));
                } else {
                    boardStrBuf.append(board.get(point).toString());
                }
            }
            boardStrBuf.append("\n");
        }
        return boardStrBuf.toString();
    }
    
    // debugging printing for board
    public String printBoard() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(y, x);
                if (board.get(point).isContainBomb()) {
                    boardStrBuf.append("B");
                } else {
                    boardStrBuf.append(board.get(point).toString());
                }
            }
            boardStrBuf.append("\n");
        }
        return boardStrBuf.toString();
    }
    
    public String printBoardWithBombCount() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(y, x);
                if (board.get(point).isContainBomb()) {
                    boardStrBuf.append("B");
                } else if (getBombCount(point) > 0) {
                    boardStrBuf.append(getBombCount(point));
                } else {
                    boardStrBuf.append(board.get(point).toString());
                }
            }
            boardStrBuf.append("\n");
        }
        return boardStrBuf.toString();
    }
  
    

    
}

















