## Part 1. Interfaces, Inheritance and Object
### Abstract classes and methods
* if we want default constructor in the child class we have 2 options: (a) define 
default constructors in **both** classes; we **can not** define it in the child class 
but not in the parent class, we implicitly call `super()` with default constructor
of the parent class; (this is probably the usual approach) (b) we can define 
it in terms of other constructors in the child class;
```java
public abstract class Employee {
    public static final String DEFAULT_NAME = "UNKNOWN";

    public Employee() {
        this(DEFAULT_NAME);
    }
}

public class Salaried extends Employee {
    public static final double DEFAULT_SALARY = 120000;

    // we can define define default constructor here only if 
    // we have default constructor in the parent class
    public Salaried() {
        
    }

    // alternatively we can define default constructor in
    // terms of other constructor
    public Salaried() {
        this(DEFAULT_NAME, DEFAULT_SALARY);
    }
}
```
* we may use **factory** pattern to instantiate a class; in this case we create an instance
of the child **concrete** class; we may check what class we instantiated with 
`getClass().getName()`:
```java
public class CurrencyPrinter {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        System.out.println(nf.getClass().getName());
    }
}
```
### Static and default methods in Interfaces
* there were no `static` and `non-abstract` methods prior to java 8; `static` methods 
were introduced to eliminate classes like `Collections` - now we can see `static` methods 
for example in `Stream` interface;
```java
class Collections {
    
    // Sorts the specified list into ascending order, 
    // according to the natural ordering of its elements.
    static <T extends Comparable<? super T>> void sort(List<T> list);
}

interface Stream<T> {
    
    // Returns an empty sequential Stream
    static <T> Stream<T> empty(); 
    
}
```
* we may now add `default` method to an interface like `forEach()` in `Iterable`;  
```java
interface Iterable<T> {
    
    // Performs the given action for each element of the Iterable
    // until all elements have been processed or the action throws an exception.
    default void forEach(Consumer<? super T> action);

}
```
* why are they necessary? (a) again we may eliminate such classes as `AbstractCollection` 
that contains some implementations of methods for classes that implements `Collection` 
interface; (b) this is necessary to be able to expand an interface - we now have `default`
method `stream()` in `Collection`:
```java
interface Collection<E> {
    
    // Returns a sequential Stream with this collection as its source.
    default Stream<E> stream();
}
```
* example of conflicts (which seems to be a common case for multiple inheritance): 
`Company` and `Employee` interfaces have `default` method `getName`; if `CompanyEmployee`
implements both of these interfaces it must override this method;
```java
public class CompanyEmployee implements Company, Employee {

    @Override
    public String getName() {
        return String.format("%s working for %s",
                Employee.super.getName(), Company.super.getName());
    }
}
```
### Overriding `equals()` and `hashCode()`
* we have to override them both - otherwise we'll broke code for hashing; we may generate 
this code with IDE (based on solution in Bloch's book); 
* to override `equals()` we have to: (a) compare pointers to these objects; (b) check if 
they are `instanceof` class (this includes superclasses; if we need to include only current class -
we check `getClass()` - this is controversial topic); (c) compare chosen attributes (its easy to do 
this with `Objects.equals()` than to write all cases explicitly like in `Task.java`); 
```java
class Task {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;
        
        // Returns true if the arguments are equal to each other and false otherwise. 
        // Consequently, if both arguments are null, true is returned and if exactly 
        // one argument is null, false is returned. Otherwise, equality is determined 
        // by using the equals method of the first argument.
        return Objects.equals(id, other.id);
    }
}
```
* to override `hashCode()` we may again use helper method from `Objects` class rather than
deal with Bloch's implementation: `return Objects.hash(id, ...)`.

### Exceptions
* Here is an example of how to close stream prior to Java 7. The only requirement to use 
`try-with-resources` - class must implement `Autocloseable` interface: 
```java
public class Arithmetic {

    public static void finallyExample() {
        Path dir = Paths.get("src", "main", "java", "exceptions");
        BufferedReader br = null; // (1) we have declare it outside and assign null
        try {
            br = Files.newBufferedReader(dir.resolve("Arithmetic.java"));
            System.out.println("1st line:" + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // (2) we have to close it
            if (br != null) { // (3) check for null
                try {
                    br.close(); // (4) it also throws an exception 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 }
```
* `finally` will be called in any case: (a) if we catch exception; (b) if we don't have
any exception or (c) if we even don't have `catch` block;
```java
public class Arithmetic {
    public static void finallyExample2() {
        try {
//            z = x / y;
        }
//        catch (ArithmeticException e) {
//            e.printStackTrace();
//        }
        finally {
            // works with catch or without
            // works if no exception
            System.out.println("this will work in any case");
        }
    }
}
 ```
* to create our own exception we have to: (a) extend `Extention` (for checked exceptions) or
 `RuntimeExceptions` (for unchecked exceptions); (b) create constructor to provide a message;
```java
public class MyException extends Exception {
    public MyException() {
        this("default message");
    }

    public MyException(String message) {
        super(message);
    }
}
```  
## Part 2. Generics
### Static methods
* to use static method with generic types we have to declare this type in the method
signature:
```java
public class Pair<T> {
    private T first;
    private T second;
    
    // here we declare type T in method signature
    public static <T> void swap(Pair<T> pair) {
        T temp = pair.first;
        pair.first = pair.second;
        pair.second = temp;
    }
}
```
### Wildcards
* we can not use `List<Salaried>` in the place where `List<Employee>` is required even if 
`Salaried` is a subclass of `Employee` - this is the reason why we have to use wildcards;
```java
public class HR {
    public static void printEmpNames(List<Employee> employees) {
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }
}

public class HRDemo {
    public static void main(String[] args) {
        HR.printEmpNames(employees);
        // HR.printEmpNames(salarieds); // doesn't compile
    }
}
```
* we have to use `<? extends Employee>` if we want the method to be applicable to subclasses
(but now we can'y add anything to `employees`):
```java
public class HR {

    public static void printEmpAndSubclassNames(List<? extends Employee> employees) {
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        // employees.add(new Employee("Fred")); // does not compile
        // employees.add(new Salaried("Barney")); // does not compile
    }
}
```
* it turns out that we have to use `super` in other situations (PECS --> produces uses extends, 
consumes uses super):
```java
public class HR {

    // PECS --> produces uses extends, consumes uses super
    public static void printAllFiltered(
            List<? extends Employee> employees, Predicate<? super Employee> predicate) {
        for (Employee e : employees) {
            if (predicate.test(e)) {
                System.out.println(e.getName());
            }
        }
    }
}
```
* we also have to use `<T extends Repairable>`, we can not just use the name of the interface:
```java
public class RepairShop {

    public static <T extends Repairable> void fixAll(List<T> items) {
        items.forEach(T::fix);
    }
}
```
* we can use bounds in static methods as well (in our example we use `Repairables`), but
more common example - `Comaparable`:
```java
public class RepairShop {

    public static <T extends Repairable> void fixAll(List<T> items) {
        items.forEach(T::fix); // if we don't extends Repairable we can't guarantee that we have `fix` method
    }
}
```
## Part 8. Inner classes
### Static and Anonymous Inner
* to sort we may use `sorted()` method on `stream`; we even can provide a comparator:
```java
public class StringSorter {
    public List<String> naturalSortWithStreams() {
        return strings.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    
    public List<String> lengthReverseSortWithStreams() {
        return strings.stream()
                .sorted((s1, s2) -> Integer.compare(s2.length(), s1.length()))
                .collect(Collectors.toList());
    }
}
```
## Part 8. Lambdas and streams
### Streams
* in `Collectors` we have analog of python `join` method - `Collectors.joining(" ")`; we also can 
get a sum (in a different way than in `IntStream`):
 ```java
 public class StreamsDemo {
     public String joinStream() {
         return Stream.of("this", "is", "a", "stream", "of", "strings")
                 .collect(Collectors.joining(" "));
     }
     
     public int getTotalLength() {
         return strings.stream()
                 .collect(Collectors.summingInt(String::length));
     }
 }
 ```
* we can produce sum directly using `reduce`; it will produce an `Optional` and we than use
`orElse` method on this `Optional`:
```java
 public class StreamsDemo {
    public Double sumRandoms1(int num) {
        return Stream.generate(Math::random)
                .limit(num)
                .reduce(Double::sum)
                .orElse(0.0);
    }
    
    // full form of reduce
    public Double sumRandoms2(int num) {
        return Stream.generate(Math::random)
                .limit(num)
                .reduce((acc, n) -> {
                    System.out.printf("Acc=%s, n=%s%n", acc, n);
                    return acc + n;
                })
                .orElse(0.0);
    }
 }
```
* if we want to convert stream into array we may use `toArray()` method:
```java
public class UsePerson {
    public Person[] createPersonArray() {
        return names.stream()
                .map(Person::new)
                .toArray(Person[]::new);
    }
}
```
* to sort we use the following approaches: (a) it's better to use `sorted()` on
the stream instead of `Collections.sort` - the former doesn't modify list in place;
(b) in some cases we may use methods of comparator instead of lambdas (IDE can 
suggest replacement); to get reversed order we chain `reversed()`:
```java
 public class SortingDemo {
        // Sort by length then alpha using sorted
        public List<String> lengthSortThenAlphaSortUsingSorted() {
            return sampleStrings.stream()
                    .sorted( Comparator.comparing(String::length)
                    // we may even chain some methods of Comparator
                        .thenComparing(Comparator.naturalOrder()) )
                    .collect(Collectors.toList());
        }
 }
```
* to find `max` we may use `findFirst()` method on stream; to deal with `Optional`
we use `orElse()`, `orElseGet()` etc.:
```java
public class ProcessDictionary {
    public static void main(String[] args) throws Exception {

        Optional<String> max = Files.lines(
                Paths.get("/", "usr", "share", "dict", "web2"))
                .filter(s -> s.length() > 10)
                .map(String::toLowerCase)
                .sorted(Comparator.comparing(String::length).reversed())
                .findFirst();
        
        System.out.println();
        System.out.println(max.isPresent() ? max.get() : max.orElse("None found"));
        System.out.println(max.orElseGet(() -> "nothing"));
        // most useful method
        System.out.println(max.orElse("nothing"));
    }
}
```
### New Java date/time 
* `Instant` is like `Date` - moment in time from `Epoch`; this is **immutable**
class (like all classes in `java.time` package); we may compare two `Instant`, use 
with `Duration` to measure time and increment/decrement it;
```java
public class JavaTimeDemos {

    public static void instantDemo() {
        Instant now = Instant.now();
        System.out.format("Instant.now():%s:%d%n", now, now.getEpochSecond());
        Instant then = now.plus(1, ChronoUnit.DAYS);
        Duration elapsed = Duration.between(now, then);
    }
}
```
* we have `LocalDate` class and can create it with `now()` or using static 
method `of`; we can measure duration between local dates; we also have time zones 
and formatter;






