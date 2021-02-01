// Jacob Vesche
// Programming Problem 2
// Semaphores (Readers and Writers Problem)
//
//"I have neither given nor received unauthorized aid in completing this work, 
// nor have I presented someone else's work as my own."





import java.util.concurrent.Semaphore;

class ProgrammingProblem2 {

    static Semaphore readLock = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    volatile static int readCount = 0;

    static class Read implements Runnable {
        @Override
        public void run() {
            try {
                //Acquire Section
                readLock.acquire();
                synchronized(ProgrammingProblem2.class) {
                    readCount++;
                }
                if (readCount == 1) {
                    writeLock.acquire();
                }
                readLock.release();

                //Reading section
                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
                Thread.sleep(1500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");

                //Releasing section
                readLock.acquire();
                synchronized(ProgrammingProblem2.class) {
                    readCount--;
                }
                if(readCount == 0) {
                    writeLock.release();
                }
                readLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();
                System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
                writeLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();
        
        Thread t6 = new Thread(write);
        t6.setName("thread6");
        
        Thread t1 = new Thread(read);
        t1.setName("thread1");
        
        Thread t2 = new Thread(read);
        t2.setName("thread2");
        
        Thread t3 = new Thread(write);
        t3.setName("thread3");
        
        Thread t4 = new Thread(read);
        t4.setName("thread4");
        
        Thread t5 = new Thread(write);
        t5.setName("thread5");
        
        
        t6.start();
        t1.start();
        t3.start();
        t2.start();
        t4.start();
        t5.start();
    }
}