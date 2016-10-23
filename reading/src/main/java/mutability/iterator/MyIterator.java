package mutability.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilyarudyak on 10/23/16.
 */
public class MyIterator {

    private final ArrayList<String> list;
    private int index;

    public MyIterator(ArrayList<String> list) {
        this.list = list;
        this.index = 0;
    }

    public boolean hasNext() {
        System.out.println("list=" + list + " list.size=" + list.size() + " index=" + index);
        return index < list.size();
    }

    /**
     * Get the next element of the list.
     * Requires: hasNext() returns true.
     * Modifies: this iterator to advance it to the element
     *           following the returned element.
     * @return next element of the list
     */
    public String next() {
        final String element = list.get(index);
        ++index;
        return element;
    }

    public static void main(String[] args) {

        ArrayList<String> subjects = new ArrayList(Arrays.asList("6.045", "6.005", "6.813"));
        MyIterator iter = new MyIterator(subjects);
        while (iter.hasNext()) {
            String subject = iter.next();
            System.out.println("subject=" + subject);
            if (subject.startsWith("6.")) {
                subjects.remove(subject);
            }
        }
        System.out.println(subjects);
    }
}















