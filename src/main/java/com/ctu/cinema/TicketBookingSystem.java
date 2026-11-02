package main.java.com.ctu.cinema;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TicketBookingSystem {
    private static final int TOTAL_SEATS = 50;
    private final boolean[] seats = new boolean[TOTAL_SEATS + 1];
    private int successfulBookings = 0;
    private int failedBookings = 0;

    public boolean bookSeat(int seatNumber, String customerName) {
        if (seatNumber < 1 || seatNumber > TOTAL_SEATS) {
            System.err.println("❌ Ghế không hợp lệ: " + seatNumber);
            return false;
        }

        // MIỀN TỐI HẠN CHƯA ĐƯỢC BẢO VỆ
        if (!seats[seatNumber]) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seats[seatNumber] = true;
            successfulBookings++;
            System.out.println("✅ [Thành công] " + customerName + " đã đặt thành công ghế #" + seatNumber);
            return true;
        } else {
            failedBookings++;
            System.out.println("❌ [Thất bại] " + customerName + " đặt ghế #" + seatNumber + " nhưng ghế ĐÃ ĐƯỢC ĐẶT!");
            return false;
        }
    }

    public int getSuccessfulBookings() {
        return successfulBookings;
    }

    public int getFailedBookings() {
        return failedBookings;
    }

    public static void main(String[] args) throws InterruptedException {
        TicketBookingSystem bookingSystem = new TicketBookingSystem();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        System.out.println("🚀 Bắt đầu mô phỏng 100 yêu cầu đặt vé đồng thời...");
        for (int i = 1; i <= 100; i++) {
            final int customerId = i;
            final int targetSeat = (i % 50) + 1;
            executor.submit(() -> {
                bookingSystem.bookSeat(targetSeat, "Khách hàng #" + customerId);
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\n========= THỐNG KÊ TỔNG KẾT (UNSAFE RUN) =========");
        System.out.println("Tổng số lượt báo THÀNH CÔNG: " + bookingSystem.getSuccessfulBookings());
        System.out.println("Tổng số lượt báo THẤT BẠI: " + bookingSystem.getFailedBookings());
        System.out.println("Tổng số yêu cầu đã xử lý: " + (bookingSystem.getSuccessfulBookings() + bookingSystem.getFailedBookings()));
        System.out.println("=================================================");
    }
}