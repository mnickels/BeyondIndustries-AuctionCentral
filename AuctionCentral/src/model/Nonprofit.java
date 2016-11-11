/**
 * 
 */
package model;

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
	public Nonprofit(String theName, String theUsername, String theEmail, String thePhoneNumber) {
		super(theName, theUsername, theEmail, thePhoneNumber);
	}
	
	/**
	 * Sets the organization name of a Nonprofit account
	 * 
	 * @param theOrganizationName A string
	 */
	public void setOrganizationName(String theOrganizationName){
		myOrganizationName = theOrganizationName;
	}
	
	public void setLocalDateTime(){
		
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getEmail());
		sb.append('\n');
		sb.append(getName());
		sb.append('\n');
		sb.append(getPhoneNumber());
		sb.append('\n');
		sb.append(getUsername());
		sb.append('\n');
		sb.append(myLastAuctionDate);
		return sb.toString();
	}

}
