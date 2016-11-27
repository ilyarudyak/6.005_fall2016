package library;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.ObjDoubleConsumer;
import java.util.stream.Collectors;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut; 
    private UUID id;
    
    public Set<BookCopy> getInLibrary() {
        return inLibrary;
    }

    public Set<BookCopy> getCheckedOut() {
        return checkedOut;
    }

    public SmallLibrary() {
        inLibrary = new HashSet<>();
        checkedOut = new HashSet<>();
        id = UUID.randomUUID();
        checkRep();
    }
    
    
    
    public UUID getId() {
        return id;
    }

    // assert the rep invariant
    // http://stackoverflow.com/questions/8882097/is-there-a-way-to-calculate-the-intersection-of-two-sets
    private void checkRep() {
        Set<BookCopy> intersection = new HashSet<>(inLibrary);
        intersection.retainAll(new HashSet<>(checkedOut));
        assert intersection.isEmpty();
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy bookCopy = new BookCopy(book);
        inLibrary.add(bookCopy);
        return bookCopy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if (isAvailable(copy)) {
            inLibrary.remove(copy);
            checkedOut.add(copy);
            checkRep();
        }
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if (checkedOut.contains(copy)) {
            checkedOut.remove(copy);
            inLibrary.add(copy);
            checkRep();
        }
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    // -------------------------------------------
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> allCopies = new HashSet<>();

        allCopies.addAll(availableCopies(book));
        allCopies.addAll(checkoutCopies(book));
        
        return allCopies;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        return inLibrary.stream()
                .filter(copy -> copy.getBook().equals(book))
                .collect(Collectors.toSet());
    }
    
    public Set<BookCopy> checkoutCopies(Book book) {
        return checkedOut.stream()
                .filter(copy -> copy.getBook().equals(book))
                .collect(Collectors.toSet());
    }

    // -------------------------------------------
    
    private Set<Book> allBooksInLibrary() {
        Set<BookCopy> allBookCopies = new HashSet<>();
        allBookCopies.addAll(inLibrary);
        allBookCopies.addAll(checkedOut);
        return allBookCopies.stream()
                .map(BookCopy::getBook)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Book> find(String query) {
        return allBooksInLibrary().stream()
                // exact matching of title or author
                .filter(book -> book.getTitle().equals(query) || 
                        book.getAuthors().contains(query))
                // remove duplicates
                .collect(Collectors.toSet())
                .stream()
                // newer books should appear first
                .sorted(Comparator.comparing(Book::getYear).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public void lose(BookCopy copy) {
        
        if (inLibrary.contains(copy)) {
            inLibrary.remove(copy);
        } 
        
        if (checkedOut.contains(copy)) {
            checkedOut.remove(copy);
        }
        
        checkRep();
    }

    // -------------------------------------------
    
    @Override
    public boolean equals(Object that) {
     
        if (this == that) {
            return true;
        }
     
        if (that == null || getClass() != that.getClass()) {
            return false;
        }
     
        SmallLibrary thatLibrary = (SmallLibrary) that;
     
        return inLibrary.equals(thatLibrary.getInLibrary()) &&
             checkedOut.equals(thatLibrary.getCheckedOut()) &&
             id.equals(thatLibrary.getId());

    }
 
    @Override
    public int hashCode() {
             return Objects.hash(inLibrary, checkedOut, id);
    }
    

    public static void main(String[] args) {
        
         Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
         Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5));
         set1.retainAll(set2);
         System.out.println(set1);
    }
}















