package GazStation.exceptions;

import GazStation.dto.UserDto;
import GazStation.repository.OrdersRepository;
import GazStation.repository.UserRepository;
import GazStation.model.User;

public class CashNotEnaughException extends RuntimeException {
  public CashNotEnaughException(Throwable y) {
      System.out.println("Недостаточно денег");
        }
  }
