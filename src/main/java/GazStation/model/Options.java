package GazStation.model;

import lombok.*;
@Getter
@Setter
public class Options {
    private Integer id;
  @Setter(AccessLevel.NONE)
    private String title;
    private Double cost;

    public Options(Integer id, String title,Double cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;

    }
}
