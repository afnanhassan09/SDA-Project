package Controller;

import DBConnection.dbController;
import Project.item;
import Project.itemDescription;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class itemManager {
    Connection connection;
    PreparedStatement preparedStatement;

    public itemManager() {
        this.connection = dbController.getConnection();
    }

    public boolean addItem(String ItemID, String ItemName, int ItemQuantity, double price, String description, String sellerID) {
        item i = new item(ItemID, ItemName, ItemQuantity, price, sellerID);
        itemDescription discription = new itemDescription(ItemID, ItemName, description);
        PreparedStatement preparedStatement = null;
        try {
            connection = dbController.getConnection();

            // Include sellerID in the query
            String query = "INSERT INTO Items (itemID, ItemName, ItemQuantity, Price, sellerID) VALUES (?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, ItemID);
            preparedStatement.setString(2, ItemName);
            preparedStatement.setInt(3, ItemQuantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, sellerID); // Bind the sellerID parameter

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Item inserted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error while inserting data.");
            e.printStackTrace();
            return false;
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
        return true;
    }

    public ArrayList<item> getAllItems() {
        String query = "SELECT * FROM items";
        ArrayList<item> itemList = new ArrayList<>();

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String itemID = resultSet.getString("ItemID");
                String itemName = resultSet.getString("ItemName");
                int itemQuantity = resultSet.getInt("ItemQuantity");
                double price = resultSet.getDouble("price");
                String sellerID = resultSet.getString("sellerID"); // Retrieve sellerID

                item i = new item(itemID, itemName, itemQuantity, price, sellerID);
                itemList.add(i);
            }

            if (itemList.isEmpty()) {
                System.out.println("No items found.");
            } else {
                System.out.println("Item List:");
                for (item i : itemList) {
                    System.out.println(i);
                }
                return itemList;
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve items from the database.");
            e.printStackTrace();
        }

        return itemList;
    }

    public item getItem(String itemID) {
        String query = "SELECT * FROM items WHERE itemID =?";
        item item = null;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, itemID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                item = new item(
                    resultSet.getString("ItemID"),
                    resultSet.getString("ItemName"),
                    resultSet.getInt("ItemQuantity"),
                    resultSet.getDouble("price"),
                    resultSet.getString("sellerID") // Include sellerID
                );
                System.out.println(item);
            } else {
                System.out.println("No item found with ID: " + itemID);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve item from the database.");
            e.printStackTrace();
        }

        return item;
    }

    public boolean updateItemQuantity(String itemID, int quantity) {
        String query = "UPDATE items SET ItemQuantity = ? WHERE ItemID = ?";
        boolean isUpdated = false;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, itemID);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Item quantity updated successfully!");
                isUpdated = true;
            } else {
                System.out.println("No item found with ID: " + itemID);
            }
        } catch (SQLException e) {
            System.err.println("Failed to update item quantity.");
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

        return isUpdated;
    }

    public boolean updateItemPrice(String itemID, double newPrice) {
        String query = "UPDATE items SET Price = ? WHERE ItemID = ?";
        boolean isUpdated = false;

        try {
            connection = dbController.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, itemID);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Item price updated successfully!");
                isUpdated = true;
            } else {
                System.out.println("No item found with ID: " + itemID);
            }
        } catch (SQLException e) {
            System.err.println("Failed to update item price.");
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

        return isUpdated;
    }
}
