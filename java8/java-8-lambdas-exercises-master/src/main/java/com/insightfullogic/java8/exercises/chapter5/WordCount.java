package com.insightfullogic.java8.exercises.chapter5;

import com.insightfullogic.java8.exercises.Exercises;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordCount {

    public static Map<String, Long> countWords(Stream<String> names) {
        return Exercises.replaceThisWithSolution();
    }

    public static void main(String[] args) {

        Stream<String> names = Stream.of("John", "Paul", "George", "John",
                "Paul", "John");

        Map<String, Long> namesMap = names.collect(
                Collectors.groupingBy(Function.identity(), Collectors.counting())
        );

        System.out.println(namesMap);

    }

}
