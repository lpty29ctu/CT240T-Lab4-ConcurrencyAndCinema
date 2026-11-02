package main.java.com.ctu.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Order {
    private final int orderId;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}

class OrderProducer implements Runnable {
    private final BlockingQueue<Order> queue;

    public OrderProducer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                Order order = new Order(i);
                queue.put(order); // Tự động chờ nếu hàng đợi bị đầy
                System.out.println("📦 [Producer] Đã tạo đơn hàng #" + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class OrderConsumer implements Runnable {
    private final BlockingQueue<Order> queue;

    public OrderConsumer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = queue.take(); // Tự động chờ nếu hàng đợi rỗng
                System.out.println("✅ [Consumer] " + Thread.currentThread().getName() + " đã xử lý xong đơn hàng #" + order.getOrderId());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> queue = new ArrayBlockingQueue<>(3);

        Thread producerThread = new Thread(new OrderProducer(queue));
        Thread consumerThread1 = new Thread(new OrderConsumer(queue), "Worker-1");
        Thread consumerThread2 = new Thread(new OrderConsumer(queue), "Worker-2");

        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();

        producerThread.join();
        Thread.sleep(2000);

        consumerThread1.interrupt();
        consumerThread2.interrupt();
    }
}