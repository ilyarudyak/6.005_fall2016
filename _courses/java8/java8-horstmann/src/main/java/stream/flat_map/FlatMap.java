package stream.flat_map;

import java.util.stream.Stream;

import static utils.Utils.head;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class FlatMap {

    public static Stream<Character> characterStream(String s) {
        return s.chars().mapToObj(i -> (char) i);
    }

    public static void main(String[] args) {

        Stream<String> words = Stream.of("your", "boat");
//        String boat = "boat";

//        boat.chars().forEach(System.out::println);
//        head(characterStream(boat));

//        Stream<Stream<Character>> result = words.map(w -> characterStream(w));
//        result.forEach(stream -> head(stream));

        head(words.flatMap(w -> characterStream(w)));
    }
}
