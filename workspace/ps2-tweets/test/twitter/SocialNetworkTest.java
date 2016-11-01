package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
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
     * for influencers():
     *  
     *  - check that all username are in the list: both authors and mentioned
     *  - check the correct order of sorting
     *  - 
     *  
     */
    
    
    private static final Tweet t1 = new Tweet(1, "user1", "tweet1 @user2 @user3", Instant.now());
    private static final Tweet t2 = new Tweet(2, "user1", "tweet2 @user1 @user2 @user4", Instant.now());
    
    private static final Tweet t3 = new Tweet(3, "user2", "tweet3 @user2 @user6", Instant.now());
    
    private static final Tweet t4 = new Tweet(4, "user3", "tweet4", Instant.now());
    
    private static final Tweet t5 = new Tweet(5, "user4", "tweet5 @user1 @user3 @user4", Instant.now());
    private static final Tweet t6 = new Tweet(6, "user4", "tweet6 @user2", Instant.now());
    private static final Tweet t7 = new Tweet(7, "user4", "tweet7 @user2 @user4 @user7", Instant.now());
    
    private static final Tweet t8 = new Tweet(8, "user8", "tweet8 @user2", Instant.now());
    private static final Tweet t9 = new Tweet(9, "user5", "tweet9", Instant.now());
    
    private static final List<Tweet> tweets = Arrays.asList(t1, t2, t3, t4, t5, t6, t7);
    private static final List<Tweet> tweets2 = Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    
    
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
    
    @Test
    public void testInfluencersAllIncluded() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets2);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected 8 users", followsGraph.keySet().containsAll(
                Arrays.asList("user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8")));
    }
    
    @Test 
    public void testInfluencersCorrectOrderFirst() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets2);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals("expected user2", "user2", influencers.get(0));    
    }
    
    @Test 
    public void testInfluencersCorrectOrderLast() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets2);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected user5 or user8", Arrays.asList("user5", "user8")
                .contains(influencers.get(influencers.size() - 1)));    
    }
    
    @Test 
    public void testInfluencersCorrectOrderMiddle() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets2);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        System.out.println(influencers);
        assertTrue("expected user3 before user4", influencers.indexOf("user3") < 
                influencers.indexOf("user4"));    
    }
    
    @Test 
    public void testInfluencersCorrectOrderMiddle2() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets2);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected user7 before user5", influencers.indexOf("user7") < 
                influencers.indexOf("user5"));    
    }
    
    @Test
    public void testInfluencersMustBeCaseInsensitive() {
        /*
         * Usernames should be case insensitive.
         */
        Map<String, Set<String>> network = new HashMap<String, Set<String>>();
        network.put("bbitdiddle", new HashSet<String>(Arrays.asList("a_lyssp_")));
        network.put("A_LYSSP_", new HashSet<String>());
        
        List<String> influencers = SocialNetwork.influencers(network);
        
        assertFalse("expected non-empty list", influencers.isEmpty());
        assertEquals("list size", 2, influencers.size());
        assertTrue("incorrect user at index 0", influencers.get(0).toLowerCase().equals("a_lyssp_"));
        assertTrue("incorrect user at index 1", influencers.get(1).toLowerCase().equals("bbitdiddle"));
    }
    
    
    

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
}

































