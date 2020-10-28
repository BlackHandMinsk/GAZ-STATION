package GazStation.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class ProductDto {
    private int id;
    private String title;
    private double cost;

    @Override
    public String toString() {
        return  "НОМЕР ПИСТОЛЕТА: " + id + " Топливо: " + title +  " Цена: " + cost ;

    }
}
