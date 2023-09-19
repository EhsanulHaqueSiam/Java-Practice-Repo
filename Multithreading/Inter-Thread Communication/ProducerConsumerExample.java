import java.util.LinkedList;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        // Create a shared buffer with a limited capacity
        Buffer buffer = new Buffer(5);

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(buffer), "Producer");
        Thread consumerThread = new Thread(new Consumer(buffer), "Consumer");

        // Start the threads
        producerThread.start();
        consumerThread.start();
    }
}

// Shared buffer for producers and consumers
class Buffer {
    private LinkedList<Integer> items;
    private int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.items = new LinkedList<>();
    }

    // Producer adds an item to the buffer
    public synchronized void produce(int item) throws InterruptedException {
        while (items.size() >= capacity) {
            // Buffer is full, wait for a consumer to consume
            wait();
        }
        items.add(item);
        System.out.println(Thread.currentThread().getName() + " produced: " + item);
        notify(); // Notify waiting consumers that an item is available
    }

    // Consumer removes an item from the buffer
    public synchronized int consume() throws InterruptedException {
        while (items.isEmpty()) {
            // Buffer is empty, wait for a producer to produce
            wait();
        }
        int item = items.remove();
        System.out.println(Thread.currentThread().getName() + " consumed: " + item);
        notify(); // Notify waiting producers that space is available
        return item;
    }
}

// Producer class
class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                buffer.produce(i);
                Thread.sleep(1000); // Simulate some production time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer class
class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int item = buffer.consume();
                Thread.sleep(1500); // Simulate some consumption time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
