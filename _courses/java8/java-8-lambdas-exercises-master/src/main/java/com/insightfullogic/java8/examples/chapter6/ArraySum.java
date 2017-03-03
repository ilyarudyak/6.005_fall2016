package com.insightfullogic.java8.examples.chapter6;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;


import org.openjdk.jmh.runner.RunnerException;


import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class ArraySum {

    public static final int SMALL_SIZE = 10;
    public static final int MEDIUM_SIZE = 100;
    public static final int LARGE_SIZE = 10000;

    public enum StreamType { SERIAL, PARALLEL }


    public List<Album> albums;

    public void initAlbums() {
        albums = IntStream.range(0, LARGE_SIZE)
                .mapToObj(i -> SampleData.aLoveSupreme.copy())
                .collect(toList());
    }

    // BEGIN serial
    public int serialArraySum() {
        return albums.stream()
                .flatMap(Album::getTracks)
                .mapToInt(Track::getLength)
                .sum();
    }
    // END serial

    // BEGIN parallel
    public int parallelArraySum() {
        return albums.parallelStream()
                .flatMap(Album::getTracks)
                .mapToInt(Track::getLength)
                .sum();
    }
    // END parallel

    private void time(StreamType type) {

        Instant start = Instant.now();

        switch (type) {
            case SERIAL:
                serialArraySum();
                break;
            case PARALLEL:
                parallelArraySum();
                break;
            default:
                throw new IllegalArgumentException();
        }

        Instant end = Instant.now();
        System.out.println(type + ":" + Duration.between(start, end).getNano());
    }


    public static void main(String[] ignore) throws IOException, RunnerException {

        ArraySum as = new ArraySum();
        as.initAlbums();
        as.time(StreamType.SERIAL);
        as.time(StreamType.PARALLEL);



    }

}


















