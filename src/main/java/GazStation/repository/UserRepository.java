package GazStation.repository;

import GazStation.exceptions.ItemNotFoundException;
import GazStation.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
public class UserRepository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/graduateWork?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private Connection connection;

    public  Connection getConnection() throws SQLException {

        if (connection == null) {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            this.connection = connection;
        }

        return connection;
    }


    public Double getUserCash(int idUser) throws SQLException {
        String query = "select cash from users where id_users=?;";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setInt(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            throw new ItemNotFoundException("User");
        Double cash = resultSet.getDouble("cash");
        return cash;
    }

    public User getByUser(String userName) throws SQLException {
        String query = "select * from users where user=?;";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, userName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new ItemNotFoundException("User");

        int id = resultSet.getInt("id_users");
        String user = resultSet.getString("user");
        Double cash = resultSet.getDouble("cash");
        String password = resultSet.getString("password");

        return new User(id, user, cash, password);
    }

    public User newUser(String inName,Double inCash, String inPassword) throws SQLException{
        String inPasswordHex = DigestUtils.sha256Hex(inPassword);

        String query = "insert into users value (null,?,?,?);";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, inName);
        preparedStatement.setDouble(2, inCash);
        preparedStatement.setString(3, inPasswordHex);
        preparedStatement.executeUpdate();


        return getByUser(inName);

    }
}
