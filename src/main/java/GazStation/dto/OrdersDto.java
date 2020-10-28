package GazStation.dto;

import lombok.*;
import GazStation.model.Item;

import java.util.ArrayList;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrdersDto {
    private Map<Integer, ArrayList<Item>> orders;

    @Override
    public String toString() {
        return "СПИСОК ПРЕДЫДУЩИХ ЗАПРАВОК: " + orders;
    }
}
