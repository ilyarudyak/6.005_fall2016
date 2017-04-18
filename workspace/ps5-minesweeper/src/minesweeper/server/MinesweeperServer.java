package minesweeper.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import minesweeper.Board;
import minesweeper.Point;
import minesweeper.Square;

public class MinesweeperServer {

    private static final int DEFAULT_PORT = 4444;
    private static final int MAXIMUM_PORT = 65535;
    public static final int DEFAULT_SIZE = 10;

    private final ServerSocket serverSocket;
    private final boolean debug;
    
    private static String BOOM_MESSAGE = "BOOM!";
    private static String HELP_MESSAGE = "This is the help message!";
    private static String TERMINATE = "terminate";
    
    private Board board;
    private AtomicInteger playersNum = new AtomicInteger(0);
    private String helloMessage;


    public MinesweeperServer(int port, boolean debug) throws IOException {
        serverSocket = new ServerSocket(port);
        this.debug = debug;
        
    }

    public void serve() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            playersNum.incrementAndGet();
            buildHelloMessage();
            Runnable r = new ConnectionHadler(socket);
            Thread t = new Thread(r);
            t.start(); 
        }
    }

    private void setBoard(Board board) {
        this.board = board;
    }
    
    private void buildHelloMessage() {
        helloMessage = String.format("Welcome to Minesweeper. Players: %d including you. "
                + "Board: %d columns by %d rows. Type 'help' for help.", playersNum.intValue(), 
                board.getBoardXSize(), board.getBoardYSize());
    }

    public static void main(String[] args) {
        boolean debug = false;
        int port = DEFAULT_PORT;
        int sizeX = DEFAULT_SIZE;
        int sizeY = DEFAULT_SIZE;
        Optional<File> file = Optional.empty();

        Queue<String> arguments = new LinkedList<String>(Arrays.asList(args));
        try {
            while ( ! arguments.isEmpty()) {
                String flag = arguments.remove();
                try {
                    if (flag.equals("--debug")) {
                        debug = true;
                    } else if (flag.equals("--no-debug")) {
                        debug = false;
                    } else if (flag.equals("--port")) {
                        port = Integer.parseInt(arguments.remove());
                        if (port < 0 || port > MAXIMUM_PORT) {
                            throw new IllegalArgumentException("port " + port + " out of range");
                        }
                    } else if (flag.equals("--size")) {
                        String[] sizes = arguments.remove().split(",");
                        sizeX = Integer.parseInt(sizes[0]);
                        sizeY = Integer.parseInt(sizes[1]);
                        file = Optional.empty();
                    } else if (flag.equals("--file")) {
                        sizeX = -1;
                        sizeY = -1;
                        file = Optional.of(new File(arguments.remove()));
                        if ( ! file.get().isFile()) {
                            throw new IllegalArgumentException("file not found: \"" + file.get() + "\"");
                        }
                    } else {
                        throw new IllegalArgumentException("unknown option: \"" + flag + "\"");
                    }
                } catch (NoSuchElementException nsee) {
                    throw new IllegalArgumentException("missing argument for " + flag);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("unable to parse number for " + flag);
                }
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            System.err.println("usage: MinesweeperServer [--debug | --no-debug] [--port PORT] [--size SIZE_X,SIZE_Y | --file FILE]");
            return;
        }

        try {
            runMinesweeperServer(debug, file, sizeX, sizeY, port);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public static void runMinesweeperServer(boolean debug, Optional<File> file, 
            int sizeX, int sizeY, int port) throws IOException {
        MinesweeperServer server = new MinesweeperServer(port, debug);
        if (!file.isPresent()) {
            server.setBoard(Board.buildRandomBoard(sizeX, sizeY));
        } else {
            Board board = new Board(file.get().toString());
            server.setBoard(board);
        }
        server.serve();
    }
    
    class ConnectionHadler implements Runnable {
        
        private Socket socket;

        public ConnectionHadler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                handleConnection(socket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        private void handleConnection(Socket socket) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            try {
                out.println(helloMessage);
                for (String line = in.readLine(); line != null; line = in.readLine()) {
                    String output = handleRequest(line);
                    if (output != null) {
                        // TODO: Consider improving spec of handleRequest to avoid use of null
                        if (output.equals(TERMINATE)) {
                            playersNum.decrementAndGet();
                            break;
                        } else if (output.equals(BOOM_MESSAGE) && !debug) {
                            out.println(output);
                            playersNum.decrementAndGet();
                            break;
                        } else {
                            out.println(output);
                        }
                    }
                }
            } finally {
                out.close();
                in.close();
            }
        }

        private String handleRequest(String input) {
            String regex = "(look)|(help)|(bye)|"
                         + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
            if ( ! input.matches(regex)) {
                // invalid input
                return HELP_MESSAGE;
            }
            String[] tokens = input.split(" ");
            if (tokens[0].equals("look")) {
                // 'look' request
                return board.toString();
            } else if (tokens[0].equals("help")) {
                // 'help' request
                return HELP_MESSAGE;
            } else if (tokens[0].equals("bye")) {
                // 'bye' request
                return TERMINATE;
            } else {
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                Point point = new Point(x, y);
                Square square = board.getBoard().get(point);
                if (tokens[0].equals("dig")) {
                    // 'dig x y' request
                    return handleDig(x, y);  
                } else if (tokens[0].equals("flag")) {
                    // 'flag x y' request
                    if (board.isOnBoard(point) && square.isUntouched()) {
                        square.setFlagged(true);
                        return board.toString();
                    }
                } else if (tokens[0].equals("deflag")) {
                    // 'deflag x y' request
                    if (board.isOnBoard(point) && square.isUntouched()) {
                        square.setFlagged(false);
                        return board.toString();
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
        
        private String handleDig(int x, int y) {
            Point point = new Point(x, y);
            Square square = board.getBoard().get(point);
            if (!board.isOnBoard(point) || !square.isUntouched()) {             
                return board.toString();
            } else if (square.isUntouched()) {                                
                square.setDug();
                if (square.isContainBomb()) {                                  
                    board.removeBomb(point);
                    handleDigNeighbors(point);
                    return BOOM_MESSAGE;
                } else {
                    handleDigNeighbors(point);
                    return board.toString();                                                                                
                }         
            } else {
                return board.toString();
            } 
        }
        
        private void handleDigNeighbors(Point point) {
            if (board.getBombCount(point) == 0) {
                board.getAdjPoints(point)
                    .stream()
                    .forEach(p -> {
                        Square square = board.getBoard().get(p);
                        if (square.isUntouched()) {
                            square.setDug();
                            handleDigNeighbors(p);
                        }
                    });
            }
        }

    }

}









