package library;

import java.time.Instant;import java.util.AbstractList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {
    
    private final Book book;
    private Condition condition;
    private UUID id;
    
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.book = new Book(book);
        this.condition = Condition.GOOD;
        this.id = UUID.randomUUID();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert Book.isAtLeastOneNonSpaceChar(book.getTitle());                    
        assert !book.getAuthors().isEmpty();                              
        assert book.getAuthors().stream()
                .filter(s -> !Book.isAtLeastOneNonSpaceChar(s))
                .collect(Collectors.toList())
                .isEmpty();                                      
        assert book.getYear() >= 0; 
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return book;
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    public UUID getId() {
        return id;
    }



    @Override
    public String toString() {
        return "BookCopy [book=" + book + ", condition=" + condition.toString().toLowerCase() + "]";
    }

     @Override
     public boolean equals(Object that) {
         
         if (this == that) {
             return true;
         }
         
         if (that == null || getClass() != that.getClass()) {
             return false;
         }
         
         BookCopy thatBookCopy = (BookCopy) that;
         
         return book.equals(thatBookCopy.getBook()) && 
//                 condition.equals(thatBookCopy.getCondition()) &&
                 id.equals(thatBookCopy.getId());
     }
     
     @Override
     public int hashCode() {
         return Objects.hash(book, condition, id);
     }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
     
     public static void main(String[] args) {
        
         Book book = new Book("title", Arrays.asList("author"), 2000);
         BookCopy bookCopy = new BookCopy(book);
         System.out.println(bookCopy);
    }

}














