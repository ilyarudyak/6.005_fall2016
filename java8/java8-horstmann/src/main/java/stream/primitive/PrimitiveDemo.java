package stream.primitive;

import utils.Utils;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static utils.Utils.head;

/**
 * Created by ilyarudyak on 11/7/16.
 */
public class PrimitiveDemo {

    public static final String RED = "red";
    public static final String FRED = "fred";

    public static void main(String[] args) {

        // we have IntStream.of() method rather than Stream.of()
        // we may also use Arrays.stream()
//        IntStream is0 = IntStream.of(1, 1, 2, 3, 5);
//        head(is0);

        // we can use generate and iterate to build infinite streams
//        IntStream is1 = IntStream.generate(() -> (int)(Math.random() * 100));
//        head(is1);

        // we also have range() - upper bound is excluded
        // these methods specific for IntStream
//        IntStream is2 = IntStream.rangeClosed(5, 10);
//        head(is2);

//        Stream<Integer> is3 = IntStream.range(0, 100).boxed();
//        head(is3);

//        Stream<String> words = Utils.readAliceToWordsStream();
//        IntStream lengths = words.peek(System.out::println).mapToInt(String::length);
//        head(lengths);

        // new method in Random class
//        Random random = new Random();
//        IntStream is4 = random.ints(0, 100);
//        head(is4);
        // we can convert it into array of primitives
//        System.out.println(Arrays.toString(is4.limit(10).toArray()));
        // we have new methods like sum(), average(), max(), min() etc.
//        System.out.println(is4.limit(10).sum());


        OptionalInt lengthRed = Utils.readAliceToWordsStream()
                .filter(s -> s.contains("fred"))
                .mapToInt(String::length)
                .findFirst();
        System.out.println(lengthRed);
    }
}













