package GazStation.model;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
@Getter
@Setter

public class OtherOrders {
    private Map<Integer, ArrayList<OtherItem>> otherOrders;

    public OtherOrders(Map<Integer, ArrayList<OtherItem>> otherOrders) {
        this.otherOrders = otherOrders;
    }
}
