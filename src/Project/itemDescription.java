package Project;
import DBConnection.dbController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class itemDescription {
	String itemID;
	String ItemName;
	String ItemDescription;
	
	public itemDescription(String itemID, String itemName, String itemDescription) {
        this.itemID = itemID;
        this.ItemName = itemName;
        this.ItemDescription = itemDescription;
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbController.getConnection();

            String query = "INSERT INTO ItemsDescription (itemID, ItemName, ItemDescription) VALUES (?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, itemID);
            preparedStatement.setString(2, itemName);
            preparedStatement.setString(3, itemDescription);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error while inserting data.");
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
	
	public String getItemDescription()
	{
		return "This is the description";
	}
}
