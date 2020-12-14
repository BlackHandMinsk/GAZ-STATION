package GazStation.dto;

import GazStation.model.Item;
import GazStation.model.OtherItem;
import lombok.*;
import java.util.ArrayList;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OtherOrdersDto {
    private Map<Integer, ArrayList<OtherItem>> otherOrders;

    @Override
    public String toString() {
        return "СПИСОК ПРЕДЫДУЩИХ ПОКУПОК: " + otherOrders;
    }
}
