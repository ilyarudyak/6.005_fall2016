package stream.create;

import java.math.BigInteger;
import java.util.stream.Stream;

import static utils.Utils.head;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class InfiniteStreams {

    public static void main(String[] args) {

//        Stream<String> echos = Stream.generate(() -> "Echo");
//        head(echos);

//        Stream<Double> randoms = Stream.generate(Math::random);
//        head(randoms);

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
        head(integers, 1000);
    }
}
