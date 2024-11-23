package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.dbController;
import Project.auctioneer;

public class auctioneerManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public auctioneerManager() {
        this.connection = dbController.getConnection();
    }

    public boolean addAuctioneer(String username, String password, String email, String name, int salary, int level) {
        auctioneer auctioneer = new auctioneer(username, password, email, name, salary, level);
        return auctioneer.addToDB();
    }

    public ArrayList<auctioneer> getAuctioneerList() {
        ArrayList<auctioneer> auctioneerList = new ArrayList<>();
        String query = "SELECT * FROM Auctioneer";

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");
                int level = resultSet.getInt("level");

                // Create an auctioneer object and add it to the list
                auctioneer auctioneer = new auctioneer(username, password, email, name, salary, level);
                auctioneerList.add(auctioneer);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve auctioneers from the database.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return auctioneerList;
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM Auctioneer WHERE username = ? AND password = ?";
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
                System.out.println("Login successful for auctioneer: " + username);
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to verify auctioneer login credentials.");
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
