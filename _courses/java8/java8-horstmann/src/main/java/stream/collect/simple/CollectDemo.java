package stream.collect.simple;

import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/7/16.
 */
public class CollectDemo {

    public static Stream<String> words = Utils.readAliceToWordsStream();

    public static void main(String[] args) {

//        Set<String> wordSet = words.collect(Collectors.toSet());
//        System.out.println(wordSet.getClass().getCanonicalName());

//        Set<String> wordSet2 = words.collect(Collectors.toCollection(TreeSet::new));
//        System.out.println(wordSet2.getClass().getCanonicalName());

//        String[] wordArray = words.toArray(String[]::new);
//        System.out.println(Arrays.toString(wordArray).substring(0, 100));


        IntSummaryStatistics summary = words.collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);
    }
}
