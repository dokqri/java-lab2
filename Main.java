import billing.CruiseLine;
import model.CorporateTicket;
import model.CruiseTicket;
import model.Excursion;
import model.IndividualTicket;
import service.impl.EmailNotification;
import service.impl.SmsNotification;

public class Main {
    public static void main(String[] args) {
        Excursion cityTour = new Excursion("Экскурсия по историческому центру", 1500.0);
        Excursion divingTour = new Excursion("Дайвинг у кораллового рифа", 4200.0);

        //billing с SMS-уведомлениями + билет физического лица
        CruiseLine smsLine = new CruiseLine(new SmsNotification());
        CruiseTicket individual = new IndividualTicket("Иванов И.И.", 20000.0, CruiseTicket.CABIN_WINDOW);
        double price1 = individual.calculateFinalPrice(cityTour);
        System.out.printf("Физлицо: %.2f руб.%n", price1);
        smsLine.processPayment(individual, "+79991234567", price1);
        System.out.println();

        //billing с Email-уведомлениями + корпоративный билет на 5 кают
        CruiseLine emailLine = new CruiseLine(new EmailNotification());
        CruiseTicket corporate = new CorporateTicket("ООО «Вектор»", 35000.0, CruiseTicket.CABIN_SUITE, 5);
        double price2 = corporate.calculateFinalPrice(divingTour, 10);
        System.out.printf("Корпорация: %.2f руб.%n", price2);
        emailLine.processPayment(corporate, "office@vector.ru", price2);
        System.out.println();

        //глобальная статистика по обоим типам билетов
        CruiseLine.printGlobalStats();
        System.out.println();

        //демонстрация проверок и исправлений «на лету»
        System.out.println("--- Проверки ---");
        smsLine.processPayment(individual, "12345", price1);                  // плохой номер
        emailLine.processPayment(corporate, "office-vector.ru", price2);      // плохой email
        CruiseTicket cheap = new IndividualTicket("Тест", -50.0);             // цена <= 0
        CruiseTicket noCabins = new CorporateTicket("ЗАО «Тест»", 10000.0,
                CruiseTicket.CABIN_INTERNAL, 0);                              // кают < 1
        corporate.calculateFinalPrice(cityTour);                              // экскурсия для корпоратива
        System.out.printf("Исправленные: цена=%.1f, кают=%d%n",
                cheap.getBasePrice(), ((CorporateTicket) noCabins).getCabinsCount());
    }
}
