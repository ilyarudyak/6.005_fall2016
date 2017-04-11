package threads.sync_lock.deadlock;

import java.util.HashSet;
import java.util.Set;

public class Wizard {
    
    private final String name;
    private final Set<Wizard> friends;
    // Rep invariant:
    //    name, friends != null
    //    friend links are bidirectional: 
    //        for all f in friends, f.friends contains this
    // Concurrency argument:
    //    threadsafe by monitor pattern: all accesses to rep 
    //    are guarded by this object's lock

    public Wizard(String name) {
        this.name = name;
        this.friends = new HashSet<Wizard>();
    }

    public synchronized boolean isFriendsWith(Wizard that) {
        return this.friends.contains(that);
    }

    public synchronized void friend(Wizard that) {
        if (friends.add(that)) {
            that.friend(this);
        } 
    }

    public synchronized void defriend(Wizard that) {
        if (friends.remove(that)) {
            that.defriend(this);
        } 
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("starting...");
        
        Wizard harry = new Wizard("Harry Potter");
        Wizard snape = new Wizard("Severus Snape");
        
        Thread threadA = new Thread(() -> {
            harry.friend(snape);
            snape.friend(harry);
            
        });
        
        threadA.start();
        threadA.sleep(1000);;
        
        new Thread(() -> {
            harry.defriend(snape);
            snape.defriend(harry);
        }).start();
        
        System.out.println("finished");
    }

}



















