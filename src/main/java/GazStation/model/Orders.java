package GazStation.model;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
@Getter
@Setter
public class Orders {
    private Map<Integer, ArrayList<Item>> orders;

    public Orders(Map<Integer, ArrayList<Item>> orders) {
        this.orders = orders;
    }
    }
