public class MultithreadingWithSynchronization {
    public static void main(String[] args) {
        // Creating a shared resource
        SharedResource sharedResource = new SharedResource();

        // Creating multiple threads that access the shared resource
        Thread thread1 = new Thread(new MyRunnable(sharedResource, "Thread 1"));
        Thread thread2 = new Thread(new MyRunnable(sharedResource, "Thread 2"));

        // Start the threads
        thread1.start();
        thread2.start();
    }
}

class SharedResource {
    private int sharedValue = 0;

    // Synchronized method to increment the shared value
    public synchronized void increment() {
        for (int i = 0; i < 5; i++) {
            sharedValue++;
            System.out.println(Thread.currentThread().getName() + " - Incremented: " + sharedValue);
        }
    }
}

class MyRunnable implements Runnable {
    private SharedResource sharedResource;
    private String threadName;

    public MyRunnable(SharedResource sharedResource, String threadName) {
        this.sharedResource = sharedResource;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println(threadName + " started.");

        // Access the shared resource in a synchronized manner
        sharedResource.increment();

        System.out.println(threadName + " finished.");
    }
}
