package com.insightfullogic.java8.exercises.chapter5;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.exercises.Exercises;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Find the artist with the longest name. You should implement this using a
 * Collector and the reduce higher-order function from Chapter 3.
 * */
public class LongestName {

    private static Comparator<Artist> byNameLength = comparing(artist -> artist.getName().length());

    public static Optional<Artist> byReduce(Stream<Artist> artists) {

        return artists.reduce(
                (acc, artist) ->  byNameLength.compare(acc, artist) >= 0 ? acc : artist
        );
    }

    public static Optional<Artist> byMax(Stream<Artist> artists) {
        Function<Artist,Integer> getNameSize = artist -> artist.getName().length();
        return artists.max(byNameLength);
    }

    public static Optional<Artist> byCollecting(Stream<Artist> artists) {
        return artists.collect(Collectors.maxBy(byNameLength));
    }

    public static void main(String[] args) {

        Stream<Artist> names = Stream.of(
                SampleData.johnLennon,
                SampleData.paulMcCartney,
                SampleData.georgeHarrison,
                SampleData.ringoStarr,
                SampleData.peteBest,
                SampleData.stuartSutcliffe);

        System.out.println(byReduce(names));

    }

}


























