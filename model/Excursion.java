package model;

/** класс Option: независимая дополнительная услуга — экскурсия. */
public class Excursion {

    private final String name;
    private double price;

    public Excursion(String name, double price) {
        this.name = name;
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                    "Стоимость экскурсии не может быть отрицательной: " + price);
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Excursion{name='" + name + "', price=" + price + '}';
    }
}
