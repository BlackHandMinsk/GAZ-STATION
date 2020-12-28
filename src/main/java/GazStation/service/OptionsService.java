package GazStation.service;

import GazStation.dto.OptionsDto;
import GazStation.dto.UserDto;
import GazStation.exceptions.ItemNotFoundException;
import GazStation.model.Options;
import GazStation.model.User;
import GazStation.repository.OptionsRepository;
import GazStation.repository.UserRepository;

import java.sql.SQLException;


public class OptionsService {

    private OptionsRepository optionsRepository = new OptionsRepository();


    public OptionsDto newGoods(String title, Double cost) throws SQLException, ItemNotFoundException {

     Options options = optionsRepository.newGoods(title, cost);

        return OptionsDto.builder()
                .id(options.getId())
                .title(options.getTitle())
                .cost(options.getCost())
                .build();
    }

    public OptionsDto updateGood(String title,Double cost) throws SQLException, ItemNotFoundException {

        Options options = optionsRepository.updateGood(title,cost);

        return OptionsDto.builder()
                .id(options.getId())
                .title(options.getTitle())
                .cost(options.getCost())
                .build();
    }
}
