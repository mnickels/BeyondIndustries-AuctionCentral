package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.users.Account;

/**
 * The Data class is used as a data placeholder. It has a list of all Auctions in the system, and also 
 * a set of all users in the system. This class can be instantiated only once which means
 * there can only be one object of this type.
 * 
 * @author Gjorgi Stojanov
 * @version November 11 2016
 */
public final class Data implements Serializable {
	
	/** Instance field for the class. */
	private static Data instance = null;
	
	/** Default serial ID. */
	private static final long serialVersionUID = 1L;
	
	/** Maximum number of upcoming auctions. */
	public final int MAX_NUM_OF_UPCOMING_AUCTIONS = 25;
	
	/** ID for each item */
	public static int itemID;
	
	/** Current date and time for the system */
	public LocalDateTime currentDateTime;
	
	/** List of all auction in the system. */
	private List<Auction> myAuctions;
	
	/** Set of all users in the system. */
	private Map<String, Account> myUsers;
	
	/**
	 * Private constructor which prevents the class to be directly instantiated.
	 */
	private Data() {
		myAuctions = new ArrayList<Auction>();
		myUsers = new HashMap<String, Account>();
		currentDateTime = LocalDateTime.now();
		itemID = 0;
	}
	
	/**
	 * 
	 * Adds an auction to the system according to the business rules. If all business rules for
	 * adding an auction are satisfied, the auction is added.
	 * 
	 * @param theAuction - An auction to be added in the list.
	 * @return True if auction was successfully added to the list. False otherwise.
	 */
	public boolean addAuction(final Auction theAuction) {
		boolean r = false;
		
		if ((!nonprofitHasFutureAuction(theAuction.getNonprofit())) 
				&& (!theAuction.getNonprofit().isAuctionWithinYear())
				&& getAuctionsForThisDay(theAuction.getDate().toLocalDate()).size() <= 1
				&& (totalNumberOfUpcommingAuctions() < MAX_NUM_OF_UPCOMING_AUCTIONS)
				&& currentDateTime.toLocalDate().plusMonths(1).plusDays(1).isAfter(theAuction.getDate().toLocalDate())
				&& theAuction.getDate().toLocalDate().isAfter(currentDateTime.toLocalDate().plusWeeks(1).minusDays(1))) {
				
			myAuctions.add(theAuction);
			
			r = true;
		} 
		
		return r;
	}
	
	/**
	 * 
	 * Checks if a specific nonprofit has future auction in the system
	 * 
	 * @param theNonprofit - A nonprofit to check
	 * @return True if this nonprofit has future auction, false otherwise
	 */
	public boolean nonprofitHasFutureAuction(final Account theNonprofit) {
		boolean result = false;
		for(Auction a : myAuctions) {
			if (a.getDate().isAfter(currentDateTime) 
					&& theNonprofit.getName().equals(a.getNonprofit().getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Returns the list of all auctions.
	 * 
	 * @return A pointer to the list of auctions.
	 */
	public List<Auction> getAuctions() {
		return myAuctions;
	}
	
	/**
	 * Adds new user to the system.
	 * 
	 * @param theUser - The name of the user as a String
	 * @param theAccount - The account type of the user as an Account
	 */
	public void addUser(final String theUser, final Account theAccount) {
		myUsers.put(theUser, theAccount);
	}
	
	/**
	 * Returns back a user from the set of all users
	 * 
	 * @param theUser - The name of the user
	 * @return An Account representing the user
	 */
	public Account getUser(final String theUser) {
		Account result;
		result = myUsers.get(theUser);
		return result;
	}
	
	/**
	 * Returns the current itemID with a post-increment.
	 * 
	 * @return the current itemID
	 */
	public static int getItemID() {
		return itemID++;
	}
	
	/**
	 * 
	 * Checks if the specified nonprofit has an registered auction in the system.
	 * This method checks all auctions, past or future.
	 * 
	 * @param theNonprofit - A nonprofit to check if it has a auction registered.
	 * @return True if the specified nonprofit has any kind of auciton (past or future), false otherwise.
	 */
	public boolean nonprofitHasAuction(final Account theNonprofit) {
		boolean result = false;
		for(Auction a : myAuctions) {
			if (a.getNonprofit().getName().equals(theNonprofit.getName())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * Returns an Auction object for the specified nonprofit.
	 * 
	 * @param theNonprofit - A nonprofit.
	 * @return an Auction object related to the specified nonprofit.
	 */
	public Auction getAuctionForThisNonprofit(final Account theNonprofit) {
		Auction result = null;
		for(Auction a: myAuctions) {
			if (a.getNonprofit().getName().equals(theNonprofit.getName())) {
				result = a;
				break;
			}
		}

		return result;
	}
	
	
	/**
	 * 
	 * Gets list of auction(s) for the specified day
	 * 
	 * @param theDate - A LocalDate argument representing the day for which auctions will be retrieved
	 * @return A list of auction(s) for the specified day
	 */
	public List<Auction> getAuctionsForThisDay(final LocalDate theDate) {
		List<Auction> result = new ArrayList<Auction>();
		for (Auction a: myAuctions) {
			
			if (a.getDate().toLocalDate().isEqual(theDate)) {
				result.add(a);
			}
		}
		return result;
	}
	
	/**
	 * Gets the total number of upcoming auctions relative to the currentDateTime field, i.e.
	 * all auctions that are scheduled after the currentDateTime field.
	 * 
	 * @return An integer representing the total number of upcoming auctions.
	 */
	public int totalNumberOfUpcommingAuctions() { 
		int counter = 0;
		for(Auction a : myAuctions) {
			if (a.getDate().isAfter(currentDateTime) || 
					a.getDate().toLocalDate().isEqual(currentDateTime.toLocalDate())) {
				counter++;
			}
		}
		
		return counter;
	}
	
	/**
	 * Gets the current date time for the system
	 * 
	 * @return A LocalDateTime object representing the current date and time for the system.
	 */
	public LocalDateTime getCurrentDateTime() {
		return currentDateTime;
	}
	
	/**
	 * Sets the current date and time for the system.
	 * 
	 * @param theDateTime - A date and time for which the current date and time of the system will be set to
	 */
	public void setCurrentDateTime(final LocalDateTime theDateTime) {
		/*currentDateTime = LocalDateTime.of(theDateTime.getYear(), theDateTime.getMonth(),
				theDateTime.getDayOfMonth(), theDateTime.getHour(), theDateTime.getMinute());
			*/
		
		currentDateTime = theDateTime;
	}
	
	/**
	 * Gets a single instance of the class
	 * 
	 * @return A instance of the class
	 */
	public static Data getInstance() {
		if (instance == null) {
			instance = new Data();
		}
		return instance;
	}
	
	public static void setInstance(Data theData) { 
		instance = theData; 
	}
	
	/**
	 * Destroys the current instance
	 * 
	 * @return null
	 */
	public static Data destroyInstance() {
		if (instance != null) {
			instance = null;
		}
		
		return instance;
	}
	
}