package Controller;

import Project.bid;
import DBConnection.dbController;
import java.sql.*;
import java.util.ArrayList;

public class bidManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public bidManager() {
        this.connection = dbController.getConnection();
    }

    // Add a new bid
    public boolean addBid(String bidID, String auctionID, String itemID, double amount) {
        bid newBid = new bid(bidID, auctionID, itemID, amount);
        String query = "INSERT INTO Bid (bidID, auctionID, ItemID, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bidID);
            preparedStatement.setString(2, auctionID);
            preparedStatement.setString(3, itemID);
            preparedStatement.setDouble(4, amount);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Bid added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while adding the bid.");
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve all bids
    public ArrayList<bid> getAllBids() {
        ArrayList<bid> bidList = new ArrayList<>();
        String query = "SELECT * FROM Bid";

        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bidID = resultSet.getString("bidID");
                String auctionID = resultSet.getString("auctionID");
                String itemID = resultSet.getString("ItemID");
                double amount = resultSet.getDouble("amount");

                bid newBid = new bid(bidID, auctionID, itemID, amount);
                bidList.add(newBid);
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve bids from the database.");
            e.printStackTrace();
        }

        return bidList;
    }

    // Retrieve bids for a specific item
    public ArrayList<bid> getBidsForItem(String itemID) {
        ArrayList<bid> bidList = new ArrayList<>();
        String query = "SELECT * FROM Bid WHERE ItemID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bidID = resultSet.getString("bidID");
                String auctionID = resultSet.getString("auctionID");
                double amount = resultSet.getDouble("amount");

                bid newBid = new bid(bidID, auctionID, itemID, amount);
                bidList.add(newBid);
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve bids for item from the database.");
            e.printStackTrace();
        }

        return bidList;
    }

    // Retrieve bids for a specific auction
    public ArrayList<bid> getBidsForAuction(String auctionID) {
        ArrayList<bid> bidList = new ArrayList<>();
        String query = "SELECT * FROM Bid WHERE auctionID = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, auctionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bidID = resultSet.getString("bidID");
                String itemID = resultSet.getString("ItemID");
                double amount = resultSet.getDouble("amount");

                bid newBid = new bid(bidID, auctionID, itemID, amount);
                bidList.add(newBid);
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve bids for auction from the database.");
            e.printStackTrace();
        }

        return bidList;
    }

    public bid getHighestBidForItem(String auctionID, String itemID) {
        bid highestBid = null;
        String query = "SELECT * FROM Bid WHERE ItemID = ? AND auctionID = ? ORDER BY amount DESC LIMIT 1";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemID);
            preparedStatement.setString(2, auctionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String bidID = resultSet.getString("bidID");
                double amount = resultSet.getDouble("amount");

                highestBid = new bid(bidID, auctionID, itemID, amount);
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve highest bid for item and auction from the database.");
            e.printStackTrace();
        }

        return highestBid;
    }

}
