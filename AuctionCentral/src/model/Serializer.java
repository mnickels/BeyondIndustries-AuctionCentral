package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;

/**
 * Reads and Writes Serializable Objects to and from files.
 * 
 * @author Matthew Subido
 * @version November 13 2016
 */
public final class Serializer {
	private Serializer(){}
	
	public static void writeFile(final Serializable theObject, final String theFileName) {
		try (
			OutputStream file = new FileOutputStream(theFileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
		){
			output.writeObject(theObject);
		}  
		catch(final IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Object readFile(final String theFileName) {
		Object obj = null;
		try(
			InputStream file = new FileInputStream(theFileName);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
		){
			obj = input.readObject();
		}
	    catch(final ClassNotFoundException ex){
	    	ex.printStackTrace();
	    }
		catch(final IOException ex){
			ex.printStackTrace();
		}
		return obj;
	}
	
	public static void main(String[] args) {
		
		Data.destroyInstance();
		
		Data d = Data.getInstance();
		
		for(int i = 0; i < 26; i++) {
			Account np = new Nonprofit("nonprofit" + i, "username", "email", "phonenumber", LocalDateTime.MIN, "FreePuppies");
			d.addUser(np.getName(), np);
		}
		
		Account nonp = new Nonprofit("nonprofitOneYear", "username", "email", "phonenumber", 
				LocalDateTime.now().minusYears(1), "FreePuppies");
		d.addUser(nonp.getName(), nonp);
		
		Account nonpr1 = new Nonprofit("nonprofitLessYear", "username", "email", "phonenumber", 
				LocalDateTime.now().minusYears(1).plusDays(1), "FreePuppies");
		d.addUser(nonpr1.getName(), nonpr1);
		
		for(int i = 0; i < 4; i++) {
			Account b = new Bidder("bidder" + i, "username", "email", "phonenumber", "address");
			d.addUser(b.getName(), b);
		}
		
		for(int i = 0; i < 3; i++) {
			Account s = new Staff("staff" + i, "username", "email", "phonenumber");
			d.addUser(s.getName(), s);
		}
		
		List<Auction> auctions = d.getAuctions();
		
		for (int i = 0; i < 22; i++) {
			
			Auction auc = new Auction((Nonprofit) d.getUser("nonprofit" + i), 
					d.getCurrentDateTime().plusWeeks(1).plusDays(i / 2), 
					"AuctionName" + i, "AuctionDescr");
			
			auc.addItem(new Item("Item 1", "Donor 1", 1, "new", "medium", "address", 
					BigDecimal.valueOf(50), "description"));
			
			auc.addItem(new Item("Item 2", "Donor 2", 1, "like new", "large", "address", 
					BigDecimal.valueOf(60), "description"));
			
			auc.addItem(new Item("Item 3", "Donor 3", 1, "used", "medium", "address", 
					BigDecimal.valueOf(70), "description"));
			
			
			auctions.add(auc);
		}
		
		Auction auc = new Auction((Nonprofit) d.getUser("nonprofit22"), 
				d.getCurrentDateTime().plusDays(2), 
				"AuctionName 22", "AuctionDescr");
		
		auc.addItem(new Item("Item 1", "Donor 1", 1, "new", "medium", "address", 
				BigDecimal.valueOf(50), "description"));
		
		auc.addItem(new Item("Item 2", "Donor 2", 1, "like new", "large", "address", 
				BigDecimal.valueOf(60), "description"));
		
		auc.addItem(new Item("Item 3", "Donor 3", 1, "used", "medium", "address", 
				BigDecimal.valueOf(70), "description"));
		
		
		auctions.add(auc);
		
		Auction auc1 = new Auction((Nonprofit) d.getUser("nonprofit23"), 
				d.getCurrentDateTime().plusDays(2), 
				"AuctionName 23", "AuctionDescr");
		
		auc1.addItem(new Item("Item 1", "Donor 1", 1, "new", "medium", "address", 
				BigDecimal.valueOf(50), "description"));
		
		auc1.addItem(new Item("Item 2", "Donor 2", 1, "like new", "large", "address", 
				BigDecimal.valueOf(60), "description"));
		
		auc1.addItem(new Item("Item 3", "Donor 3", 1, "used", "medium", "address", 
				BigDecimal.valueOf(70), "description"));
		
		auctions.add(auc1);
		
		
		
//		for (Auction a: d.getAuctions()) {
//			System.out.println(a.getName() + " " + a.getNonprofit().getName());
//		}
		
		writeFile(d, "24Auctions.ser");
		
		Data.destroyInstance();
		
		d = Data.getInstance();
		d.addUser("np364", new Nonprofit("Nonprofit", "Nonprofit", "nonprofit@email.com",
				"##########", d.currentDateTime.minusDays(364), "Some Kind of Org"));
		d.addUser("np365", new Nonprofit("Nonprofit2", "Nonprofit2", "nonprofit2@email.com",
				"##########", d.currentDateTime.minusDays(365), "Some Kind of Org"));
		d.addUser("np366", new Nonprofit("Nonprofit3", "Nonprofit3", "nonprofit3@email.com",
				"##########", d.currentDateTime.minusDays(366), "Some Kind of Org"));
		
		writeFile(d, "prevAuctions.ser");
	
		Data.destroyInstance();
		
		System.out.println("DONE");
		
	}
	
}
