package com.insightfullogic.java8.exercises.chapter5;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.exercises.Exercises;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    public static final Map<Integer, Integer> fibMap = new HashMap<>();

    {
        fibMap.put(0, 0);
        fibMap.put(1, 1);
    }

    public Fibonacci() {

    }

    public Integer fibonacci(Integer n) {

        return  fibMap.computeIfAbsent(n - 1, this::fibonacci) +
                fibMap.computeIfAbsent(n - 2, this::fibonacci);
    }

    public Integer fibonacci2(Integer n) {

        return fibMap.computeIfAbsent(n, x -> fibonacci2(x - 1) + fibonacci2(x - 2));
    }

    public static void main(String[] args) {

        System.out.println(new Fibonacci().fibonacci2(4));
    }

}
