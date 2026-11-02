package main.java.com.ctu.concurrency;

public class UnsafeCounter {
    private int count = 0;

    public void increment() {
        count++; // Lỗi Race Condition tại đây!
    }

    public int getCount() {
        return count;
    }
}