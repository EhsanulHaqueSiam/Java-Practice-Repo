public class MonitorExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(sharedResource));
        Thread consumerThread = new Thread(new Consumer(sharedResource));

        // Start the threads
        producerThread.start();
        consumerThread.start();
    }
}

class SharedResource {
    private int value;
    private boolean produced;

    // Producer method
    public synchronized void produce(int newValue) {
        while (produced) {
            try {
                wait(); // Wait for the consumer to consume
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        value = newValue;
        System.out.println("Produced: " + value);
        produced = true;
        notify(); // Notify the consumer that a value is ready
    }

    // Consumer method
    public synchronized int consume() {
        while (!produced) {
            try {
                wait(); // Wait for the producer to produce
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Consumed: " + value);
        produced = false;
        notify(); // Notify the producer that the value has been consumed
        return value;
    }
}

class Producer implements Runnable {
    private SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.produce(i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int value = sharedResource.consume();
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
