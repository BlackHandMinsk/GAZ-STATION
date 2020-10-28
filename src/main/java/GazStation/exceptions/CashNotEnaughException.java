package GazStation.exceptions;

import GazStation.dto.UserDto;
import GazStation.model.User;

public class CashNotEnaughException extends RuntimeException {
    public CashNotEnaughException(double cash) {
        if (cash <= 0) {
            System.out.println("У вас не достаточно средств");
        }
    }
}
