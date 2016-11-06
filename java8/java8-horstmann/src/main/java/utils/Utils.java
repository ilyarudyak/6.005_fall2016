package utils;

import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class Utils {

    public static final int LIMIT = 10;

    public static <T> void head(Stream<T> stream) {
        stream.limit(LIMIT).forEach(e -> System.out.print(e + " "));
        System.out.println();
    }

    public static <T> void head(Stream<T> stream, int limit) {
        stream.limit(limit).forEach(e -> System.out.print(e + " "));
        System.out.println();
    }
}
