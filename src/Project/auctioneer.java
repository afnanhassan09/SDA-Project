package Project;

import DBConnection.dbController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class auctioneer extends user {
    public int salary;
    public int level;

    public auctioneer(String username, String password, String email, String name, int salary, int level) {
        super(username, password, email, name, "auctioneer");
        this.salary = salary;
        this.level = level;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Salary: " + salary);
        System.out.println("Level: " + level);
    }

    public boolean addToDB() {
        String query = "INSERT INTO Auctioneer (username, password, email, name, salary, level) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Ideally, hash the password before storing it
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, name);
            preparedStatement.setInt(5, salary);
            preparedStatement.setInt(6, level);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Auctioneer added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while adding auctioneer to the database.");
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
