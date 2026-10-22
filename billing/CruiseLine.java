package billing;

import model.CruiseTicket;
import service.NotificationService;

public class CruiseLine {

    private static int totalOrdersCount = 0;
    private static double totalRevenue = 0.0;

    // Тип поля — интерфейс, а не конкретный класс!
    private final NotificationService notificationService;

    public CruiseLine(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /** Регистрирует оплату (обновляет статику) и отправляет чек клиенту. */
    public void processPayment(CruiseTicket ticket, String contact, double finalPrice) {
        totalOrdersCount++;
        totalRevenue += finalPrice;
        notificationService.sendNotification(
                String.format("Оплата билета (%s) принята. Сумма: %.2f руб.",
                        ticket.getPassengerName(), finalPrice),
                contact);
    }

    public static int getTotalOrdersCount() {
        return totalOrdersCount;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static void printGlobalStats() {
        System.out.println("=====================================");
        System.out.println("   ГЛОБАЛЬНАЯ СТАТИСТИКА CruiseLine");
        System.out.println("=====================================");
        System.out.printf("Всего оформлено билетов : %d%n", totalOrdersCount);
        System.out.printf("Суммарная выручка       : %.2f руб.%n", totalRevenue);
        System.out.println("=====================================");
    }
}
