package GazStation.repository;


//import GazStation.exceptions.CashNotEnaughException;
import GazStation.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class OrdersRepository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/belhard24?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if (connection == null) {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            this.connection = connection;
        }

        return connection;
    }

    public Orders getByOrders(int idUser)throws SQLException{
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("select o.id_orders from orders o join users u on u.id_users = ? and o.id_user = ?;");
        preparedStatement.setInt(1, idUser);
        preparedStatement.setInt(2, idUser);

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> numbersOrders = new ArrayList<>();


        while(resultSet.next()) {
            int id_orders = resultSet.getInt("id_orders");
            numbersOrders.add(id_orders);
        }

        Map<Integer, ArrayList<Item>> map = fillingOrders(numbersOrders);


        Orders orders = new Orders(map);
        return orders;
    }

    public OtherOrders getByOtherOrders(int idUser)throws SQLException{
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("select o.id_otherOrders from otherOrders o join users u on u.id_users = ? and o.id_user = ?;");
        preparedStatement.setInt(1, idUser);
        preparedStatement.setInt(2, idUser);

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Integer> numbersOrders = new ArrayList<>();


        while(resultSet.next()) {
            int id_orders = resultSet.getInt("id_otherOrders");
            numbersOrders.add(id_orders);
        }

        Map<Integer, ArrayList<OtherItem>> map = fillingOtherOrders(numbersOrders);


        OtherOrders orders = new OtherOrders(map);
        return orders;
    }



    public Map<Integer, ArrayList<Item>> fillingOrders(ArrayList<Integer> numbersOrders) throws SQLException{
        Map<Integer, ArrayList<Item>> map = new HashMap<>();
        for (Integer i:numbersOrders) {

            PreparedStatement preparedStatement =
                    getConnection().prepareStatement(" select p.title,p.cost, oi.quantity, order_cost from order_items oi join orders o join products p on oi.id_order = ? and o.id_orders=? and oi.id_product=p.id_products;");
            preparedStatement.setInt(1, i);
            preparedStatement.setInt(2, i);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Item> orders = new ArrayList<>();

            while(resultSet.next()) {
                String title = resultSet.getString("title");
                double cost = resultSet.getDouble("cost");
                int quantity = resultSet.getInt("quantity");
                double order_cost = resultSet.getDouble("order_cost");
                Item item = new Item(title,cost,quantity,order_cost);
                orders.add(item);
            }

            map.put(i,orders);
        }

        return map;
    }

    public Map<Integer, ArrayList<OtherItem>> fillingOtherOrders(ArrayList<Integer> numbersOrders) throws SQLException{
        Map<Integer, ArrayList<OtherItem>> map = new HashMap<>();
        for (Integer i:numbersOrders) {

            PreparedStatement preparedStatement =
                    getConnection().prepareStatement(" select p.title,p.cost, oi.quantity, order_cost from order_OtherItems oi join otherOrders o join otherProducts p on oi.id_order = ? and o.id_otherOrders=? and oi.id_product=p.id_products;");
            preparedStatement.setInt(1, i);
            preparedStatement.setInt(2, i);

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<OtherItem> orders = new ArrayList<>();

            while(resultSet.next()) {
                String title = resultSet.getString("title");
                double cost = resultSet.getDouble("cost");
                int quantity = resultSet.getInt("quantity");
                double order_cost = resultSet.getDouble("order_cost");
                OtherItem item = new OtherItem(title,cost,quantity,order_cost);
                orders.add(item);
            }

            map.put(i,orders);
        }

        return map;
    }

    public ArrayList<Product> getProducts()throws SQLException{
        ArrayList<Product> arr = new ArrayList<>();
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("select * from products;");


        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            int id = resultSet.getInt("id_products");
            String title = resultSet.getString("title");
            double cost = resultSet.getDouble("cost");
            Product product = new Product(id, title, cost);
            arr.add(product);
        }
        return arr;
    }

    public ArrayList<OtherProduct> getOtherProducts()throws SQLException{
        ArrayList<OtherProduct> arr = new ArrayList<>();
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("select * from otherProducts;");


        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            int id = resultSet.getInt("id_products");
            String title = resultSet.getString("title");
            double cost = resultSet.getDouble("cost");
            OtherProduct otherProduct = new OtherProduct(id, title, cost);
            arr.add(otherProduct);
        }
        return arr;
    }


    public Integer getNumberOrder(int idUser) throws SQLException{
        int numberOrder = 0;
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("SELECT id_orders FROM orders;");

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            numberOrder += 1;
        }
        numberOrder += 1;
        String query = "insert into orders value (null, ?);";
        PreparedStatement preparedStatement2 =
                getConnection().prepareStatement(query);
        preparedStatement2.setInt(1, idUser);

        preparedStatement2.executeUpdate();
        return numberOrder;
    }

    public Integer getNumberOtherOrder(int idUser) throws SQLException{
        int numberOrder = 0;
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("SELECT id_otherOrders FROM otherOrders;");

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            numberOrder += 1;
        }
        numberOrder += 1;
        String query = "insert into otherOrders value (null, ?);";
        PreparedStatement preparedStatement2 =
                getConnection().prepareStatement(query);
        preparedStatement2.setInt(1, idUser);

        preparedStatement2.executeUpdate();
        return numberOrder;
    }

    public void newOrder(int numberOrder, int idProduct, int quantity, double order_cost) throws SQLException{
        String query = "insert into order_items value ( ?, ?, ?,?);";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setInt(1, numberOrder);
        preparedStatement.setInt(2, idProduct);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setDouble(4, order_cost);
        preparedStatement.executeUpdate();

    }
    public void newOtherOrder(int numberOrder, int idProduct, int quantity, double order_cost) throws SQLException{
        String query = "insert into order_OtherItems value ( ?, ?, ?,?);";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setInt(1, numberOrder);
        preparedStatement.setInt(2, idProduct);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setDouble(4, order_cost);
        preparedStatement.executeUpdate();

    }
    public void CashUpdate(Double order_cost,int id_users) throws SQLException{
           String query = "UPDATE users SET cash = cash-? WHERE id_users=?";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setDouble(1, order_cost);
        preparedStatement.setInt(2, id_users);
        preparedStatement.executeUpdate();
    }
}
