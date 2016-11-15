package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T08:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest tAlk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alYsSa", "secOnD tweet by alyssa", d1);
    private static final Tweet tweet4 = new Tweet(4, "alYsSa", "i'm out of timespan", d3);
    private static final Tweet tweet5 = new Tweet(5, "alYsSa", "talking - substring bug testing", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // ------------ writtenBy ---------------
    
    @Test
    public void testWrittenByEmptyList() {
        /*
         * An empty list of tweets should not return any tweets.
         */
        List<Tweet> filteredTweets = Filter.writtenBy(new ArrayList<Tweet>(),
                "test");

        assertTrue("expected empty list", filteredTweets.isEmpty());
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByCorrectOrder() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet3), "alyssa");
       
        
        assertEquals(1, writtenBy.get(0).getId());
        assertEquals(3, writtenBy.get(1).getId());
    }
    
    // ------------ inTimespan ---------------
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet4), new Timespan(testStart, testEnd));
        
        assertTrue(inTimespan.size() == 2);
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsBoundaries() {
        Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    // ------------ containing ---------------
    
    @Test
    public void testContainingEmptyList() {
        /*
         * Empty tweets -> return nothing
         */
        List<Tweet> filteredTweets = Filter.containing(new ArrayList<Tweet>(),
                Arrays.asList("test"));

        assertTrue("expected empty list", filteredTweets.isEmpty());
    }
    
    @Test
    public void testContainingNoWordsToCheck() {
        /*
         * Comparing tweets against no words -> return nothing
         */
        List<Tweet> filteredTweets = Filter.containing(Arrays.asList(tweet1, tweet2),
                new ArrayList<String>());

        assertTrue("expected empty list", filteredTweets.isEmpty());
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testContainingMultipleWordsInList() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "second"));
        
        assertTrue("expected list to contain all tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
    }
    
    @Test
    public void testContainingMultipleNotContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet3), Arrays.asList("talk"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingSubstringBug() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet5), Arrays.asList("talk"));
        
        assertTrue("expected empty list", containing.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
