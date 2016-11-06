package stream.optional;

import java.util.Optional;

/**
 * Created by ilyarudyak on 11/6/16.
 */
public class OptionalDemo2 {

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    public static void main(String[] args) {

        System.out.println(inverse(4.0).flatMap(OptionalDemo2::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalDemo2::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalDemo2::squareRoot));

        System.out.println();
        System.out.println(Optional.of(4.0).flatMap(OptionalDemo2::inverse).flatMap(OptionalDemo2::squareRoot));
        System.out.println(Optional.of(-1.0).flatMap(OptionalDemo2::inverse).flatMap(OptionalDemo2::squareRoot));
        System.out.println(Optional.of(0.0).flatMap(OptionalDemo2::inverse).flatMap(OptionalDemo2::squareRoot));
    }
}
