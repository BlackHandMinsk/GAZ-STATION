package GazStation.controller;

import GazStation.dto.*;
import GazStation.exceptions.CashNotEnaughException;
import GazStation.exceptions.ItemNotFoundException;
import GazStation.model.OtherProduct;
import GazStation.model.Product;
import GazStation.repository.OrdersRepository;
import GazStation.repository.UserRepository;
import GazStation.service.OrdersService;
import GazStation.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuController {
    static final String  START_MENU =  "-------------------------\n1. ВСТАВИТЬ КАРТУ\ne. УЕХАТЬ С ЗАПРАВКИ\n-------------------------";
    private static final String  USER_MENU =  "-------------------------\n1. ПРЕДЫДУЩИЕ ЗАПРАВКИ\n1.1 ПРЕДЫДУЩИЕ ПОКУПКИ\n2. ЗАПРАВИТЬ МАШИНУ\n3. ОСТАТОК НА СЧЕТУ\n4. КУПИТЬ СОПУТСТВУЮЩИЕ ТОВАРЫ\ne. ПРЕДЫДУЩЕЕ МЕНЮ\n-------------------------";

    private final UserService userService = new UserService();
    private final OrdersService ordersService = new OrdersService();
    private final OrdersRepository ordersRepository = new OrdersRepository();
    private final UserRepository userRepository = new UserRepository();

    public void start() {
        String in = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"e".equals(in)) {
            try {
                System.out.println(START_MENU);
                in = reader.readLine();
                switch (in) {
                    case "1":
                        startSession();
                        break;
                    case "new":
                        startCreature();
                        break;
                    case "e":
                       break;
                }
            } catch (ItemNotFoundException | IOException e) {
                System.err.println("НЕВАЛИДНЫЙ ВВОД ДАННЫХ: " + e.getMessage());
            }
        }

    }

    public void startSession(){
        String in = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"e".equals(in)) {
            try {
                System.out.println("ВВЕДИТЕ ИМЯ ПОЛЬЗОВАТЕЛЯ, ЛИБО 'e' ДЛЯ ВЫХОДА В ПРЕДЫДУЩЕЕ МЕНЮ:");
                in = reader.readLine();
                if ("e".equals(in)){start();}
                else {
                    UserDto userDto = userService.getByUser(in);
                    if (userDto != null) {
                        System.out.println("ВВЕДИТЕ ПАРОЛЬ:");
                        in = reader.readLine();
                        if (userService.checkPassword(in, userDto)) {
                            startUserSession(userDto.getUser(),userDto.getId());
                            in = "e";
                        } else {
                            System.err.println("ПАРОЛЬ ВВЕДЕН НЕ ВЕРНО");
                        }
                    }
                }
            } catch(ItemNotFoundException e){
                System.err.println("ДАННАЯ УЧЕТНАЯ ЗАПИСЬ НЕ НАЙДЕНА: " + e.getMessage());
            } catch(IOException | SQLException e){
                e.printStackTrace();
            }

        }

    }


    private void startCreature() {
        String inName;
        String inPassword;
        Double inCash;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ВЫ ПОПАЛИ В СЕКРЕТНОЕ МЕНЮ СОЗДАНИЯ ПЕРСОНАЖА, ПОЛЬЗУЙТЕСЬ");
            System.out.println("ВВЕДИТЕ ИМЯ ПОЛЬЗОВАТЕЛЯ, ЛИБО 'e' ДЛЯ ВЫХОДЫ В ПРЕДЫДУЩЕЕ МЕНЮ:");
            inName = reader.readLine();
            if ("e".equals(inName)) {
                start();
            }
            else {
                System.out.println("ВВЕДИТЕ ПАРОЛЬ");
                inPassword = reader.readLine();
                System.out.println("ВВЕДИТЕ КОЛИЧЕСТВО ДЕНЕГ НА СЧЕТУ");
                inCash = Double.parseDouble(reader.readLine());
                UserDto userDto = userService.newUser(inName, inCash, inPassword);
                System.out.println("НОВЫЙ АККАУНТ СОЗДАН, ДОБРО ПОЖАЛОВАТЬ");
                startUserSession(userDto.getUser(),userDto.getId());
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    private void startUserSession(String user,int id) {
        String in = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"e".equals(in)) {
            try {
                System.out.println(USER_MENU);
                in = reader.readLine();
                switch (in) {
                    case "1":
                        orders(id);
                        break;
                    case "1.1":
                        otherOrders(id);
                        break;
                    case "2":
                        newOrder(id);
                        break;
                    case "3":
                        GK(id);
                        break;
                    case "4":
                        buyOther(id);
                        break;
                    case "e":
                        break;
                }
            } catch (ItemNotFoundException | IOException | SQLException e) {
                System.err.println("НЕВАЛИДНЫЙ ВВОД ДАННЫХ: " + e.getMessage());
            }
        }
    }

    private void buyOther(int idUser) {
        try {
            System.out.println("-----------------------ДОП.ПОКУПКИ---------------------");
            ArrayList<OtherProductDto> arr = ordersService.getOtherProducts();
            int numberOrder = ordersRepository.getNumberOtherOrder(idUser);
            System.out.println(arr.toString());
            String in = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (!"e".equals(in)) {
                System.out.println("ВВЕДИТЕ НОМЕР ТОВАРА И КОЛИЧЕСТВО  ЧЕРЕЗ ПРОБЕЛ, ДЛЯ ЗАВЕРШЕНИЯ ПОКУПОК НАЖМИТЕ 'e'");
                in = reader.readLine();
                if (!"e".equals(in)) {
                    String[] split = in.split(" ");
                    int idProduct = Integer.parseInt(split[0]);
                    int quantity = Integer.parseInt(split[1]);
                    double order_cost = OtherProduct.cost * quantity;
                    ordersRepository.newOtherOrder(numberOrder, idProduct, quantity, order_cost);
                    ordersRepository.CashUpdate(order_cost, idUser);
                }
            }

        } catch (ItemNotFoundException | IOException | CashNotEnaughException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("НЕВАЛИДНЫЙ ВВОД");
        }catch (SQLException e){
            System.out.println("НЕДОСТАТОЧНО ДЕНЕГ НА СЧЕТУ");
        }
    }


    private void orders(int idUser) {
        try {
            System.out.println("-------------------------\nВАШИ ПРЕДЫДУЩИЕ ЗАПРАВКИ:\n-------------------------");
            OrdersDto ordersDto = ordersService.getByOrders(idUser);
            System.out.println(ordersDto.toString());
        }catch (ItemNotFoundException e) {
            System.err.println("ЗАПРАВКИ ОТСУТСТВУЮТ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void otherOrders(int idUser) {
        try {
            System.out.println("-------------------------\nВАШИ ПРЕДЫДУЩИЕ ПОКУПКИ:\n-------------------------");
            OtherOrdersDto otherOrdersDto = ordersService.getByOtherOrders(idUser);
            System.out.println(otherOrdersDto.toString());
        }catch (ItemNotFoundException e) {
            System.err.println("ПОКУПКИ ОТСУТСТВУЮТ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void GK(int idUser) throws SQLException{
        System.out.println("-------------------------\nОСТАТОК ДЕНЕГ НА СЧЕТУ:\n-------------------------");
        Double cash = userRepository.getUserCash(idUser);
        System.out.println(cash);
       // return Double.valueOf(0);
    }
    private void newOrder(int idUser) throws CashNotEnaughException {
        try {
            System.out.println("-------------------------\nСВОБОДНЫЕ КОЛОНКИ:\n-------------------------");
            ArrayList<ProductDto> arr = ordersService.getProducts();
           // ArrayList<ProductDto> arrNewOrder = new ArrayList<>();
          //  OrdersDto newOrder = new OrdersDto();
            System.out.println(arr.toString());


            int numberOrder = ordersRepository.getNumberOrder(idUser);

            String in = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (!"e".equals(in)) {
                System.out.println("ВВЕДИТЕ НОМЕР ПИСТОЛЕТА И КОЛИЧЕСТВО ТОПЛИВА ЧЕРЕЗ ПРОБЕЛ, ДЛЯ ЗАВЕРШЕНИЯ ЗАПРАВКИ НАЖМИТЕ 'e'");
                in = reader.readLine();
                if (!"e".equals(in)) {
                    String[] split = in.split(" ");
                    int idProduct = Integer.parseInt(split[0]);
                    int quantity = Integer.parseInt(split[1]);
                    double order_cost = Product.cost * quantity;
                        ordersRepository.newOrder(numberOrder, idProduct, quantity, order_cost);
                        ordersRepository.CashUpdate(order_cost, idUser);
                }
            }

        } catch (ItemNotFoundException | IOException | CashNotEnaughException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("НЕВАЛИДНЫЙ ВВОД");
        }catch (SQLException e){
            System.out.println("НЕДОСТАТОЧНО ДЕНЕГ НА СЧЕТУ");
        }
        }
    }
