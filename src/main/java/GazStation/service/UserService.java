package GazStation.service;

import GazStation.dto.UserDto;
import GazStation.exceptions.ItemNotFoundException;
import GazStation.model.User;
import GazStation.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;


import java.sql.SQLException;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public UserDto getByUser(String in) throws SQLException, ItemNotFoundException {

        User user = userRepository.getByUser(in);

        return UserDto.builder()
                .id(user.getId())
                .user(user.getUser())
                .cash(user.getCash())
                .password(user.getPassword())
                .build();
    }

    public UserDto newUser(String inName,Double inCash, String inPassword) throws SQLException, ItemNotFoundException {

        User user = userRepository.newUser(inName,inCash,inPassword);

        return UserDto.builder()
                .id(user.getId())
                .user(user.getUser())
                .cash(user.getCash())
                .password(user.getPassword())
                .build();
    }


    public boolean checkPassword(String in, UserDto user) {
        return DigestUtils.sha256Hex(in).equals(user.getPassword());
    }
}
