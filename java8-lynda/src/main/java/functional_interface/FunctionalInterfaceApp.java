package functional_interface;

/**
 * Created by ilyarudyak on 10/11/16.
 */
public class FunctionalInterfaceApp {

    public static void main(String[] args) {
        FunctionalInterfaceWithoutArguments fi =
                () -> System.out.println("i'm a functional interface without arguments");
        fi.doSomething();
    }
}
