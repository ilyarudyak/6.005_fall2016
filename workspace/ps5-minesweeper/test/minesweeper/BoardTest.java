/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO: Description
 */
public class BoardTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testConstructorSimple() {
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
    public void testSquareSimple() {
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
    public void testPointSimple() {
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
        Board board = Board.buildRandomBoard();
        System.out.println(board);
        System.out.println();
        System.out.println(board.printBoard());
        System.out.println(board.getBombs());
    }
}
