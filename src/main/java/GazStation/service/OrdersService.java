package GazStation.service;

import GazStation.dto.ProductDto;
import GazStation.exceptions.ItemNotFoundException;
import GazStation.dto.OrdersDto;
import GazStation.model.Orders;
import GazStation.model.Product;
import GazStation.repository.OrdersRepository;


import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersService {
    private OrdersRepository ordersRepository = new OrdersRepository();


    public OrdersDto getByOrders (int idUser)throws SQLException, ItemNotFoundException{
        Orders orders = ordersRepository.getByOrders(idUser);
        return OrdersDto.builder()
                .orders(orders.getOrders()).
                        build();
    }

    public ArrayList<ProductDto> getProducts ()throws SQLException, ItemNotFoundException{

        ArrayList<Product> arr = ordersRepository.getProducts();
        ArrayList<ProductDto> arrDto = new ArrayList<>();
        for (Product product:
                arr) {

            arrDto.add(ProductDto.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .cost(product.getCost())
                    .build());
        }
        return arrDto;
    }
}
