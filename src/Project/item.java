package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBConnection.dbController;

public class item {
	String ItemID;
	String ItemName;
	int ItemQuantity;
	double price;
	String sellerID;
	
	public item(String ItemID, String ItemName, int ItemQuantity, double price, String sellerID) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.ItemQuantity = ItemQuantity;
        this.price = price;
        this.sellerID = sellerID;
    }
	
	public void priceUpdate(float price)
	{
		this.price = price;
	}
}
