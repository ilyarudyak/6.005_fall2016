package trader;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by ilyarudyak on 11/4/16.
 */
public class PuttingIntoPractice {

    public static void main(String ...args){

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        Stream<Trader> traders = Arrays.asList(raoul, mario, alan, brian).stream();

        Stream<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        ).stream();


        // Query 1: Find all transactions from year 2011 and sort them by value (small to high).
//        transactions.filter(t -> t.getYear() == 2011)
//                .sorted(Comparator.comparing(Transaction::getValue).reversed())
//                .forEach(System.out::println);

        // Query 2: What are all the unique cities where the traders work?
//        System.out.println(traders.map(Trader::getCity)
//                .collect(Collectors.toSet()));
//        System.out.println(transactions.map(Transaction::getTrader)
//                .map(Trader::getCity)
//                .distinct()
//                .collect(Collectors.toList()));

        // Query 3: Find all traders from Cambridge and sort them by name.
        Comparator<Trader> byName = Comparator.comparing(Trader::getName);
//        System.out.println(traders.filter(t -> t.getCity().equals("Cambridge"))
//                .sorted(byName)
//                .collect(Collectors.toList()));

        // Query 4: Return a string of all traders’ names sorted alphabetically.
//        System.out.println(transactions.map(Transaction::getTrader)
//                .distinct()
//                .sorted(byName)
//                .map(Trader::getName)
//                .collect(Collectors.joining(", ")));

        // Query 5: Are there any trader based in Milan?
//        System.out.println(transactions.map(Transaction::getTrader)
//                .anyMatch(t -> t.getCity().equals("Milan")));

        // Query 6: Print all transactions’ values from the traders living in Cambridge
//        System.out.println(transactions.filter(t -> t.getTrader().getCity().equals("Cambridge"))
//                .map(Transaction::getValue)
//                .collect(Collectors.toList()));

        // Query 7: What’s the highest value of all the transactions.
//        System.out.println(transactions.mapToInt(Transaction::getValue)
//                .max()
//                .getAsInt());

//        System.out.println(transactions.map(Transaction::getValue)
//                .reduce(Integer::max));

//        System.out.println(transactions.map(Transaction::getValue)
//                .max(Integer::compare));

        // Query 8: Find the transaction with the smallest value
        Comparator<Transaction> byValue = Comparator.comparing(Transaction::getValue);
        System.out.println(transactions.min(byValue));
    }
}





















