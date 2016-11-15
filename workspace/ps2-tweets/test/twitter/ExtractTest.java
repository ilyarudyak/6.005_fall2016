package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy.
     * 
     * Partitions:
     * 
     *  - no usernames, 1 or 2 usernames
     *  - contains _, -; contains not legal characters 
     *  - username in the beginning or end of the string
     *  - username is preceded by a legal character, so it's not a mention: bitdiddle@mit.edu
     *    but this is a mention of @mit: @mit.edu
     *  - usernames are case insensitive
     *  - we return set of unique usernames, so string should contain repetitions 
     *  - length of username == 0 (not legal), 1, > 1   
     * 
     * 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    // tweets to test mentions
    // beginning or end of the string, case insensitive, unique usernames
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "@user1 #hype @usER1 @uSeR2", d1);
    // length of username > 0, contains _, -
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "abc @user89_- def @USer0_1-2 ght @_ htr @ #hype", d1);
    // not legal characters
    private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "@us&er def @user1& #hype @user2& bitdiddle@mit1.edu dfdfd @mit2.edu", d1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
 // -------------- getTimespan --------------
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // Fri 14 Feb 2015 01:18:18 PM EST
    private static final Instant d10 = Instant.ofEpochMilli(1423937898000L);
    // Sat 15 Feb 2015 01:18:18 PM EST
    private static final Instant d20 = Instant.ofEpochMilli(1424024298000L);
    // Sun 16 Feb 2015 11:18:18 AM EST
    private static final Instant d30 = Instant.ofEpochMilli(1424103498000L);

    private static final Tweet tweet10 = createTweetWithDate(10, d10);
    private static final Tweet tweet20 = createTweetWithDate(20, d10);
    private static final Tweet tweet30 = createTweetWithDate(30, d20);
    private static final Tweet tweet40 = createTweetWithDate(40, d20);
    private static final Tweet tweet50 = createTweetWithDate(50, d30);
    
    public static Tweet createTweetWithDate(int id, Instant d1) {
        Tweet tweet = new Tweet(id, "testUser", "dummyText", d1);
        return tweet;
    }


        /**
     * Private tests for grading the Extract class.
     */

    /**
     * Tests for Extract.getTimespan
     */
    @Test
    public void testGetTimespanEmptyListAlwaysReturnsZeroLengthTimespan() {
        /*
         * The timespan of an empty list should be zero length. We don't care
         * what time is put in there.
         */
        Timespan timespan = Extract.getTimespan(new ArrayList<Tweet>());
        if (timespan.getStart() == timespan.getEnd()) {
            return;
        }
        
        long startTime = System.nanoTime();
        long durationToRunTestNanoSeconds = 100_000_000; // 100 milliseconds
        while ((System.nanoTime()) - startTime <= durationToRunTestNanoSeconds ) {
            timespan = Extract.getTimespan(new ArrayList<Tweet>());
            if (!timespan.getStart().equals(timespan.getEnd())){
                fail();
            }
        }
    }

    @Test
    public void testGetTimespanSingleTweet() {
        /*
         * The timespan of a list containing a single tweet should also have
         * zero length. However, the timespan should be directly on the
         * timestamp of the tweet.
         */
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet10));
        assertEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
    }

    @Test
    public void testGetTimespanTwoDuplicateStamps() {
        /*
         * This test is identical to the case for a single tweet. We want the
         * resultant timespan to be the minimal covering timespan.
         */
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet10, tweet20));
        assertEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
    }

    @Test
    public void testGetTimespanTwoDifferentStamps() {
        /*
         * This test checks to make sure that when we give them two tweets, that
         * the timespan correctly handles setting the start and end.
         */
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet10, tweet30));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet30.getTimestamp(), timespan.getEnd());

        // Make sure the solution isn't order dependant.
        timespan = Extract.getTimespan(Arrays.asList(tweet30, tweet10));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet30.getTimestamp(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleStampsWithDuplicates() {
        /*
         * Testing to make sure duplicates are handled as well as more than two
         * tweets as input.
         */

        // Testing duplicates at the start of the timespan.
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet10, tweet20,
                tweet30));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet30.getTimestamp(), timespan.getEnd());

        // Testing duplicates at the end of the timespan.
        timespan = Extract.getTimespan(Arrays.asList(tweet10, tweet30, tweet40));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet30.getTimestamp(), timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleStampsWithoutDuplicates() {
        /*
         * Testing to make sure more than two inputs are handled.
         */
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet10, tweet30,
                tweet50));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet50.getTimestamp(), timespan.getEnd());

        // One last check on order.
        timespan = Extract.getTimespan(Arrays.asList(tweet30, tweet50, tweet10));
        assertNotEquals(timespan.getStart(), timespan.getEnd());
        assertEquals(tweet10.getTimestamp(), timespan.getStart());
        assertEquals(tweet50.getTimestamp(), timespan.getEnd());
    }
    
    // -------------- getMentionedUsers --------------
  
    @Test 
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
       
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void getMentionedUserBeginnigEndString() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        List<String> mentionedUsersList = new ArrayList<>(mentionedUsers);
        mentionedUsersList.sort(Comparator.naturalOrder());
        
        assertTrue(mentionedUsersList.size() == 2);
        assertTrue(mentionedUsersList.get(0).equalsIgnoreCase("user1"));
        assertTrue(mentionedUsersList.get(1).equalsIgnoreCase("user2"));

    }

    @Test
    public void getMentionedUserLengthSpecialSymbols() {
        
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4))
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        
        
        assertTrue(mentionedUsers.size() == 3);
        assertTrue(mentionedUsers.contains("user0_1-2"));
        assertTrue(mentionedUsers.contains("user89_-"));
        assertTrue(mentionedUsers.contains("_"));
        
    }
    
    @Test
    public void getMentionedUserBeginnigNotLegalSymbols() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        List<String> mentionedUsersList = new ArrayList<>(mentionedUsers);
        mentionedUsersList.sort(Comparator.naturalOrder());
        
        assertTrue(mentionedUsersList.size() == 4);
        assertTrue(mentionedUsersList.get(0).equalsIgnoreCase("mit2"));
        assertTrue(mentionedUsersList.get(1).equalsIgnoreCase("us"));
        assertTrue(mentionedUsersList.get(2).equalsIgnoreCase("user1"));
        assertTrue(mentionedUsersList.get(3).equalsIgnoreCase("user2"));

    }
    
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
