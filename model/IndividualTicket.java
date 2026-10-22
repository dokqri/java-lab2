package model;

/** билет физического лица: базовая цена * коэффициент каюты. */
public class IndividualTicket extends CruiseTicket {

    public IndividualTicket(String passengerName, double basePrice, String cabinType) {
        super(passengerName, basePrice, cabinType);
    }

    public IndividualTicket(String passengerName, double basePrice) {
        super(passengerName, basePrice);
    }

    @Override
    public double calculateFinalPrice() {
        return getBasePrice() * resolveCabinCoefficient();
    }
}
