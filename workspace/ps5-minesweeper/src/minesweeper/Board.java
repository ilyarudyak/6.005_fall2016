/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import minesweeper.server.MinesweeperServer;

/**
 * TODO: Specification
 */
public class Board {
    
    private static double BOMB_PROBABILITY = .25;
    private static String BOMB_SYMBOL = "1";
    
    private int boardXSize;
    private int boardYSize;
    private ConcurrentHashMap<Point, Boolean> bombs;
    private ConcurrentHashMap<Point, Square> board;
    
    public Board() {
        this(0, 0, new ArrayList<Point>());
    }
    
    public Board(String fileName) {
        buildBoardFromFile(fileName);
    }
    
    public Board(int boardXSize, int boardYSize, List<Point> bombsList) {
        this.boardXSize = boardXSize;
        this.boardYSize = boardYSize;
        buildBombsFromList(bombsList);
        buildBoardUsingSizeAndBombList();
    }
    
    // ----------------- build bomb helpers --------------
    
    public static Board buildRandomBoard(int sizeX, int sizeY) {
        return new Board(sizeX, sizeY, buildRandomBombList(sizeX, sizeY));
    }
    
    private static List<Point> buildRandomBombList(int sizeX, int sizeY) {
        Random random = new Random(0);
        List<Point> bombList = new ArrayList<>();
        for (int y = 0; y < sizeY; y++)  {
            for (int x = 0; x < sizeX; x++){
                double probability = random.nextFloat();
                if (probability <= BOMB_PROBABILITY) {
                    bombList.add(new Point(x, y));
                }   
            }
        }
        return bombList;
    }
    
    private void buildBombsFromList(List<Point> bombsList) {
        bombs = new ConcurrentHashMap<>();
        bombsList.forEach(point -> bombs.put(point, true));
    }
    
    private void buildBoardUsingSizeAndBombList() {
        board = new ConcurrentHashMap<>();
        for (int x = 0; x < boardXSize; x++) {
            for (int y = 0; y < boardYSize; y++) {
                Point point = new Point(x, y);
                board.computeIfAbsent(point, p -> new Square(p, bombs.containsKey(p)));
            }
        }
    }
    
    // ----------------- build bomb from file --------------
    
    private void buildBoardFromFile(String fileName) {
        try(Stream<String> linesStream = Files.lines(Paths.get(fileName))) {
            List<String> linesList = linesStream.collect(Collectors.toList());
            getBoardSizeFromLine(linesList.get(0));
            buildBombsFromFile(linesList);
            buildBoardUsingSizeAndBombList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void buildBombsFromFile(List<String> linesList) {
        List<Point> bombsList = Stream.iterate(0, y -> y + 1)
            .limit(linesList.size() - 1)
            .flatMap(y -> getBombsFromLine(linesList.get(y + 1), y))
            .collect(Collectors.toList());
        buildBombsFromList(bombsList);
    }
    
    private void getBoardSizeFromLine(String line) throws IOException {
        String[] size = Pattern.compile("\\s{1}").split(line);
        boardXSize = Integer.parseInt(size[0]);
        boardYSize = Integer.parseInt(size[1]);
    }
    
    private Stream<Point> getBombsFromLine(String line, int y) {
        String[] lineArr = Pattern.compile("\\s{1}").split(line);
        return IntStream.range(0, lineArr.length)
                 .filter(x -> lineArr[x].equals(BOMB_SYMBOL))   
                 .mapToObj(x -> new Point(x, y));
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
        bombs.remove(point);
    }
    
    // ------------- calculate number of bombs -----------
    
    public int getBombCount(Point point) {
        Square square = board.get(point);
        return getAdjSquares(point)
                .stream()
                .mapToInt(s -> s.getBombCount())
                .sum();
    }
    
    public List<Point> getAdjPoints(Point point) {
        List<Point> adjPoints = new CopyOnWriteArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point p = new Point(point.getX() + i, point.getY() + j);
                if (!p.equals(point) && isOnBoard(p)) {
                    adjPoints.add(p);
                }
            }
        }
        return adjPoints;
    }
    
    public List<Square> getAdjSquares(Point point) {
        List<Point> adjPoints = getAdjPoints(point);
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
        for (int y = 0; y < boardYSize; y++)  {
            for (int x = 0; x < boardXSize; x++) {
                Point point = new Point(x, y);
                Square square = board.get(point);
                int bombCount = getBombCount(point);
                if (square.isDug() && bombCount > 0) {
                    boardStrBuf.append(Integer.toString(bombCount));
                } else {
                    boardStrBuf.append(board.get(point).toString());
                }
                if (x != boardXSize - 1) {
                    boardStrBuf.append(" ");
                }
            }
            if (y < boardYSize - 1) {
                boardStrBuf.append("\n");
            }
        }
        return boardStrBuf.toString();
    }
    
    // debugging printing for board
    public String printBoard() {
        StringBuffer boardStrBuf = new StringBuffer();
        for (int y = 0; y < boardYSize; y++)  {
            for (int x = 0; x < boardXSize; x++){
                Point point = new Point(x, y);
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
  
    // -------------- main --------------------------
    
    public static void main(String[] args) {
        
        String line = "0 1 0 1 0 1";
        int y = 5;
        Board b = new Board();
        b.getBombsFromLine(line, y).forEach(System.out::println);
        
    }
}

















