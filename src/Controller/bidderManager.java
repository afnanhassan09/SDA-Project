package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnection.dbController;
import Project.bidder;

public class bidderManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public bidderManager() {
        this.connection = dbController.getConnection();
    }

    public boolean addBidder(String username, String password, String email, String name) {
        bidder bidder = new bidder(username, password, email, name);
        return bidder.addToDB();
    }

    public ArrayList<bidder> getBidderList() {
        ArrayList<bidder> bidderList = new ArrayList<>();
        String query = "SELECT * FROM Bidder";

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                int bids = resultSet.getInt("bids");
                boolean active = resultSet.getBoolean("active");

                bidder bidder = new bidder(username, password, email, name);
                bidder.bids = bids;
                bidder.active = active;

                bidderList.add(bidder);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve bidders from the database.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bidderList;
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM Bidder WHERE username = ? AND password = ?";
        boolean isValidUser = false;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValidUser = true;
                System.out.println("Login successful for user: " + username);
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to verify login credentials.");
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
