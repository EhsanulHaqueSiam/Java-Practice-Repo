public class ThreadConstructorExample {
    public static void main(String[] args) {
        // Using the default constructor
        Thread thread1 = new MyThread();
        
        // Using the constructor with a Runnable and a name
        Runnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable, "Thread 2");
        
        // Using the constructor with a Runnable (no name)
        Thread thread3 = new Thread(myRunnable);
        
        // Using the constructor with a ThreadGroup and a name
        ThreadGroup threadGroup = new ThreadGroup("MyThreadGroup");
        Thread thread4 = new Thread(threadGroup, new MyRunnable(), "Thread 4");
        
        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}

// Custom thread class with a default constructor
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread with default constructor is running.");
    }
}

// Custom Runnable class
class MyRunnable implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running.");
    }
}
