package Project;

import DBConnection.dbController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class seller extends user {
    public int itemsSold;
    public boolean active;

    public seller(String username, String password, String email, String name) {
        super(username, password, email, name, "seller");
        this.itemsSold = 0;
        this.active = true;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Items Sold: " + itemsSold);
        System.out.println("Active: " + (active ? "Yes" : "No"));
    }

    public boolean addToDB() {
        String query = "INSERT INTO Seller (username, password, email, name, itemsSold, active) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Ideally, hash the password before storing it
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, name);
            preparedStatement.setInt(5, itemsSold);
            preparedStatement.setBoolean(6, active);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Seller added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while adding seller to the database.");
            e.printStackTrace();
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
