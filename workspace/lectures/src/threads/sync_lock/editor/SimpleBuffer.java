package threads.sync_lock.editor;

/**
 * SimpleBuffer is a threadsafe EditBuffer with a simple rep.
 */
public class SimpleBuffer implements EditBuffer {

    private String text;
    // Rep invariant: 
    //   text, lock != null
    // Abstraction function: 
    //   represents the sequence text[0],...,text[text.length()-1]
    // Concurrency argument:
    //   TODO

    private void checkRep() {
        assert text != null;
    }

    /** Make a SimpleBuffer. */
    public SimpleBuffer() {
        text = "";
        checkRep();
    }

    /** @see EditBuffer#insert */
    public synchronized void insert(int pos, String s) {
        text = text.substring(0, pos) + s + text.substring(pos);
        checkRep();
    }

    /** @see EditBuffer#delete */
    public synchronized void delete(int pos, int len) {
        text = text.substring(0, pos) + text.substring(pos+len);
        checkRep();
    }

    /** @see EditBuffer#length */
    public synchronized int length() {
        return text.length();
    }

    /** @see EditBuffer#toString */
    public synchronized String toString() {
        return text;
    }
}
