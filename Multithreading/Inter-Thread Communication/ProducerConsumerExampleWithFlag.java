public class ProducerConsumerExampleWithFlag {
    public static void main(String[] args) {
        ItemContainer itemContainer = new ItemContainer();

        Thread producerThread = new Thread(new Producer(itemContainer), "Producer");
        Thread consumerThread = new Thread(new Consumer(itemContainer), "Consumer");

        producerThread.start();
        consumerThread.start();
    }
}

class ItemContainer {
    private int item;
    private boolean itemProduced;

    public synchronized void produce(int newItem) throws InterruptedException {
        while (itemProduced) {
            wait(); // Wait for the consumer to consume the previous item
        }

        item = newItem;
        System.out.println(Thread.currentThread().getName() + " produced: " + item);
        itemProduced = true;
        notify(); // Notify the consumer that an item is available
    }

    public synchronized int consume() throws InterruptedException {
        while (!itemProduced) {
            wait(); // Wait for the producer to produce an item
        }

        System.out.println(Thread.currentThread().getName() + " consumed: " + item);
        itemProduced = false;
        notify(); // Notify the producer that the item has been consumed
        return item;
    }
}

class Producer implements Runnable {
    private ItemContainer itemContainer;

    public Producer(ItemContainer itemContainer) {
        this.itemContainer = itemContainer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                itemContainer.produce(i);
                Thread.sleep(1000); // Simulate some production time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private ItemContainer itemContainer;

    public Consumer(ItemContainer itemContainer) {
        this.itemContainer = itemContainer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int item = itemContainer.consume();
                Thread.sleep(1500); // Simulate some consumption time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
