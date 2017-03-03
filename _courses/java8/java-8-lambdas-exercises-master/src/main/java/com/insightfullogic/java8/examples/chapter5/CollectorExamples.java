package com.insightfullogic.java8.examples.chapter5;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Comparator.comparing;
import static java.util.Map.Entry;
import static java.util.stream.Collectors.*;

public class CollectorExamples {

    // ----- Enter the Collector: Into Other Collections

    public Set<Integer> toCollectionTreeset() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        // BEGIN TO_COLLECTION_TREESET
        return stream.collect(toCollection(TreeSet::new));
        // END TO_COLLECTION_TREESET
    }

    public Set<Integer> toDefaultSet() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        return stream.collect(toSet());
    }

    // ------ Enter the Collector: To Values

    // BEGIN BIGGEST_GROUP
    public Optional<Artist> biggestGroup(Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.collect(maxBy(comparing(getCount)));
    }
    // END BIGGEST_GROUP

    public Optional<Artist> biggestGroup2(Stream<Artist> artists) {
        Function<Artist, Long> getCount = artist -> artist.getMembers().count();
        return artists.max(comparing(getCount));
    }

    // ------ Enter the Collector: Partitioning the Data

    // BEGIN BANDS_AND_SOLO
    public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
        return artists.collect(partitioningBy(artist -> artist.isSolo()));
    }
    // END BANDS_AND_SOLO

    // BEGIN BANDS_AND_SOLO_REF
    public Map<Boolean, List<Artist>> bandsAndSoloRef(Stream<Artist> artists) {
        return artists.collect(partitioningBy(Artist::isSolo));
    }
    // END BANDS_AND_SOLO_REF

    // ------ Enter the Collector: Grouping the Data

    // BEGIN ALBUMS_BY_ARTIST
    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician));
    }
    // END ALBUMS_BY_ARTIST

    // -------------------------------------------------------------------------

    // ------ Enter the Collector: Composing Collectors

    public Map<Artist, Integer> numberOfAlbumsDumb(Stream<Album> albums) {
        // BEGIN NUMBER_OF_ALBUMS_DUMB
        Map<Artist, List<Album>> albumsByArtist
                = albums.collect(groupingBy(album -> album.getMainMusician()));

        Map<Artist, Integer> numberOfAlbums = new HashMap<>();
        for (Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            numberOfAlbums.put(entry.getKey(), entry.getValue().size());
        }
        // END NUMBER_OF_ALBUMS_DUMB
        return numberOfAlbums;
    }

    // BEGIN NUMBER_OF_ALBUMS
    public Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(album -> album.getMainMusician(),
                counting()));
    }
    // END NUMBER_OF_ALBUMS

    public Map<Artist, Long> numberOfAlbums2(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician, counting()));
    }

    // ------

    // BEGIN NAME_OF_ALBUMS_DUMB
    public Map<Artist, List<String>> nameOfAlbumsDumb(Stream<Album> albums) {
        Map<Artist, List<Album>> albumsByArtist =
                albums.collect(groupingBy(album -> album.getMainMusician()));

        Map<Artist, List<String>> nameOfAlbums = new HashMap<>();
        for (Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
            nameOfAlbums.put(entry.getKey(), entry.getValue()
                    .stream()
                    .map(Album::getName)
                    .collect(toList()));
        }
        return nameOfAlbums;
    }
    // END NAME_OF_ALBUMS_DUMB

    // BEGIN NAME_OF_ALBUMS
    public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician,
                mapping(Album::getName, toList())));
    }
    // END NAME_OF_ALBUMS

    public Map<Artist, List<String>> nameOfAlbums2(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician,
                mapping(Album::getName, toList())
        ));
    }

    // ------

    public static Map<String, Long> countWords(Stream<String> words) {
        return words.collect(groupingBy(word -> word, counting()));
    }

    private static final Pattern SPACES = Pattern.compile("\\w+");

    public static Map<String, Long> countWordsIn(Path path) throws IOException {
        Stream<String> words = Files.readAllLines(path, defaultCharset())
                .stream()
                .flatMap(line -> SPACES.splitAsStream(line));

        return countWords(words);
    }

    // BEGIN averagingTracks
    public double averageNumberOfTracks(List<Album> albums) {
        return albums.stream()
                .collect(averagingInt(album -> album.getTrackList().size()));
    }
    // END averagingTracks

    public static void main(String[] args) {

//        Set<Integer> treeSet = new CollectorExamples().toCollectionTreeset();
//        System.out.println(treeSet.getClass().getName());
//
//        Set<Integer> defaultSet = new CollectorExamples().toDefaultSet();
//        System.out.println(defaultSet.getClass().getName());

//        System.out.println(new CollectorExamples()
//                .biggestGroup(SampleData.threeArtists()));
//
//        System.out.println(new CollectorExamples()
//                .biggestGroup2(SampleData.threeArtists()));

//        System.out.println(new CollectorExamples().bandsAndSolo(SampleData.threeArtists()));

//        System.out.println(new CollectorExamples()
//                .albumsByArtist(SampleData.albumsWithWhite));

        System.out.println(new CollectorExamples()
                .nameOfAlbums2(SampleData.albumsWithWhite));


    }

}

















