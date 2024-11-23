package Controller;

import DBConnection.dbController;
import Project.auction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

public class auctionManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public auctionManager() {
        this.connection = dbController.getConnection();
    }

    // Add a new auction to the database
    public boolean addAuction(String auctionID, LocalDate auctionDate, int capacity) {
        auction newAuction = new auction(auctionID, auctionDate, capacity);
        return newAuction.addToDB();
    }

    public ArrayList<auction> getAuctionList() {
        ArrayList<auction> auctionList = new ArrayList<>();
        String query = "SELECT * FROM Auction";

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String auctionID = resultSet.getString("auctionID");
                LocalDate auctionTime = resultSet.getDate("auctionTime").toLocalDate();
                int capacity = resultSet.getInt("capacity");
                boolean active = resultSet.getBoolean("active");

                auction auction = new auction(auctionID, auctionTime, capacity);
                auction.active = active;
                auctionList.add(auction);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve auctions from the database.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return auctionList;
    }

    public auction getAuction(String auctionID) {
        String query = "SELECT * FROM Auction WHERE auctionID = ?";
        auction auction = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, auctionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LocalDate auctionTime = resultSet.getDate("auctionTime").toLocalDate();
                int capacity = resultSet.getInt("capacity");
                boolean active = resultSet.getBoolean("active");

                auction = new auction(auctionID, auctionTime, capacity);
                auction.active = active;
            } else {
                System.out.println("No auction found with ID: " + auctionID);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve auction from the database.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return auction;
    }
}
