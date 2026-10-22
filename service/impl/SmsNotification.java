package service.impl;

import service.NotificationService;

/** отправка уведомлений по SMS (имитация вывода в консоль). */
public class SmsNotification implements NotificationService {

    private static final int MIN_PHONE_LENGTH = 11;

    @Override
    public void sendNotification(String message, String recipient) {
        // Проверка: номер начинается с +7 или 8 и содержит не менее 11 символов
        boolean validStart = recipient != null && (recipient.startsWith("+7") || recipient.startsWith("8"));
        if (!validStart || recipient.length() < MIN_PHONE_LENGTH) {
            System.out.println("[WARNING] Некорректный номер \"" + recipient
                    + "\": SMS не отправлено.");
            return;
        }
        System.out.println("[SMS] На номер " + recipient + " отправлено сообщение: " + message);
    }
}
