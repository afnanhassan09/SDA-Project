package Project;
import DBConnection.dbController;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


public class auction {
	public String auctionID;
    public LocalDate auctionTime;
    public int capacity;
    public boolean active;
    
    public auction(String auctionID, LocalDate auctionDate, int capacity) {
    	this.auctionID = auctionID;
        this.auctionTime = auctionDate;
        this.capacity = capacity;
        this.active = true;
    }
    
    public boolean addToDB() {
        String query = "INSERT INTO Auction (auctionID, auctionTime, capacity, active) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbController.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, this.auctionID);
            preparedStatement.setDate(2, java.sql.Date.valueOf(this.auctionTime)); // Convert LocalDate to java.sql.Date
            preparedStatement.setInt(3, this.capacity);
            preparedStatement.setBoolean(4, this.active);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Auction added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Failed to add auction to the database.");
            e.printStackTrace();
        }
        return false;
    }
}
