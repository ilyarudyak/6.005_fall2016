/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;

/**
 * TODO: Description
 */
public class BoardTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testConstructor() {
        Board board = new Board(3, 3, 
                Arrays.asList(new Board.Point(0, 0), new Board.Point(2, 2)));
//        System.out.println(board);
        
        Board.Point p00 = new Board.Point(0, 0);
        Board.Point p01 = new Board.Point(0, 1);
        Board.Point p22 = new Board.Point(2, 2);
        assertTrue(board.getBoard().get(p00).isContainBomb() &&
                   board.getBoard().get(p22).isContainBomb());
        assertFalse(board.getBoard().get(p01).isContainBomb());
        
        
        board.setSquareDug(p00);
//        System.out.println(board);
        assertTrue(board.getBoard().get(p00).isDug());
        assertFalse(board.getBoard().get(p00).isUntouched());
        
        
        board.setSquareFlagged(p01, true);
//        System.out.println(board);
        assertTrue(board.getBoard().get(p01).isFlagged());
        
        board.removeBomb(p00);
//        System.out.println(board);
        assertFalse(board.getBoard().get(p00).isContainBomb());
    }
    
    @Test
    public void testSquare() {
        Board.Point p11 = new Board.Point(1, 1);
        Board.Square s1 = new Board.Square(p11, false);
        Board.Square s2 = new Board.Square(p11, true);
        Board.Square s3 = new Board.Square(p11, true);
        s3.setDug();
        Board.Square s4 = new Board.Square(p11, false);
        s4.setFlagged(true);
        
        assertEquals("-", s1.toString());
        assertEquals("-", s2.toString());
        assertEquals("B", s3.toString());
        assertEquals("F", s4.toString());
    }
    
    @Test
    public void testPoint() {
        Board.Point p1 = new Board.Point(1, 1);
        Board.Point p2 = new Board.Point(1, 1);
        Board.Point p3 = new Board.Point(0, 0);
        
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
        
        assertTrue(p1.hashCode() == p2.hashCode());
        assertFalse(p1.hashCode() == p3.hashCode());
        
        assertEquals("(1,1)", p1.toString());
        
    }
    
    @Test
    public void testBuildingRandom() {
        Board board = Board.buildRandomBoard(0L);
        ConcurrentHashMap<Board.Point, Boolean> bombs = board.getBombs();
        List<Board.Point> points = Arrays.asList(
                new Board.Point(3, 2),
                new Board.Point(5, 4),
                new Board.Point(1, 0),
                new Board.Point(1, 1),
                new Board.Point(7, 7)
                );
        
        assertEquals(10, bombs.size());
        points.forEach(p -> assertTrue(bombs.get(p)));
    }
    
    @Test
    public void testgetNumberOfBombs() {
        Board board = Board.buildRandomBoard(0L);
        List<Board.Point> points = Arrays.asList(
                new Board.Point(0, 0),
                new Board.Point(9, 0),
                new Board.Point(2, 1),
                new Board.Point(4, 4),
                new Board.Point(8, 7)
        );

        System.out.println(board.printBoardWithBombCount());
        System.out.println();
        
        points.forEach(p -> board.setSquareFlagged(p, true));
        System.out.println(board.printBoard());
        
        
        assertEquals(2, board.getNumberOfBombs(points.get(0)));
        assertEquals(0, board.getNumberOfBombs(points.get(1)));
        assertEquals(3, board.getNumberOfBombs(points.get(2)));
        assertEquals(3, board.getNumberOfBombs(points.get(3)));
        assertEquals(2, board.getNumberOfBombs(points.get(4)));

    }
}











