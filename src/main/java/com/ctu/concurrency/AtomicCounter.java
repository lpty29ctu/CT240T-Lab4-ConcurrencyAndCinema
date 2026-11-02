package main.java.com.ctu.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet(); // Thao tác nguyên tử an toàn luồng tuyệt đối
    }

    public int getCount() {
        return count.get();
    }
}