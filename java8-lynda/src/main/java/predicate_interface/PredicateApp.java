package predicate_interface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 10/11/16.
 */
public class PredicateApp {

    public static void main(String args[]){

        List<Person> people = new ArrayList<>();

        people.add(new Person("Joe", 48));
        people.add(new Person("Mary", 30));
        people.add(new Person("Mike", 73));

        Predicate<Person> predicate = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() >= 65;
            }
        };

        List<Person> oldPeople = people.stream()
                                        .filter(person -> person.getAge() >= 65)
                                        .collect(toList());
        System.out.println(oldPeople);

    }
}
