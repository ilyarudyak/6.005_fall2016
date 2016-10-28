package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * Testing strategy:
     * 
     *  - all users that are authors are in the map (even if they don't follow anybody)
     *  - check that set contains all mentions (not necessarily from authors list, when only one mention)
     *  - we don't check situation when set is empty - it's not properly defined
     *  - check that user doesn't follow himself
     *  - correct set for users with many tweets and mentions
     *  
     */
    
    
    private static final Tweet t1 = new Tweet(1, "user1", "tweet1 @user2 @user3", Instant.now());
    private static final Tweet t2 = new Tweet(2, "user1", "tweet2 @user1 @user2 @user4", Instant.now());
    
    private static final Tweet t3 = new Tweet(3, "user2", "tweet3 @user2 @user6", Instant.now());
    
    private static final Tweet t4 = new Tweet(4, "user3", "tweet4", Instant.now());
    
    private static final Tweet t5 = new Tweet(5, "user4", "tweet5 @user1 @user3 @user4", Instant.now());
    private static final Tweet t6 = new Tweet(6, "user4", "tweet6 @user2", Instant.now());
    private static final Tweet t7 = new Tweet(7, "user4", "tweet7 @user2 @user4 @user7", Instant.now());
    
    private static final List<Tweet> tweets = Arrays.asList(t1, t2, t3, t4, t5, t6, t7);
    
    
    /* ----------- test of influencers() ----------- */
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphCheckUsers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected 4 users", followsGraph.keySet()
                .containsAll(Arrays.asList("user1", "user2", "user3", "user4")));
    }
    
    @Test
    public void testGuessFollowsGraphUserContainsAllMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected 3 users", followsGraph.get("user1")
                .containsAll(Arrays.asList("user2", "user3", "user4")));
    }
    
    @Test
    public void testGuessFollowsGraphNotContainHimself() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertFalse("not expect use1", followsGraph.get("user1")
                .contains("user1"));
    }
    
    @Test
    public void testGuessFollowsGraphWhenOnlyOneMention() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertEquals("expect size 1", 1, followsGraph.get("user2").size());
        assertTrue("expect size 1", followsGraph.get("user2").contains("user6"));
    }
    
    @Test
    public void testGuessFollowsGraphManyTweetsAndUsers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected 4 users", followsGraph.get("user4")
                .containsAll(Arrays.asList("user1", "user2", "user3", "user7")));
    }
    
    
    
    /* ----------- test of influencers() ----------- */
    
    
    
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
}

































