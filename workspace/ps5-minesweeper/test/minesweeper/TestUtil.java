package minesweeper;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import minesweeper.server.MinesweeperServer;

public class TestUtil {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 4000 + new Random().nextInt(1 << 15);
    private static final String PORT_STRING = Integer.toString(PORT);

    private static final int MAX_CONNECTION_ATTEMPTS = 20;
    
    private static final String BOARDS_DIR = "test/resources/boards/";

    static ThreadWithObituary startServer() {
        return startServer("--port", PORT_STRING);
    }

    static ThreadWithObituary startServer(boolean debug) {
        return startServer(debug ? "--debug" : "--no-debug", "--port", PORT_STRING);
    }

    static ThreadWithObituary startServer(boolean debug, int size) {
        return startServer(debug ? "--debug" : "--no-debug", "--port", PORT_STRING, "--size", Integer.toString(size) + "," + Integer.toString(size));
    }

    static ThreadWithObituary startServer(boolean debug, int sizeX, int sizeY) {
        return startServer(debug ? "--debug" : "--no-debug", "--port", PORT_STRING, "--size", Integer.toString(sizeX) + "," + Integer.toString(sizeY));
    }
    
    /**
     * Start a MinesweeperServer with the given debug mode and board file.
     */
    static ThreadWithObituary startServer(boolean debug, String boardFileStr) throws IOException {
        File boardFile = new File(BOARDS_DIR + boardFileStr);
        return startServer(debug ? "--debug" : "--no-debug", "--port", PORT_STRING, 
                "--file", boardFile.getAbsolutePath().toString());
    }

    /**
     * Start a MinesweeperServer with the given command-line arguments.
     */
    static ThreadWithObituary startServer(final String... args) {
        return new ThreadWithObituary(() -> MinesweeperServer.main(args));
    }
    
    /**
     * Connect to a MinesweeperServer and return the connected socket.
     * @param server if not null, abort connection attempts if that thread dies
     * @see autograder.PublishedTest#connectToMinesweeperServer(Thread)
     */
    static Socket connect(ThreadWithObituary server) throws IOException {
        int attempts = 0;
        while (true) {
            try {
                Socket socket = new Socket(LOCALHOST, PORT);
                socket.setSoTimeout(3000);
                return socket;
            } catch (ConnectException ce) {
                if (server != null && ! server.thread().isAlive()) {
                    throw new IOException("Server thread not running", server.error());
                }
                if (++attempts > MAX_CONNECTION_ATTEMPTS) {
                    throw new IOException("Exceeded max connection attempts", ce);
                }
                try { Thread.sleep(attempts * 10); } catch (InterruptedException ie) { }
            }
        }
    }
    
    static String nosp(String s) {
        return s == null ? s : s.replaceAll("\\s+", "");
    }
    
    static String regsp(String s) {
        return s == null ? s : s.replaceAll("\\s+", " ");
    }
}

/** A thread and possibly the error that terminated it. */
class ThreadWithObituary {
    
    private final Thread thread;
    private Throwable error = null;
    
    /** Create and start a new thread. */
    ThreadWithObituary(Runnable runnable) {
        thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler((thread, error) -> {
            error.printStackTrace();
            this.error = error;
        });
        thread.start();
    }
    
    /** Return the thread. */
    synchronized Thread thread() { return thread; }
    
    /** Return the error that terminated the thread, if any. */
    synchronized Throwable error() { return error; }

}
