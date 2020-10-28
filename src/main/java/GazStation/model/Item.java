package GazStation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private String title;
    private double cost;
    private int quantity;
    private double order_cost;

    public Item(String title, double cost, int quantity,double order_cost) {
        this.title = title;
        this.cost = cost;
        this.quantity = quantity;
        this.order_cost = order_cost;
    }

    @Override
    public String toString() {
        return "ТОПЛИВО: " + title +" "+
                "ЦЕНА: " + cost +" "+
                "КОЛИЧЕСТВО: " + quantity +" "+
                "СУММА ЗАПРАВКИ: "+ order_cost+" "+
                '\n';
    }
}
