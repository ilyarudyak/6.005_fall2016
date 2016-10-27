package twitter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        
        Instant start = tweets.stream()
                               .min(Comparator.comparing(tweet -> tweet.getTimestamp()))
                               .get()
                               .getTimestamp();
        
        Instant end = tweets.stream()
                            .max(Comparator.comparing(tweet -> tweet.getTimestamp()))
                            .get()
                            .getTimestamp();
        
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        
        Set<String> mentions = new HashSet<>();
        
        // we use '+' because length(username) > 0
        // \w is predefined class for [a-zA-Z_0-9]
        Pattern up = Pattern.compile("(?:[^\\w-]|^)@([\\w-]+)");
        
        for (Tweet tweet : tweets) {
            Matcher um = up.matcher(tweet.getText());
            while(um.find()) {
                mentions.add(um.group(1).toLowerCase());
            }
        }

        return mentions;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
    
    public static void main(String[] args) {
        
        Tweet t1 = new Tweet(1, "", "abcd @user1 def @user2 ghjk", Instant.now());
        Tweet t2 = new Tweet(2, "", "@user3 def @user4", Instant.now());
        Tweet t3 = new Tweet(3, "", "abc@user5 def abc.@user6.edu", Instant.now());

        System.out.println(getMentionedUsers(Arrays.asList(t1, t2, t3)));
        

    }
}




















