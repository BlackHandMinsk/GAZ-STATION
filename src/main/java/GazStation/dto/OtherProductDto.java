package GazStation.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OtherProductDto {
    private int id;
    private String title;
    private double cost;

    @Override
    public String toString() {
        return  "НОМЕР ТОВАРА: " + id + " ТОВАР: " + title +  " Цена: " + cost ;

    }
}
