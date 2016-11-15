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
		
		Data d = Data.getInstance();
		
		for(int i = 0; i < 26; i++) {
			Account np = new Nonprofit("Nonprofit " + i, "username", "email", "phonenumber", LocalDateTime.MIN, "FreePuppies");
			d.addUser(np.getName(), np);
		}
		
		for(int i = 0; i < 4; i++) {
			Account b = new Bidder("Bidder " + i, "username", "email", "phonenumber", "address");
			d.addUser(b.getName(), b);
		}
		
		for(int i = 0; i < 3; i++) {
			Account s = new Staff("Staff " + i, "username", "email", "phonenumber");
			d.addUser(s.getName(), s);
		}
		
		for(int i = 0; i < 24; i++) {
			
			Auction auc = new Auction((Nonprofit) d.getUser("Nonprofit " + i), 
					d.getCurrentDateTime().plusWeeks(1).plusDays(i), 
					"AuctionName" + i, "AuctionDescr");
			
			auc.addItem(new Item("Item 1", "Donor 1", 1, "new", "medium", "address", 
					BigDecimal.valueOf(50), "description"));
			
			auc.addItem(new Item("Item 2", "Donor 2", 1, "like new", "large", "address", 
					BigDecimal.valueOf(50), "description"));
			
			auc.addItem(new Item("Item 3", "Donor 3", 1, "used", "medium", "address", 
					BigDecimal.valueOf(50), "description"));
			
			
			d.addAuction(auc);
			
			
		}
		
		for(Auction a: d.getAuctions()) {
			System.out.println(a.getName() + " " + a.getNonprofit().getName());
		}
		
		writeFile(d, "dataInstance24Auctions.ser");
		
		System.out.println("Done!");
		
	}
	
}
