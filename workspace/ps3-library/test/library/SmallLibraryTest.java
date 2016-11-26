package library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SmallLibraryTest {
    
    private Library libraryA;
    private Library libraryB;
    private Library libraryC;
    
    @Before
    public void buildLibraries() {
        libraryA = new SmallLibrary();
        libraryB = new SmallLibrary();
        libraryC = new SmallLibrary();
        libraryC.buy(new Book("The Happy Return", Arrays.asList("C.S. Forester"), 1937));
    }
    
    @Test @Ignore
    public void testLose() {
        Library library = new SmallLibrary();
        Book book = new Book("Midshipman Hornblower", Arrays.asList("C.S. Forester"), 1950);
        
        BookCopy copy1 = library.buy(book);
        BookCopy copy2 = library.buy(book);
        
        // lost copy was checked out, >1 copies of book
        library.checkout(copy2);
        library.lose(copy2);
        assertEquals(new HashSet<>(Arrays.asList(copy1)), library.allCopies(book)); 
        
        // lost copy was available, 1 copy of book
        library.lose(copy1);
        assertFalse(library.isAvailable(copy1));        
        assertTrue(library.allCopies(book).isEmpty()); 
    }  
    
    @Test
    public void testEqualsHashcodeReflexive() {

        assertTrue(libraryA.equals(libraryA));
        assertEquals(libraryA.hashCode(), libraryA.hashCode());
    }
    
    @Test
    public void testEqualsHashcodeDifferentLibrariesEmpty() {
        
        // different libraries, same contents (empty)
        assertFalse(libraryA.equals(libraryB));

    }
    
    @Test
    public void testEqualsHashcodeDifferentLibrariesDifferentContent() {
        
        // different libraries, different contents
        assertFalse(libraryA.equals(libraryC));
    }

}




















