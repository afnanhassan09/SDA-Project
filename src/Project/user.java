package Project;

import DBConnection.dbController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class user {
    public String username;
    public String password;
    public String email;
    public String name;
    public String role;

    public user(String username, String password, String email, String name, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
    }
    
    public user()
    {
    	
    }
    public static boolean login(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error during login.");
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean register(String username, String password, String email, String name, String role) {
        String query = "INSERT INTO Users (username, password, email, name, role) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, role);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                return true;
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate")) {
                System.err.println("Username or email already exists.");
            } else {
                System.err.println("Error during registration.");
                e.printStackTrace();
            }
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
