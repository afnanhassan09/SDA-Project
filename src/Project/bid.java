package Project;

import DBConnection.dbController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class bid {
    String bidID;
    String auctionID;
    String ItemID;
    double amount; // Adding bid amount as a field

    // Constructor to initialize the bid object with bidID, auctionID, ItemID, and amount
    public bid(String bidID, String auctionID, String ItemID, double amount) {
        this.bidID = bidID;
        this.auctionID = auctionID;
        this.ItemID = ItemID;
        this.amount = amount;
    }

    // Function to add the bid to the database
    public boolean addToDB() {
        String query = "INSERT INTO Bid (bidID, auctionID, ItemID, amount) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = dbController.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, this.bidID);
            preparedStatement.setString(2, this.auctionID);
            preparedStatement.setString(3, this.ItemID);
            preparedStatement.setDouble(4, this.amount); // Adding amount to the query

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Bid added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Failed to add bid to the database.");
            e.printStackTrace();
        }
        return false;
    }
}
