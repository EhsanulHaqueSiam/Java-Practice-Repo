public class ThreadExample {
    public static void main(String[] args) {
        // Create two threads using the Thread class
        Thread thread1 = new MyThread("Thread 1");
        Thread thread2 = new MyThread("Thread 2");

        // Create two threads using the Runnable interface
        Runnable myRunnable1 = new MyRunnable("Runnable 1");
        Runnable myRunnable2 = new MyRunnable("Runnable 2");
        
        Thread thread3 = new Thread(myRunnable1);
        Thread thread4 = new Thread(myRunnable2);

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}

// Custom thread class that extends Thread
class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - Count: " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Custom Runnable class
class MyRunnable implements Runnable {
    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - Count: " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
