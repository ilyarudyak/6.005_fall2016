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
                Arrays.asList(new Point(0, 0), new Point(2, 2)));
//        System.out.println(board);
        
        Point p00 = new Point(0, 0);
        Point p01 = new Point(0, 1);
        Point p22 = new Point(2, 2);
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
        Point p11 = new Point(1, 1);
        Square s1 = new Square(p11, false);
        Square s2 = new Square(p11, true);
        Square s3 = new Square(p11, true);
        s3.setDug();
        Square s4 = new Square(p11, false);
        s4.setFlagged(true);
        
        assertEquals("-", s1.toString());
        assertEquals("-", s2.toString());
        assertEquals("B", s3.toString());
        assertEquals("F", s4.toString());
    }
    
    @Test
    public void testPoint() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(0, 0);
        
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
        
        assertTrue(p1.hashCode() == p2.hashCode());
        assertFalse(p1.hashCode() == p3.hashCode());
        
        assertEquals("(1,1)", p1.toString());
        
    }
    
    @Test
    public void testBuildingRandom() {
        Board board = Board.buildRandomBoard(0L);
        ConcurrentHashMap<Point, Boolean> bombs = board.getBombs();
        List<Point> points = Arrays.asList(
                new Point(3, 2),
                new Point(5, 4),
                new Point(1, 0),
                new Point(1, 1),
                new Point(7, 7)
                );
        
        assertEquals(10, bombs.size());
        points.forEach(p -> assertTrue(bombs.get(p)));
    }
    
    @Test
    public void testgetNumberOfBombs() {
        Board board = Board.buildRandomBoard(0L);
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(9, 0),
                new Point(2, 1),
                new Point(4, 4),
                new Point(8, 7)
        );

        System.out.println(board.printBoardWithBombCount());
        System.out.println();
        
        points.forEach(p -> board.setSquareFlagged(p, true));
        System.out.println(board.printBoard());
        
        
        assertEquals(2, board.getBombCount(points.get(0)));
        assertEquals(0, board.getBombCount(points.get(1)));
        assertEquals(3, board.getBombCount(points.get(2)));
        assertEquals(3, board.getBombCount(points.get(3)));
        assertEquals(2, board.getBombCount(points.get(4)));

    }
}











