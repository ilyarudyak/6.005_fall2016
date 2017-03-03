## Part 1. Interfaces, Inheritance and Object
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


















