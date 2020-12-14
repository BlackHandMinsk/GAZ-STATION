package GazStation.service;

import GazStation.dto.OtherOrdersDto;
import GazStation.dto.OtherProductDto;
import GazStation.dto.ProductDto;
import GazStation.exceptions.ItemNotFoundException;
import GazStation.dto.OrdersDto;
import GazStation.model.Orders;
import GazStation.model.OtherOrders;
import GazStation.model.OtherProduct;
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
    public OtherOrdersDto getByOtherOrders (int idUser)throws SQLException, ItemNotFoundException{
        OtherOrders otherOrders = ordersRepository.getByOtherOrders(idUser);
        return OtherOrdersDto.builder()
                .otherOrders(otherOrders.getOtherOrders()).
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
    public ArrayList<OtherProductDto> getOtherProducts ()throws SQLException, ItemNotFoundException{

        ArrayList<OtherProduct> arr = ordersRepository.getOtherProducts();
        ArrayList<OtherProductDto> arrDto = new ArrayList<>();
        for (OtherProduct product:
                arr) {

            arrDto.add(OtherProductDto.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .cost(product.getCost())
                    .build());
        }
        return arrDto;
    }
}
