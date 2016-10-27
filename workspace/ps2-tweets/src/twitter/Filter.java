package twitter;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.time.Instant;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        return tweets.stream()
                .filter(tweet -> tweet.getAuthor().equals(username))
                .collect(toList());
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        return tweets.stream()
                .filter(tweet -> {
                    Instant start = timespan.getStart();
                    Instant end = timespan.getEnd();
                    Instant timestamp = tweet.getTimestamp();
                    return  (timestamp.equals(start) || 
                             timestamp.equals(end) ||
                            (tweet.getTimestamp().isAfter(start) &&
                            tweet.getTimestamp().isBefore(end))); 
                })
                .collect(toList());
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        return tweets.stream()
                .filter(tweet -> isContainSomeWord(tweet.getText(), words))
                .collect(toList());
    }
    
    private static boolean isContainSomeWord(String s, List<String> words) {
        
        String sLowerCase = s.toLowerCase();
        
//        Set<String> wordsContaining = words.stream()
//                .filter(word -> sLowerCase.contains(word.toLowerCase()))
//                .collect(toSet());
        
        
        for (String word : words) {
            if (sLowerCase.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
    
    public static void main(String[] args) {
        
        Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
        Instant d2 = Instant.parse("2016-02-17T10:00:00Z");
        System.out.println(d2.equals(d1));
    }
    
    
    
    
    
    
    
    
    
    
}
