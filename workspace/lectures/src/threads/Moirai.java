package threads;

public class Moirai {
    
    public static void main(String[] args) {
        Thread clotho = new Thread(new Runnable() {
       
            public void run() { 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("spinning"); 
            };
        });
        

        
        clotho.start();
        
        
        new Thread(new Runnable() {
            public void run() { System.out.println("measuring"); };
        }).start();
        new Thread(new Runnable() {
            public void run() { System.out.println("cutting"); };
        });
        // bug! never started
    }

}
