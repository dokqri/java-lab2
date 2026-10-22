package service.impl;

import service.NotificationService;

/** отправка уведомлений по электронной почте (имитация вывода в консоль). */
public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String message, String recipient) {
        // Проверка: адрес должен содержать символ '@' и точку
        if (recipient == null || !recipient.contains("@") || !recipient.contains(".")) {
            System.out.println("[WARNING] Некорректный email \"" + recipient
                    + "\": письмо не отправлено.");
            return;
        }
        System.out.println("[EMAIL] На адрес " + recipient + " отправлено письмо: " + message);
    }
}
