package main.java.com.ctu.concurrency;

import java.util.concurrent.CompletableFuture;

public class AsyncPipelineDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("🚀 Luồng chính bắt đầu...");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Dữ liệu người dùng ID: 101";
        }).thenApply(data -> data.toUpperCase() + " [ĐÃ XÁC THỰC]");

        future.thenAccept(result -> {
            System.out.println("📩 Kết quả xử lý bất đồng bộ: " + result);
        });

        System.out.println("⏳ Luồng chính không bị nghẽn, tiếp tục làm việc khác...");
        Thread.sleep(1200);
    }
}