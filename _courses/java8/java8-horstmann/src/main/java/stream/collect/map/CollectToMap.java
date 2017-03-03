package stream.collect.map;

import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by ilyarudyak on 11/4/16.
 */
public class CollectToMap {

    static class Person {
        private int id;
        private String name;

        public Person(int id, String name) { this.id = id; this.name = name; }
        public int getId() { return id; }
        public String getName() { return name; }
        public String toString() { return getClass().getSimpleName() +
                "[id=" + id + ",name=" + name + "]";
        }
    }

    public static Stream<Person> people() {
        return Stream.of(
                new Person(1001, "Peter"),
                new Person(1002, "Paul"),
                new Person(1003, "Mary"));
    }

    public static void main(String[] args) {

//        // id:name
//        System.out.println(people()
//                // we can also use toMap(p -> p.getId(), p -> p.getName())
//                .stream.collect(toMap(Person::getId, Person::getName)));
//
//        // id:person
//        Map<Integer, Person> peopleMap = people()
//                // again we can use p -> p
//                .stream.collect(toMap(Person::getId, Function.identity()));
//        System.out.println(peopleMap);
//        System.out.println(peopleMap.get(1001));

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());

        // language:set-of-countries
//        locales
//            .map(locale -> locale.getDisplayLanguage(locale))
//            .forEach(System.out::println);

        // country:list of locales
//        Map<String, List<Locale>> countryToLocales = locales
//                .stream.collect(groupingBy(Locale::getCountry));
//        System.out.println(countryToLocales.get("CH"));

        // country:*set* of locales
//        System.out.println(locales.stream.collect(groupingBy(Locale::getCountry,
//                // downstream collector
//                toSet())).get("CH"));

        // country:set of languages
        System.out.println(locales.collect(groupingBy(Locale::getCountry,
                // downstream collector - we transform locales into set of languages
                mapping(Locale::getDisplayLanguage, toSet())
        )).get("CH"));


    }

}

































