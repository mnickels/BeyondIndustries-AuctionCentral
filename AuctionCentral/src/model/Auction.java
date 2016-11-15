package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.users.Bidder;
import model.users.Nonprofit;

/**
 * The Auction class stores all the information of an auction, which includes
 * the nonprofit info., date of auction, name of auction, description, and list
 * of the items from the auction. When an item is added to the auction, it will
 * make sure no same item will appear twice in the auction item list. When an item
 * is removed, it will make sure the item exists in the list in order to be removed.
 * 
 * @author Ming Lam
 * @version 11/09/2016
 */
public class Auction implements Serializable{

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The info. of the nonprofit
	 */
	private Nonprofit myNonprofit;
	
	/**
	 * A list of the items
	 */
	private List<Item> myItems;
	
	/**
	 * The date of the auction
	 */
	private LocalDateTime myDate;
	
	/**
	 * The name of the auction
	 */
	private String myName;
	
	/**
	 * The number of items in the auction
	 */
	private int size;
	
	/**
	 * The auction's description
	 */
	private String myDescription;
	
	/**
	 * The constructor of the Auction class
	 * 
	 * @param theNonprofit info. of the nonprofit
	 * @param theDate date of the auction
	 * @param theName name of the auction
	 * @param theDescription aution's description
	 */
	public Auction(Nonprofit theNonprofit, LocalDateTime theDate, String theName, String theDescription) {
		myNonprofit = theNonprofit;
		myDate = theDate;
		myName = theName;
		myDescription = theDescription;
		
		myItems = new ArrayList<Item>();
		size = 0;
	}
	
	/**
	 * get the nonprofit info
	 * 
	 * @return myNonprofit
	 */
	public Nonprofit getNonprofit() {
		return myNonprofit;
	}
	
	/**
	 * set the nonprofit info
	 * 
	 * @param theNonprofit nonprofit info
	 */
	public void setNonprofit(Nonprofit theNonprofit) {
		myNonprofit = theNonprofit;
	}
	
	/**
	 * get the date of the auction
	 * 
	 * @return myDate
	 */
	public LocalDateTime getDate() {
		return myDate;
	}
	
	/**
	 * set the date of the auction
	 * 
	 * @param theDate date of auction
	 */
	public void setDate(LocalDateTime theDate) {
		myDate = theDate;
	}
	
	/**
	 * get the name of the auction
	 * 
	 * @return myName
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * set the name of the auction
	 * 
	 * @param theName name of auction
	 */
	public void setName(String theName) {
		myName = theName;
	}
	
	/**
	 * get the auction's description
	 * 
	 * @return myDescription
	 */
	public String getDescription() {
		return myDescription;
	}
	
	/**
	 * set the auction's description
	 * 
	 * @param theDescription auction's description
	 */
	public void setDescription(String theDescription) {
		myDescription = theDescription;
	}
	
	/**
	 * get the list of items that are in the auction
	 * 
	 * @return myItems
	 */
	public List<Item> getItems() {
		return myItems;
	}
	
	/**
	 * get the number of items in the auction 
	 * 
	 * @return size;
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * add a new item to the auction
	 * 
	 * @param theItem new item to be added
	 */
	public boolean addItem(Item theItem) {
		boolean result = true;
		
		for (int i = 0; i < size; i++) {
			if (theItem.getName().equals(myItems.get(i).getName())) {
				result = false;
				break;
			}
		}
		
		if (result) {
			myItems.add(theItem);
			size++;
		}
		
		return result;
	}
	
	/**
	 * remove an item from the auction
	 * 
	 * @param theItem the item that will be removed
	 */
	public boolean removeItem(Item theItem) {
		boolean result = false;
		
		for (int i = 0; i < size; i++) {
			if (theItem.getName().equals(myItems.get(i).getName())) {
				myItems.remove(i);
				size--;
				result = true;
				break;
			}
		}
		

		return result;
	}
	
	public String toString() {
		String result = "Auction: ";
		result += myName;
		result += "\nOrganization: ";
		result += myNonprofit.getOrganizationName();
		result += "\nDate: ";
		result += myDate;
		result += "\nNumber of items: ";
		result += size;
		result += "\nDescription: ";
		result += myDescription;
		
		return result;
		
	}
	
	public boolean isAuctionAvailable(LocalDateTime theDate) {
		boolean result = true;
		
		if (theDate.isEqual(myDate) || theDate.isAfter(myDate)) {
			result = false;
		}
		return result;
		
	}
	
}
