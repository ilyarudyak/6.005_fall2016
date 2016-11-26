package library;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     */
    
    
    @Test
    public void testExampleTest() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals("This Test Is Just An Example", book.getTitle());
    }
    
    @Test(expected=AssertionError.class)
    public void checkRepEmptyTitle() {
        Book book = new Book("   ", Arrays.asList("author"), 2000);
    }
    
    @Test(expected=AssertionError.class)
    public void checkRepEmptyAuthorList() {
        Book book = new Book("title", new ArrayList<String>(), 2000);
    }
    
    @Test(expected=AssertionError.class)
    public void checkRepEmptyAuthors() {
        Book book = new Book("title", Arrays.asList("author", "   "), 2000);
    }
    
    @Test(expected=AssertionError.class)
    public void checkRepNegativeYear() {
        Book book = new Book("title", Arrays.asList("author"), -2000);
    }
    
    
    @Test
    public void equalsSimpleEqualBooks() {
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("title", Arrays.asList("author"), 2000);
        assertEquals(book1, book2);
    }
    
    @Test
    public void equalsSimpleEqualBooksYear() {
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("title", Arrays.asList("author"), 2001);
        assertNotEquals(book1, book2);
    }
    
    @Test
    public void equalsSimpleNotEqualBooksCaseSensitiveAuthor() {
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("title", Arrays.asList("auThoR"), 2000);
        assertNotEquals(book1, book2);
    }
    
    @Test
    public void equalsSimpleNotEqualBooksCaseSensitiveTitle() {
        Book book1 = new Book("title", Arrays.asList("author"), 2000);
        Book book2 = new Book("tiTlE", Arrays.asList("author"), 2000);
        assertNotEquals(book1, book2);
    }
    
    @Test
    public void equalsSimpleNotEqualBooksAuthorsOrder() {
        Book book1 = new Book("title", Arrays.asList("author1", "author2"), 2000);
        Book book2 = new Book("title", Arrays.asList("author2", "author1"), 2000);
        assertNotEquals(book1, book2);
    }
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


}














