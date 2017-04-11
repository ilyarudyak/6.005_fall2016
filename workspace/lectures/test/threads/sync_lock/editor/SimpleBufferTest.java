package threads.sync_lock.editor;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SimpleBufferTest {

    @Test
    public void testInsert() {
        EditBuffer buf = new SimpleBuffer();
        assertEquals("", buf.toString());
        assertEquals(0, buf.length());

        buf.insert(0, "a"); // empty.insert(start, one-char)
        assertEquals("a", buf.toString());
        assertEquals(1, buf.length());

        buf.insert(1, "bcd"); // one-char.insert(end, many-chars)
        assertEquals("abcd", buf.toString());
        assertEquals(4, buf.length());

        buf.insert(2, ""); // many-chars.insert(middle, empty)
        assertEquals("abcd", buf.toString());
        assertEquals(4, buf.length());
    }

    private static final int N = 10;

    @Test
    public void testManyInserts() {
        EditBuffer buf = new SimpleBuffer();
        doManyInserts(buf, "a", N);
//        System.out.println(buf.toString());
        doManyInserts(buf, "b", N);
//        System.out.println(buf.toString());
        assertEquals(N, count(buf.toString(), "a"));
        assertEquals(N, count(buf.toString(), "b"));
    }

    /* insert s n times at random locations in buf. */
    private static void doManyInserts(EditBuffer buf, String s, int n) {
        for (int i = 0; i < n; ++i) {
            int pos = (int) (Math.random() * buf.length());
            buf.insert(pos, s);
        }
    }

    /*
     * @return number of times needle occurs in haystack (including overlapping
     * matches).
     */
    private static int count(String haystack, String needle) {
        int n = 0;
        for (int i = haystack.indexOf(needle); i != -1; i = haystack.indexOf(needle, i + 1)) {
            ++n;
        }
        return n;
    }

    @Test
    public void testManyInsertsMultithreaded() {
        EditBuffer buf = new SimpleBuffer();
        Set<Thread> threads = new HashSet<Thread>();
        threads.add(startInserterThread(buf, "a", N));
        threads.add(startInserterThread(buf, "b", N));
        waitForAll(threads);
        
        assertEquals(N, count(buf.toString(), "a"));
        assertEquals(N, count(buf.toString(), "b"));
    }

    /*
     * @return a started thread making n randomly-positioned inserts of s into
     * buf.
     */
    private static Thread startInserterThread(final EditBuffer buf, final String s, final int n) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Thread.yield();
                doManyInserts(buf, s, n);
            }
        });
        thread.start();
        return thread;
    }

    /* wait for all threads in set to complete. */
    private static void waitForAll(Set<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new AssertionError("don't interrupt my testing");
            }
        }
    }

    @Test
    public void testManyInsertsMultithreaded2() throws InterruptedException {
        EditBuffer buf = new SimpleBuffer();
        
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> doManyInserts(buf, "a", N));
        executor.execute(() -> doManyInserts(buf, "b", N));
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        
        assertEquals(N, count(buf.toString(), "a"));
        assertEquals(N, count(buf.toString(), "b"));
    }
}

























