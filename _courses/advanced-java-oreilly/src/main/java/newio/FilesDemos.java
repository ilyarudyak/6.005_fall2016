package newio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FilesDemos {

    public static void workWithFiles() throws IOException {
        // Create a directory
        Files.createDirectory(Paths.get("data"));

        // Create a directory with intermediate directories
        Files.createDirectories(Paths.get("sub1", "sub2", "sub3"));

        // Add an empty file to that directory
        Files.createFile(Paths.get("sub1", "sub2", "sub3", "myfile.txt"));

        // Delete them all
        boolean deleted = Files.deleteIfExists(Paths.get("sub1", "sub2", "sub3", "myfile.txt"));
        deleted = Files.deleteIfExists(Paths.get("data"));
        deleted = Files.deleteIfExists(Paths.get("sub1", "sub2", "sub3"));
        deleted = Files.deleteIfExists(Paths.get("sub1", "sub2"));
        deleted = Files.deleteIfExists(Paths.get("sub1"));

        // Copy the file to a new location
        Path sourceDir = Paths.get("src", "main", "java", "newio");
        Path dataFile = sourceDir.resolve("data.txt");
        Path destination = sourceDir.resolve("data_copy.txt");
        Files.copy(dataFile, destination);

        // Move the file
        Path other = sourceDir.resolve("data_moved.txt");
        Files.move(destination, other);

        // Delete the copy
        Files.deleteIfExists(other);
    }

    public static void readFiles() throws IOException {

        // Using stream
        Path fileDir = Paths.get("src", "main", "resources", "newio");
        Path filePath = fileDir.resolve("data.txt");
        List<String> lines = Files.lines(filePath).collect(Collectors.toList());
        System.out.println(lines);

        // Using readAllLines
        List<String> lines2 = Files.readAllLines(filePath);
        System.out.println(lines2);

        // Using readAllBytes
        String dataStr = new String(Files.readAllBytes(filePath), UTF_8);
        System.out.println(dataStr);
    }

    public static void walkDir() throws IOException {
        // Visit all the files in the source folder
        Path javaDir = Paths.get("src", "main", "java");
        try (Stream<Path> entries = Files.walk(javaDir)) {
            entries.forEach(System.out::println);
        }
    }

    public static void main(String[] args) throws IOException {
        readFiles();
    }
}
