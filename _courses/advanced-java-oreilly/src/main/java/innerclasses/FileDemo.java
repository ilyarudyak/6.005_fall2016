package innerclasses;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDemo {

    private static String pathStr = "src/main/java/innerclasses";

    public static void oldWay() {
        File dir = new File(pathStr);
        for (String name : dir.list()) {
            System.out.println(name);
        }

        System.out.println("\nOnly Java files:");
        for (String name : dir.list(new JavaFilter())) {
            System.out.println(name);
        }

        System.out.println("\nUsing anonymous inner class for text files:");
        for (String name : dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        })) {
            System.out.println(name);
        }
    }

    public static void newWay() throws IOException {
        Files.list(Paths.get(pathStr))
                .forEach(path -> System.out.println(path.getFileName()));

        System.out.println("\nonly java files...");
        Files.list(Paths.get(pathStr))
                .filter(path -> path.getFileName().toString().endsWith(".java"))
                .forEach(path -> System.out.println(path.getFileName()));
    }

    public static void main(String[] args) throws IOException {
//        oldWay();
        newWay();
    }
}



















