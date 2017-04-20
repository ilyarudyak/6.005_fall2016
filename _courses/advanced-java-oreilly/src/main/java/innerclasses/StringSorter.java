package innerclasses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StringSorter {
    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> naturalSort() {
        Collections.sort(strings);
        return strings;
    }

    public List<String> naturalSortWithStreams() {
        return strings.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> lengthSort() {
        Collections.sort(strings, Comparator.comparingInt(String::length));
        return strings;
    }

    public List<String> lengthSortWithLambda() {
        Collections.sort(strings, Comparator.comparingInt(String::length));
        return strings;
    }

    public List<String> lengthReverseSortWithStreams() {
        return strings.stream()
                .sorted((s1, s2) -> Integer.compare(s2.length(), s1.length()))
                .collect(Collectors.toList());
    }
}
