package GazStation.model;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(of = "id")

public class Product {
    private int id;
    private String title;
    public static double cost;

    public Product(int id, String title, double cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
