package GazStation.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserDto {
    private int id;
    private String user;
    private Double cash;
    private String password;
}

