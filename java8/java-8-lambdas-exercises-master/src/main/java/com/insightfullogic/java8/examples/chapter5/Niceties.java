package com.insightfullogic.java8.examples.chapter5;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Niceties {

    abstract static class ArtistService {

        protected Map<String, Artist> artistCache = new HashMap<>();

        public abstract Artist getArtist(String name);

        protected Artist readArtistFromDB(String name) {
            fromDB();
            return new Artist(name, "UK");
        }

        protected void fromDB() {
            System.out.println("read from d/b ...");
        }

        protected void fromCache() {
            System.out.println("read from cache ...");
        }
    }

    static class OldArtistService extends ArtistService {
        // BEGIN ARTIST_CACHE_OLD
        public Artist getArtist(String name) {
            Artist artist = artistCache.get(name);
            if (artist == null) {
                artist = readArtistFromDB(name);
                artistCache.put(name, artist);
            } else {
                fromCache();
            }
            return artist;
        }
        // END ARTIST_CACHE_OLD
    }

    static class Java8ArtistService extends ArtistService {

        // BEGIN ARTIST_CACHE_COMPUTE
        public Artist getArtist(String name) {

            if (artistCache.get(name) != null) {
                fromCache();
            }
            return artistCache
                    .computeIfAbsent(name, this::readArtistFromDB);
        }
        // END ARTIST_CACHE_COMPUTE
    }


    static class ImperativeCount {

        public Map<Artist, Integer> countAlbums(Map<Artist, List<Album>> albumsByArtist) {
            // BEGIN COUNT_ALBUMS_VALUES_UGLY
            Map<Artist, Integer> countOfAlbums = new HashMap<>();
            for (Map.Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
                Artist artist = entry.getKey();
                List<Album> albums = entry.getValue();
                countOfAlbums.put(artist, albums.size());
            }
            // END COUNT_ALBUMS_VALUES_UGLY
            return countOfAlbums;
        }
    }

    static class Java8Count {
        public Map<Artist, Integer> countAlbums(Map<Artist, List<Album>> albumsByArtist) {
            // BEGIN COUNT_ALBUMS_VALUES_FOREACH
            Map<Artist, Integer> countOfAlbums = new HashMap<>();
            albumsByArtist.forEach((artist, albums) -> countOfAlbums.put(artist, albums.size()));
            // END COUNT_ALBUMS_VALUES_FOREACH
            return countOfAlbums;
        }
    }

    public static void main(String[] args) {

//        OldArtistService oas = new OldArtistService();
//        oas.getArtist("Madonna");
//        oas.getArtist("Lady Gaga");
//        oas.getArtist("Madonna");
//
//        System.out.println();
//        Java8ArtistService j8as = new Java8ArtistService();
//        j8as.getArtist("Madonna");
//        j8as.getArtist("Lady Gaga");
//        j8as.getArtist("Madonna");

        ImperativeCount ic = new ImperativeCount();
        Map<Artist, List<Album>> albumsByArtist = new HashMap<>();
        albumsByArtist.put(SampleData.johnColtrane, SampleData.albums.collect(Collectors.toList()));
        System.out.println(ic.countAlbums(albumsByArtist));

        Java8Count j8c = new Java8Count();
        System.out.println();
        System.out.println(j8c.countAlbums(albumsByArtist));
    }

}
















