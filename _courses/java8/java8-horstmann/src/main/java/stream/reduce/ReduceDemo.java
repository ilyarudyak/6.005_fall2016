package stream.reduce;

import utils.Utils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class ReduceDemo {

    private static final Stream<Integer> digits = Stream.of( 1, 2, 3, 4, 5 );

    public static void sum(Stream<Integer> stream) {
//        Optional<Integer> sum = stream.reduce((x, y) -> x + y);
        Optional<Integer> sum = stream.reduce(Integer::sum);
        System.out.println("sum:" + sum);
    }

    public static void sum2(Stream<Integer> stream) {
        Integer sum = stream.reduce(0, (x, y) -> x + y);
        System.out.println("sum:" + sum);
    }

    public static void countTotalWordLen(int limit) {
        Stream<String> words = Utils.readAliceToWordsStream().limit(limit);
        int totalLen = words.reduce(0,
                (acc, w) -> acc + w.length(),
                (s1, s2) -> s1 + s2);
        System.out.println("total len=" + totalLen);
    }

    public static void main(String[] args) {

//        sum(Stream.empty());
//        sum(digits);

//        sum2(digits);
//        sum2(Stream.empty());

        countTotalWordLen(100);
    }

}
















