package Controller;
import Project.item;
import java.time.LocalDate;
public class AuctionRegister {

    public static void main(String[] args) {
    	itemManager manager = new itemManager();
    	bidderManager bidder = new bidderManager();
    	sellerManager seller = new sellerManager();
    	auctioneerManager auctioneer = new auctioneerManager();
    	auctionManager auctionManager = new auctionManager();
    	bidManager bidManager = new bidManager();
    	
    	
    	
//    	auctioneer.addAuctioneer("this", "123", "this@gmail.com", "thsiMan", 1000, 1);
//    	System.out.println(auctioneer.login("this", "123"));
    	manager.addItem("1234", "Doll", 9, 9.9, "This is a very good product please buy it", "this");
//    	manager.getAllItems();
//    	manager.getItem("1234");
//    	auctionManager.addAuction("A123", LocalDate.of(2024, 12, 25), 100);
//    	auctionManager.getAuctionList();
    	bidManager.addBid("bid1", "A123", "1234", 100.0);

    }
}
