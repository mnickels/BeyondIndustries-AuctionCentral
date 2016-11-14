/**
 * 
 */
package model.users;

import java.time.LocalDateTime;

/**
 * Nonprofit subclass that creates an account for the representative of an organization
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class Nonprofit extends Account {
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A localdatetime of the Nonprofit's auction
	 */
	private LocalDateTime myLastAuctionDate;
	
	/**
	 * Private string of the organization name of this Nonprofit's account
	 */
	private String myOrganizationName;

	/**
	 * Creates a Nonprofit account
	 * 
	 * @param theName A string
	 * @param theUsername A string
	 * @param theEmail A string
	 * @param thePhoneNumber A string
	 */
	public Nonprofit(String theName, String theUsername, String theEmail, String thePhoneNumber, LocalDateTime theLastAuctionDate
			, String theOrganizationName) {
		super(theName, theUsername, theEmail, thePhoneNumber);
		myLastAuctionDate = theLastAuctionDate;
		myOrganizationName = theOrganizationName;
	}
	
	/**
	 * Sets the organization name of a Nonprofit account
	 * 
	 * @param theOrganizationName A string
	 */
	public void setOrganizationName(String theOrganizationName){
		myOrganizationName = theOrganizationName;
	}
	
	public void setLocalDateTime(LocalDateTime theLastAuctionDate){
		myLastAuctionDate = theLastAuctionDate;
	}
	
	/**
	 * returns a string representing the Nonprofit's organization
	 * 
	 * @return A string
	 */
	public String getOrganizationName(){
		return myOrganizationName;
	}
	
	/**
	 * returns the local date time of my auction as a way of identification
	 * 
	 * @return LocalDateTime
	 */
	public LocalDateTime getLastAuctionDate(){
		return myLastAuctionDate;
	}
	
	/**
	 * Checks if this nonprofit's last auction is within a year of the current date. To determine if a 
	 * new auction can be made for this nonprofit
	 * 
	 * @return true if auction is within a year, false otherwise.
	 */
	public boolean isAuctionWithinYear(){
		LocalDateTime myCurrentDate;
		myCurrentDate = LocalDateTime.now();
		//myCurrentDate.minusYears(1); Object is immutable, this line does not change myCurrentDate's year
		
		/* If last auction for this nonprofit is within the range of current date 
		 * minus 364 days or is above the current date which means there is no past 
		 * auctions for this nonprofit, then this nonprofit has no past auctions
		 * within the last year minus one day ago (364 days ago). Here we are also checking
		 * if last auction is above the current date for case if nonprofit has no past auctions.
		 * When a new nonprofit object is contructed, myLastAuctionDate field should be assigned to
		 * the same date as the date of his auction to be.
		 */
		
		return !(myLastAuctionDate.isBefore(myCurrentDate.minusYears(1).plusDays(1)) 
				|| (myLastAuctionDate.isAfter(myCurrentDate)));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contact person of " + getOrganizationName());
		return sb.toString();
	}

}
