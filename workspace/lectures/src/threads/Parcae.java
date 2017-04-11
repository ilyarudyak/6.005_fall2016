package threads;

public class Parcae {
    public static void main(String[] args) {
        Thread nona = new Thread(new Runnable() {
            public void run() { System.out.println("spinning"); };
        });
        nona.run(); // bug! called run instead of start
        Runnable decima = new Runnable() {
            public void run() { System.out.println("measuring"); };
        };
        decima.run(); // bug? maybe meant to create a Thread?
        // ...
    }

}
