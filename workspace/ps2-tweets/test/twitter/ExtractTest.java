package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
  
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
       
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void getMentionedUserBeginnigEndString() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertEquals(new HashSet<String>(Arrays.asList("user1", "user2")), mentionedUsers);
    }

    @Test
    public void getMentionedUserLengthSpecialSymbols() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        
        assertEquals(new HashSet<String>(Arrays.asList("user89_-", "user0_1-2", "_")), mentionedUsers);
    }
    
    @Test
    public void getMentionedUserBeginnigNotLegalSymbols() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertEquals(new HashSet<String>(Arrays.asList("us", "user1", "user2", "mit2")), mentionedUsers);
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
