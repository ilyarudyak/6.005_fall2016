package com.insightfullogic.java8.examples.chapter6;

import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class DiceRolls {

    private static final int N_SMALL = 1_000_000;
    private static final int N_LARGE = 10_000_000;

    private static final int N = N_LARGE;

    public enum StreamType { SERIAL, PARALLEL }

    private void time(StreamType type) {

        Instant start = Instant.now();

        switch (type) {
            case SERIAL:
                serialDiceRolls();
                break;
            case PARALLEL:
                parallelDiceRolls();
                break;
            default:
                throw new IllegalArgumentException();
        }

        Instant end = Instant.now();
        System.out.println(type + ":" + Duration.between(start, end).toMillis());
    }

    // BEGIN serial
    public Map<Integer, Double> serialDiceRolls() {
        double fraction = 1.0 / N * 36;
        return IntStream.range(0, N)
                .mapToObj(twoDiceThrows())
                .collect(groupingBy(side -> side, summingDouble(n -> fraction)));
    }
    // END serial

    // BEGIN parallel
    public Map<Integer, Double> parallelDiceRolls() {
        double fraction = 1.0 / N;
        return IntStream.range(0, N)
                .parallel()
                .mapToObj(twoDiceThrows())
                .collect(groupingBy(side -> side,
                        summingDouble(n -> fraction)));
    }
    // END parallel

    private static IntFunction<Integer> twoDiceThrows() {
        return i -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int firstThrow = random.nextInt(1, 7);
            int secondThrow = random.nextInt(1, 7);
            return firstThrow + secondThrow;
        };
    }

    public static void main(String[] ignore) throws IOException, RunnerException {

//        new DiceRolls().time(StreamType.SERIAL);
//        new DiceRolls().time(StreamType.PARALLEL);

        System.out.println(IntStream.range(0, N_LARGE)
                .parallel()
                .mapToObj(twoDiceThrows())
                .collect(groupingBy(side -> side, Collectors.counting())));
    }

}













