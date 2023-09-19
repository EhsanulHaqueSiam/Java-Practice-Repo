public class ThreadMethodsExample2 {
    public static void main(String[] args) {
        // Create two threads
        Thread thread1 = new MyThread("Thread 1");
        Thread thread2 = new MyThread("Thread 2");

        // Start both threads
        thread1.start();
        thread2.start();

        try {
            // Main thread waits for thread1 to finish using join()
            thread1.join();

            // Main thread waits for thread2 to finish using join() with a timeout
            thread2.join(2000);

            // Interrupt thread2
            thread2.interrupt();

            // Yield control to other threads
            Thread.yield();

            // Get the current thread and display its name
            Thread currentThread = Thread.currentThread();
            System.out.println("Current Thread: " + currentThread.getName());

            // Get the number of active threads
            int activeThreadCount = Thread.activeCount();
            System.out.println("Active Thread Count: " + activeThreadCount);

            // Dump stack traces of all active threads
            Thread[] allThreads = new Thread[activeThreadCount];
            Thread.enumerate(allThreads);
            for (Thread thread : allThreads) {
                if (thread != null) {
                    System.out.println("Thread Name: " + thread.getName());
                    StackTraceElement[] stackTrace = thread.getStackTrace();
                    for (StackTraceElement element : stackTrace) {
                        System.out.println("\t" + element.toString());
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is running.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " is interrupted.");
        }
    }
}
