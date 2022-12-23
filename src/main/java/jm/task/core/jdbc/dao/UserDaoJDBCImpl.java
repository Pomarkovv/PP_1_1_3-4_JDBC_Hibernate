package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format(
                        "CREATE TABLE IF NOT EXISTS users(%s, %s, %s, %s);",
                        "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT",
                        "name VARCHAR(255)",
                        "lastName VARCHAR(255)",
                        "age INT"
                );
                statement.execute(sql);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "DROP TABLE IF EXISTS users;";
                statement.execute(sql);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastName, age) VALUES(?, ?, ?);")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = (?)")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT name, lastName, age FROM users;")) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    users.add(makeUser(result));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "TRUNCATE TABLE users";
                statement.execute(sql);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User makeUser(ResultSet result) throws SQLException {
        return new User(result.getString(1), result.getString(2),
                result.getByte(3));
    }
}
