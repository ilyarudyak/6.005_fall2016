package stream.map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ilyarudyak on 11/4/16.
 */
public class CollectToMap2 {

    private static final Stream<City> cities = readCities("src/main/resources/cities.txt");

    static class City {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", state='" + state + '\'' +
                    ", population=" + population +
                    '}';
        }
    }

    public static Stream<City> readCities(String filename)  {
        try {
            return Files.lines(Paths.get(filename))
                    .map(l -> l.split(", "))
                    .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    public static void main(String[] args) {

//        cities.limit(3).forEach(System.out::println);

        // sum of populations per state
//        System.out.println(cities.collect(Collectors
//                .groupingBy(City::getState,
//                            Collectors.summingInt(City::getPopulation))).get("NY"));

        // largest city per state
//        Comparator<City> compareByPopulation = Comparator.comparing(City::getPopulation);
//        System.out.println(cities.collect(Collectors
//                .groupingBy(City::getState,
//                            Collectors.maxBy(compareByPopulation)))
//                .get("TX").get());

        // set of cities per state
//        System.out.println(cities.collect(Collectors
//                .groupingBy(City::getState,
//                            Collectors.mapping(City::getName, Collectors.toSet())))
//                .get("TX"));

        // list of cities, separated by comma
        System.out.println(cities.collect(Collectors
                .groupingBy(City::getState,
                        Collectors.mapping(City::getName, Collectors.joining(", "))))
                .get("TX"));
    }
}





























