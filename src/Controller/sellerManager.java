package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.dbController;
import Project.seller;

public class sellerManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public sellerManager() {
        this.connection = dbController.getConnection();
    }

    public boolean addSeller(String username, String password, String email, String name) {
        seller seller = new seller(username, password, email, name);
        return seller.addToDB();
    }

    public ArrayList<seller> getSellerList() {
        ArrayList<seller> sellerList = new ArrayList<>();
        String query = "SELECT * FROM Seller";

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                int itemsSold = resultSet.getInt("itemsSold");
                boolean active = resultSet.getBoolean("active");

                // Create a seller object and add it to the list
                seller seller = new seller(username, password, email, name);
                seller.itemsSold = itemsSold;
                seller.active = active;

                sellerList.add(seller);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve sellers from the database.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sellerList;
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM Seller WHERE username = ? AND password = ?";
        boolean isValidUser = false;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If a record is found, the username and password are valid
                isValidUser = true;
                System.out.println("Login successful for seller: " + username);
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to verify seller login credentials.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isValidUser;
    }
}
