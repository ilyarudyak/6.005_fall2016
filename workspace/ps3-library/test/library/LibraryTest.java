package library;

import static org.junit.Assert.*;
import static java.util.Arrays.asList;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {
    
    private static Book book1;
    private static Book book2;
    private static Book book3;
    private static Book book4;
    private static Book book5;
    
    private static Library library;
    private static List<Book> booksToBuy;
    private static List<BookCopy> bookCopies;
    
    @Before
    public void setUp() {
        book1 = new Book("title1", Arrays.asList("author1"), 2000);
        book2 = new Book("title1", Arrays.asList("author1"), 1900); // the same as book1 except year
        book3 = new Book("title3", Arrays.asList("author3"), 1950); // different from book1 but the same author
        book4 = new Book("title4", Arrays.asList("author4", "author44 long name", "author1"), 1998); // 2 authors including author1
        book5 = new Book("title: title5", Arrays.asList("author5"), 1999); // long title
        
        library = makeLibrary();
        booksToBuy = Arrays.asList(book1, book1, book1, book2, book3, book3, book4, book5);
        bookCopies = new ArrayList<>();
        
    }
    
    private void buySomeBooks() {
        booksToBuy.forEach(book -> bookCopies.add(library.buy(book)));
    }

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     * 
     * TODO: your testing strategy for this ADT should go here.
     * Make sure you have partitions.
     */
    
    @Test
    public void buyFirstCopy() {
        BookCopy copy = library.buy(book1);
        
        assertTrue(library.isAvailable(copy));
        
    }
    
    @Test
    public void buySecondCopy() {
        BookCopy copy1 = library.buy(book1);
        BookCopy copy2 = library.buy(book1);
        
        assertFalse(copy1.equals(copy2));
        assertTrue(library.isAvailable(copy1) && library.isAvailable(copy2));
        
    }
    
    @Test
    public void buyDifferentBook() {
        BookCopy copy1 = library.buy(book1);
        BookCopy copy2 = library.buy(book2);
        
        assertTrue(library.isAvailable(copy1) && library.isAvailable(copy2));
        
    }

    @Test
    public void buyManyBooks() {
        
        buySomeBooks();
        bookCopies.forEach(copy -> assertTrue(library.isAvailable(copy)));
        assertTrue(library.availableCopies(book1).size() == 3);
        assertTrue(library.availableCopies(book2).size() == 1);
        assertTrue(library.availableCopies(book3).size() == 2);
    }
    
    @Test
    public void checkOut() {
        
        buySomeBooks();
        
        BookCopy copy0 = bookCopies.get(0);
        BookCopy copy1 = bookCopies.get(1);
        BookCopy copy2 = bookCopies.get(2);
        library.checkout(copy0);
        assertFalse(library.isAvailable(copy0));
        assertTrue(library.availableCopies(book1).size() == 2);
        assertTrue(library.availableCopies(book1).containsAll(asList(copy1, copy2)));
        
        library.checkout(copy1);
        assertFalse(library.isAvailable(copy1));
        assertTrue(library.availableCopies(book1).size() == 1);
        assertTrue(library.availableCopies(book1).contains(copy2));
        
        library.checkout(copy2);
        assertFalse(library.isAvailable(copy2));
        assertTrue(library.availableCopies(book1).isEmpty());
        
    }
    
    @Test
    public void checkInCheckOut() {
     
        buySomeBooks();
        
        BookCopy copy0 = bookCopies.get(0);
        BookCopy copy1 = bookCopies.get(1);
        BookCopy copy2 = bookCopies.get(2);
        
        library.checkout(copy0);
        library.checkout(copy1);
        library.checkout(copy2);
        
        assertTrue(library.availableCopies(book1).isEmpty());
        
        library.checkin(copy0);
        assertTrue(library.isAvailable(copy0));
        assertTrue(library.availableCopies(book1).size() == 1);
        
        library.checkin(copy0);
        assertTrue(library.isAvailable(copy0));
        assertTrue(library.availableCopies(book1).size() == 1);
        
        library.checkout(copy0);
        library.checkin(copy1);
        assertFalse(library.isAvailable(copy0));
        assertTrue(library.isAvailable(copy1));
        assertTrue(library.availableCopies(book1).size() == 1);
        
    }
    
    @Test
    public void findExactTitle() {
     
        buySomeBooks();
        
        List<Book> books = library.find(book1.getTitle());
        // newest first, only one matching book in list
        assertEquals(books, asList(book1, book2)); 
        
        List<Book> books2 = library.find(book5.getTitle());
        assertEquals(books2, asList(book5));
        
    }
    
    @Test
    public void findExactAuthor() {
        buySomeBooks();
        
        List<Book> books = library.find(book1.getAuthors().get(0));
        // newest first, only one matching book in list
        assertEquals(books, asList(book1, book4, book2)); 
        
        List<Book> books2 = library.find(book4.getAuthors().get(1));
        assertEquals(books2, asList(book4));
    }
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


}















































