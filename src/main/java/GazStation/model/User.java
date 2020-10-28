package GazStation.model;

import lombok.*;

@Getter
@Setter
@ToString(exclude = "password")
@EqualsAndHashCode(of = "user")

public class User {
    private int id;
    private String user;
    private Double cash;
    @Setter(AccessLevel.NONE)
    private String password;

    public User(int id, String user,Double cash, String password) {
        this.id = id;
        this.user = user;
        this.cash = cash;
        this.password = password;
    }
}
