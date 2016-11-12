package model;

import java.io.Serializable;
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
	
	/** Maximum number of auctions allowed inside the list. */
	public final int MAX_NUM_OF_AUCTIONS = 25;
	
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
	}
	
	/**
	 * 
	 * @param theAuction - An auction to be added in the list.
	 * @return True if auction was successfully added to the list. False otherwise.
	 */
	public boolean addAuction(final Auction theAuction) {
		boolean r = false;
		
		if (myAuctions.size() < MAX_NUM_OF_AUCTIONS) {
			myAuctions.add(theAuction);
			r = true;
		} 
		
		return r;
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