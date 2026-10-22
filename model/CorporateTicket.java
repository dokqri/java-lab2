package model;

/** корпоративный билет: (базовая цена * коэффициент каюты) * число кают. */
public class CorporateTicket extends CruiseTicket {

    private final int cabinsCount;

    public CorporateTicket(String companyName, double basePrice, String cabinType, int cabinsCount) {
        super(companyName, basePrice, cabinType);
        if (cabinsCount < 1) {
            System.out.println("[WARNING] Количество кают " + cabinsCount
                    + " некорректно. Установлено значение 1");
            this.cabinsCount = 1;
        } else {
            this.cabinsCount = cabinsCount;
        }
    }

    public int getCabinsCount() {
        return cabinsCount;
    }

    @Override
    public double calculateFinalPrice() {
        return getBasePrice() * resolveCabinCoefficient() * cabinsCount;
    }

    /**
     * не подключается — выводим предупреждение и возвращаем стандартную цену.
     * групповая экскурсия calculateFinalPrice(excursion, people) работает как обычно.
     */
    @Override
    public double calculateFinalPrice(Excursion excursion) {
        System.out.println("[WARNING] Корпоративным клиентам индивидуальная экскурсия не применяется. "
                + "Возвращена стандартная цена.");
        return calculateFinalPrice();
    }
}
