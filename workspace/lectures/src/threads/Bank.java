package threads;

import java.util.ArrayList;
import java.util.List;

public class Bank {
 // all the cash machines share a single bank account
    private static int balance = 0;
    
    public static void deposit() {
        balance = balance + 1;
    }
    public static void withdraw() {
        balance = balance - 1;
    }
    
    // simulate a network of cash machines, handling customer transactions
    // concurrently
    private static final int CASH_MACHINES = 2;
    private static final int TRANSACTIONS_PER_MACHINE = 10000;
    
    // each ATM does a bunch of transactions that
    // modify balance, but leave it unchanged afterward
    private static void cashMachine() {
        for (int i = 0; i < TRANSACTIONS_PER_MACHINE; i = i + 1) {
            deposit();
            withdraw();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting balance is $" + balance);
        
        System.out.println("...then "
                            + CASH_MACHINES
                            + " cash machines do "
                            + TRANSACTIONS_PER_MACHINE
                            + " $1-deposit/$1-withdrawal transactions each...");
        
        // simulate each cash machine with a thread
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < CASH_MACHINES; i = i + 1) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    // give the other threads a chance to start to,
                    // so it's a fair race
                    Thread.yield();
                    // do the transactions for this cash machine
                    cashMachine();
                }
            });
            t.start();  // don't forget to start the thread!
            threads.add(t);
        }
        
        // wait for all the threads to finish
        for (Thread t : threads) {
            t.join();
        }
        
        System.out.println("final account balance: $" + balance);
    }

}
