package builtin_interface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;

/**
 * Created by ilyarudyak on 10/11/16.
 */
public class ComparatorApp {

    private static void printList(List<String> list, String message) {
        System.out.println();
        System.out.println(message);
        System.out.println(list);
    }

    public static void main(String args[]){

        List<String> strings = asList("AAA", "bbb", "CCC", "ddd", "EEE");

        //Simple case-sensitive sort operation
        Collections.sort(strings);
        printList(strings, "Simple sort");

        // case-insensitive sort with an anonymous class
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareToIgnoreCase(str2);
            }
        });
        printList(strings, "Sort with comparator - anonymous class");

        // case-insensitive sort with lambda
        Collections.sort(strings, (str1, str2) -> str1.compareToIgnoreCase(str2));
        printList(strings, "Sort with comparator - lambda");

        // case-insensitive sort with method reference
        Collections.sort(strings, String::compareToIgnoreCase);
        printList(strings, "Sort with comparator - method reference");

        System.out.println();
        strings.forEach( s -> System.out.println(s));
        System.out.println();
        strings.forEach(System.out::println);
    }
}













