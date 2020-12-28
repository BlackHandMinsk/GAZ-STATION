package GazStation.repository;

import GazStation.exceptions.ItemNotFoundException;
import GazStation.model.Options;
import GazStation.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

public class OptionsRepository {
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

    public Options newGoods(String title, Double cost) throws SQLException {
        String query = "insert into otherProducts value (null, ?, ?);";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setDouble(2, cost);
        preparedStatement.executeUpdate();
       return getByNewGoods(title);
    }
    public Options updateGood(String title,Double cost) throws SQLException {
        String query = "UPDATE otherProducts SET title = ? WHERE Manufacturer = 'Samsung';";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, title);
        preparedStatement.executeUpdate();
        return getByNewGoods(title);
    }



    public Options getByNewGoods(String title) throws SQLException {
        String query = "select * from otherProducts where title=?;";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new ItemNotFoundException("Product");

        Integer id_products = resultSet.getInt("id_products");
        String title2 = resultSet.getString("title");
        Double cost = resultSet.getDouble("cost");

        return new Options(id_products, title2, cost);
    }
}
