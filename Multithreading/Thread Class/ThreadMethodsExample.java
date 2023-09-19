public class ThreadMethodsExample {
    public static void main(String[] args) {
        // Create a new thread
        Thread myThread = new Thread(new MyRunnable(), "MyThread");
        
        // Get and display thread properties
        long threadId = myThread.getId();
        String threadName = myThread.getName();
        int threadPriority = myThread.getPriority();
        Thread.State threadState = myThread.getState();
        ThreadGroup threadGroup = myThread.getThreadGroup();
        boolean isDaemon = myThread.isDaemon();
        
        System.out.println("Thread ID: " + threadId);
        System.out.println("Thread Name: " + threadName);
        System.out.println("Thread Priority: " + threadPriority);
        System.out.println("Thread State: " + threadState);
        System.out.println("Thread Group: " + threadGroup.getName());
        System.out.println("Is Daemon: " + isDaemon);
        
        // Set thread properties
        myThread.setPriority(Thread.MAX_PRIORITY);
        myThread.setDaemon(true);
        myThread.setName("UpdatedThreadName");
        
        // Check thread properties after setting
        threadName = myThread.getName();
        threadPriority = myThread.getPriority();
        isDaemon = myThread.isDaemon();
        
        System.out.println("Updated Thread Name: " + threadName);
        System.out.println("Updated Thread Priority: " + threadPriority);
        System.out.println("Updated Is Daemon: " + isDaemon);
        
        // Start the thread
        myThread.start();
        
        // Check if the thread is alive, interrupted
        boolean isAlive = myThread.isAlive();
        boolean isInterrupted = myThread.isInterrupted();
        
        System.out.println("Is Thread Alive: " + isAlive);
        System.out.println("Is Thread Interrupted: " + isInterrupted);
    }
}

class MyRunnable implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted.");
        }
        System.out.println("Thread finished its task.");
    }
}
