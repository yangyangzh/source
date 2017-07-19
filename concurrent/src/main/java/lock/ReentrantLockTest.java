package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/7/19.
 */
public class ReentrantLockTest implements Runnable {

    public static void main(String[] args) {


        final ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 3; i++) {
            String name = String.valueOf(i);
            ReentrantLockTest t = new ReentrantLockTest(name, lock);
            Thread thread = new Thread(t);
            thread.start();
        }

    }

    public ReentrantLockTest(String name, ReentrantLock lock) {
        this.name = name;
        this.lock = lock;
        done = lock.newCondition();
    }

    private String name;

    private ReentrantLock lock;

    private Condition done;


    public void write() throws InterruptedException {
       // if (lock.tryLock()) {
            try {
                //lock.lock();
                done.await();
                System.out.println("I am " + name + ", I get lock !");
                System.out.println("I am " + name + ", I am writting !");
                Thread.sleep(2000);
                System.out.println("I am " + name + ", I am Done !");
                done.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
               // lock.unlock();
            }
     //   } else {
        //    System.out.println("I am " + name + " , I cannot not lock !");
       //     System.out.println("I am " + name + " , I try again !");
           // Thread.sleep(1000);
           // write();
       // }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void setLock(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            write();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
