package GazStation.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ItemDto {
    private String title;
    private double cost;
    private int quantity;
   private double order_cost;

    @Override
    public String toString() {
        return
                "НОМЕР='" + title  +
                "ЦЕНА=" + cost +
                "КОЛИЧЕСТВО=" + quantity + order_cost;
    }
}
