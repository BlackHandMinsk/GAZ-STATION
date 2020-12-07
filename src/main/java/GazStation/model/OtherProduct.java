package GazStation.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")

public class OtherProduct {
    private int id;
    private String title;
    public static double cost;

    public OtherProduct(int id, String title, double cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
