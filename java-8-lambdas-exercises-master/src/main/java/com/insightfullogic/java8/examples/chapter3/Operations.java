package com.insightfullogic.java8.examples.chapter3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 10/9/16.
 */
public class Operations {

    public static void main(String[] args) {

        List<String> collectedList = Stream.of("a", "b", "c").collect(toList());
        System.out.println(collectedList);

        List<String> upperCaseList = Stream.of("a", "b", "c")
                .map(string -> string.toUpperCase())
                .collect(toList());
        System.out.println(upperCaseList);

    }
}
