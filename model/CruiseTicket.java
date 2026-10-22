package model;

public abstract class CruiseTicket {

    public static final String CABIN_INTERNAL = "INTERNAL";
    public static final String CABIN_WINDOW = "WINDOW";
    public static final String CABIN_SUITE = "SUITE";

    private static final double COEFFICIENT_INTERNAL = 1.00; // +0%
    private static final double COEFFICIENT_WINDOW = 1.45;   // +45%
    private static final double COEFFICIENT_SUITE = 2.20;    // +120%

    private static final double MIN_BASE_PRICE = 100.0;
    private static final String DEFAULT_CABIN_TYPE = CABIN_INTERNAL;

    private final String passengerName;
    private final double basePrice;
    private String cabinType;
    private Excursion excursion = null;

    /** Основной конструктор. Некорректная цена исправляется «на лету». */
    protected CruiseTicket(String passengerName, double basePrice, String cabinType) {
        this.passengerName = passengerName;
        if (basePrice <= 0) {
            System.out.println("[WARNING] Базовая цена " + basePrice
                    + " некорректна. Установлена минимальная цена " + MIN_BASE_PRICE);
            this.basePrice = MIN_BASE_PRICE;
        } else {
            this.basePrice = basePrice;
        }
        setCabinType(cabinType);
    }

    /** Дополнительный конструктор: тип каюты по умолчанию (цепочка this). */
    protected CruiseTicket(String passengerName, double basePrice) {
        this(passengerName, basePrice, DEFAULT_CABIN_TYPE);
    }

    public String getPassengerName() {
        return passengerName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getCabinType() {
        return cabinType;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setCabinType(String cabinType) {
        if (cabinType == null) {
            throw new IllegalArgumentException("Тип каюты не может быть null");
        }
        String normalized = cabinType.toUpperCase();
        if (!normalized.equals(CABIN_INTERNAL)
                && !normalized.equals(CABIN_WINDOW)
                && !normalized.equals(CABIN_SUITE)) {
            throw new IllegalArgumentException("Неизвестный тип каюты: " + cabinType);
        }
        this.cabinType = normalized;
    }

    /**
     * Коэффициент тарифа по типу каюты. Многострочный switch с yield;
     * в default админу выводится предупреждение, а не молча возвращается 1.
     */
    protected double resolveCabinCoefficient() {
        return switch (cabinType) {
            case CABIN_INTERNAL -> COEFFICIENT_INTERNAL;
            case CABIN_WINDOW -> COEFFICIENT_WINDOW;
            case CABIN_SUITE -> {
                double coefficient = COEFFICIENT_SUITE;
                yield coefficient;
            }
            default -> {
                System.out.println("[ADMIN WARNING] Неизвестный тип каюты \"" + cabinType
                        + "\" у пассажира " + passengerName + ". Применён коэффициент 1.0");
                yield 1.0;
            }
        };
    }

    /** Абстрактный метод: общих правил расчёта нет, каждый тип билета считает по-своему. */
    public abstract double calculateFinalPrice();

    /** Билет + экскурсия на одного человека. */
    public double calculateFinalPrice(Excursion excursion) {
        this.excursion = excursion;
        return calculateFinalPrice() + excursion.getPrice();
    }

    /** Билет + экскурсия на несколько человек (вариант 12). */
    public double calculateFinalPrice(Excursion excursion, int people) {
        if (people <= 0) {
            throw new IllegalArgumentException(
                    "Количество человек на экскурсии должно быть положительным: " + people);
        }
        this.excursion = excursion;
        return calculateFinalPrice() + excursion.getPrice() * people;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{passenger='" + passengerName + "', basePrice=" + basePrice
                + ", cabinType='" + cabinType + "', excursion=" + excursion + '}';
    }
}
