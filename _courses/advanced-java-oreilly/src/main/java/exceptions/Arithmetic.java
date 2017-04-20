package exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Arithmetic {

    private static int x = 3;
    private static int y = 0;
    private static double z = 0;

    public static void printException() {
        try {
            z = x / y;
        } catch (ArithmeticException e) {
            // (1) different ways to print stack trace
            e.printStackTrace();
            System.err.println("toString(): " + e);
            System.err.println("getMessage(): " + e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(System.err::println);
        }
    }

    public static void multipleCatchBlock() {
        try {
            Arithmetic e = Arithmetic.class.newInstance();
            System.out.println(e.toString());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

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

    public static void tryWithResources() {

        Path dir = Paths.get("src", "main", "java", "exceptions");
        try (BufferedReader br = Files.newBufferedReader(dir.resolve("Arithmetic.java"))) {
            System.out.println("1st line:" + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static void myException() {
        try {
//            throw new MyException();
            throw new MyException("this is something NEW, something FRESH!!!");
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        printException();
//        multipleCatchBlock();
//        finallyExample();
//        tryWithResources();
//        finallyExample2();
        myException();
    }
}
