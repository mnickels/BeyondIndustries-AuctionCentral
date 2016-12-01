package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;

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
	private int maxAuctions = 25;
	
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
				&& auctionMoreThan2Day(theAuction) == false
				&& auctionExceedsMax(theAuction) == false
				&& auctionPlannedWeekAhead(theAuction) == false
				&& auctionExceedsOneMonthInFuture(theAuction) == false) {
				
			myAuctions.add(theAuction);
			
			r = true;
		} 
		
		return r;
	}
	
	/**
	 * Checks if there is already 2 actions on that specified day.
	 * 
	 * @param theAuction
	 * @return false if there is not 2 auctions on that day, true otherwise
	 */
	public boolean auctionMoreThan2Day(final Auction theAuction){
		if(getAuctionsForThisDay(theAuction.getDate().toLocalDate()).size() <= 1){
			return false;
		} else{
			return true;
		}
	}
	
	/**
	 * Checks if this auction planned more than one month ahead
	 * 
	 * @param theAuction
	 * @return false if the auction is planned within a month, true otherwise
	 */
	public boolean auctionExceedsOneMonthInFuture(final Auction theAuction){
		if(currentDateTime.toLocalDate().plusMonths(1).plusDays(1).isAfter(theAuction.getDate().toLocalDate())){
			return false;
		} else{
			return true;
		}
	}
	
	/**
	 * Checks if this auction planned at least one week in advance
	 * 
	 * @param theAuction
	 * @return false if the auction is planned at least a week ahead, true otherwise
	 */
	public boolean auctionPlannedWeekAhead(final Auction theAuction){
		if(theAuction.getDate().toLocalDate().isAfter(currentDateTime.toLocalDate().plusWeeks(1).minusDays(1))){
			return false;
		} else{
			return true;
		}
	}
	
	/**
	 * Checks if adding this auction will exceed the max number of auctions
	 * 
	 * @param theAuction
	 * @return false if it doesnt exceed, true otherwise
	 */
	public boolean auctionExceedsMax(final Auction theAuction){
		if(totalNumberOfUpcomingAuctions() < maxAuctions){
			return false;
		} else{
			return true;
		}
	}
	
	/**
	 * 
	 * Removes (cancels) an auction from the system according to the business rules. If all business rules for
	 * canceling an auction are satisfied, the auction is canceled (removed) from the list.
	 * 
	 * @param theAuction - An auction to be removed (canceled) from the list.
	 * @return True if auction was successfully removed (canceled) from the list. False otherwise.
	 */
	public boolean removeAuction(final Auction theAuction) {
		boolean r = false;
		int i = 0;
		//for every auction in the system
		for(Auction a : myAuctions) {
			//check if the auction that's about to be removed its nonprofit already exists in the system
			//and if the current date is 2 days before the auction scheduled date.
			if (theAuction.getNonprofit().getName().equals(a.getNonprofit().getName())
					&& currentDateTime.isBefore(theAuction.getDate().minusDays(2))) {
				myAuctions.remove(i);
				r = true;
				break;
			}
			i++;
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
	public int totalNumberOfUpcomingAuctions() { 
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
	 * @return the Items bid upon by the given Bidder, null if Bidder has not bid.
	 */
	public Map<Auction, List<Item>> getItemsForBidder(final Bidder theBidder) {
		Map<Auction, List<Item>> items = new HashMap<Auction, List<Item>>();
		
		for (final Auction a : myAuctions) {
			List<Item> l = new ArrayList<Item>();
			for (final Item i : a.getItems()) {
				if (i.checkBidder(theBidder)) {
					l.add(i);
				}
			}
			if (!l.isEmpty()) {
				items.put(a, l);
			}
		}
		
		return items;
	}
	
	/**
	 * Gets the max number of auctions that can be scheduled at one time.
	 * @return The max number of auctions that can be scheduled at one time.
	 * @author Mike Nickels | mnickels@uw.edu
	 */
	public int getMaxNumberOfAuctions() {
		return maxAuctions;
	}
	
	/**
	 * Sets the max number of auctions that can be scheduled at one time.
	 * @author Mike Nickels | mnickels@uw.edu
	 */
	public void setMaxNumberOfAuctions(int newMax) {
		if (newMax > 0)
			maxAuctions = newMax;
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
	
	public Object[][] getAuctionItems(Nonprofit theNonprofit) {
		Auction currentAuction = Data.getInstance().getAuctionForThisNonprofit((Nonprofit) theNonprofit);
		List<Item> tempItemList = currentAuction.getItems();
		Object[][] resultItemList = new Object[tempItemList.size()][6];
		for (int i = 0; i < tempItemList.size(); i++) {
			Item tempItem = tempItemList.get(i);
			resultItemList[i][0] = tempItem.getName();
			resultItemList[i][1] = tempItem.getDonor();
			resultItemList[i][2] = tempItem.getQuantity();
			resultItemList[i][3] = tempItem.getCondition();
			resultItemList[i][4] = tempItem.getSize();
			resultItemList[i][5] = tempItem.getStartingBid();
			
		}
		return resultItemList;
		
	}
}