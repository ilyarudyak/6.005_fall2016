
    - Method Reference:                      Method Reference.java
    - Element Ordering: n/a
    - Enter the Collector
        * Into Other Collections             CollectorExamples.java
        * To Values                          the same
        * Partitioning the Data              the same
        * Grouping the Data                  the same
        * Strings                            StringExamples.java
        * Composing Collectors               CollectorExamples.java
        * Refactoring and Custom Collectors: StringCollector.java,
                                             StringCombiner.java,
                                             StringExamples.java
        * Reduction as a Collector           StringExamples.java
    - Collection Niceties: Niceties.java


    - This is such a common idiom that there’s actually an abbreviated syntax for
    this that lets you reuse an existing method, called a method reference.
    - When you create a Stream from a collection with a defined order, the Stream
    has a defined encounter order. If there’s no defined order to begin, the Stream
    produced by that source doesn't have a defined order. NEW!
    - But when you’re calling toList or toSet, you don’t get to specify the concrete
    implementation of the List or Set. Under the hood, the streams library is
    picking an appropriate implementation for you. It might be the case that you wish
    to collect your values into a Collection of a specific type if you require that
    type later.
    - It’s also possible to collect into a single value using a collector. There are
    maxBy and minBy collectors that let you obtain a single value according to some
    ordering. (Not recommended by Horstmann).
    - Another common operation that you might want to do with a Stream is partition
    it into two collections of values.
    - There’s a natural way to generalize partitioning through altering the grouping
    operation. It’s more general in the sense that instead of splitting up your data
    into true and false groups, you can use whatever values you want.
    - A very common reason for collecting streams of data is to generate strings at
    the end. Let’s suppose that we want to put together a formatted list of names
    of the artists involved in an album. Here, we use a map to extract the artists’
    names and then collect the Stream using Collectors.joining.






















