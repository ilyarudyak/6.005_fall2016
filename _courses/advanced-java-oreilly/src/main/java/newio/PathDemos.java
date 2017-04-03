package newio;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemos {

    public static void mainMethods() {
        // Using Paths.get(...) to create a Path
        Path data = Paths.get("/", "src", "main", "resources", "newio");
        System.out.println(data);

        Path home = Paths.get("/Users/ilyarudyak");
        System.out.println(home);

        // Using resolve to find nested paths
        Path docs = home.resolve("Documents");
        System.out.println(docs);

        // Can resolve siblings as well
        Path downloads = docs.resolveSibling("Downloads");
        System.out.println(downloads);

        Path relative = downloads.relativize(docs.resolve("IMG_0004.PNG"));
        System.out.println(relative);
    }

    public static void normalize() {
        // Normalize a path
        Path p = Paths.get("/Users/ilyarudyak/Downloads/../Documents/IMG_0004.PNG").normalize();
        System.out.println("Normalized: " + p);
    }

    public static void otherMethods() {

        // Project directory
        Path project = Paths.get(".");
        System.out.println(project);
        System.out.println(project.toAbsolutePath());
        System.out.println("As a URI: " + project.toUri());

        System.out.println("--------------------------");
        System.out.println("parent: " + project.toAbsolutePath().getParent());
        System.out.println("file name: " + project.toAbsolutePath().getFileName());
        System.out.println("root: " + project.toAbsolutePath().getRoot());

        System.out.println("--------------------------");
        for (Path path : project.toAbsolutePath()) {
            System.out.println(path);
        }

        System.out.println("--------------------------");
        File localDir = new File("..");
        System.out.println(localDir);
        System.out.println(localDir.toPath().toAbsolutePath().normalize());
    }

    public static void main(String[] args) {
//        mainMethods();
//        normalize();
        otherMethods();
    }
}






















