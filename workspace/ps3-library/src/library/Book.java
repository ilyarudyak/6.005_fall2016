package library;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {
    
    private final String title;
    private final List<String> authors;
    private final int year;
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.authors = Collections.unmodifiableList(authors);
        this.year = year;
        checkRep();
    }
    
    public Book(Book book) {
        this(book.getTitle(), book.getAuthors(), book.getYear());
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert isAtLeastOneNonSpaceChar(title);                    
        assert !authors.isEmpty();                              
        assert authors.stream()
                .filter(s -> !isAtLeastOneNonSpaceChar(s))
                .collect(Collectors.toList())
                .isEmpty();                                      
        assert year >= 0; 
    }
    
    static boolean isAtLeastOneNonSpaceChar(String s) {
        return !s.replaceAll("\\s+", "").isEmpty();
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    @Override
    public String toString() {
        return "Book [title=" + title + ", authors=" + authors + ", year=" + year + "]";
    }
    

     @Override
     public boolean equals(Object that) {
         
         if (this == that) {
             return true;
         }
         
         if (that == null || getClass() != that.getClass()) {
             return false;
         }
         
         Book thatBook = (Book) that;
         
         return title.equals(thatBook.getTitle()) &&
                 authors.equals(thatBook.getAuthors()) &&
                 year == thatBook.getYear();
     }
     
     @Override
     public int hashCode() {
         return Objects.hash(title, authors, year);
     }
    
    public static void main(String[] args) {
        
        String s = "a    b c";
        System.out.println(s.replaceAll("\\s+", ""));
    }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}












