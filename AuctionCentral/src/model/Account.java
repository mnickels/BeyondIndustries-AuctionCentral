package model;

import java.io.Serializable;

/**
 * A superclass for accounts within AuctionCentral
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class Account implements Serializable{
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Private name of the person holding the account
	 */
	private String myName;
	
	/**
	 * Private username of the account holder
	 */
	private String myUsername;
	
	/**
	 * Private email of the account holder
	 */
	private String myEmail;
	
	/**
	 * Private phone number of the account holder
	 */
	private String myPhoneNumber;
	
	/**
	 * Creates an account which is a superclass for the types of account within AuctionCentral 
	 * 
	 * @param theName A string
	 * @param theUsername A string
	 * @param theEmail A string
	 * @param thePhoneNumber A string
	 */
	public Account(String theName, String theUsername, String theEmail, String thePhoneNumber){
		myName = theName;
		myUsername = theUsername;
		myEmail = theEmail;
		myPhoneNumber = thePhoneNumber;
	}
	
	/**
	 * Returns the name of the account holder
	 * 
	 * @return A string
	 */
	public String getName(){
		return myName;
	}
	
	/**
	 * returns the username of the account holder
	 * 
	 * @return A string
	 */
	public String getUsername(){
		return myUsername;
	}
	
	/**
	 * Returns the email of the account holder
	 * 
	 * @return A string
	 */
	public String getEmail(){
		return myEmail;
	}
	
	/**
	 * Returns the phone number of the account holder
	 * 
	 * @return A string
	 */
	public String getPhoneNumber(){
		return myPhoneNumber;
	}
	
	/**
	 * Sets the name of the account holder
	 */
	public void setName(final String theName){
		myName = theName;
	}
	
	/**
	 * Sets the email for the account
	 */
	public void setEmail(final String theEmail){
		myEmail = theEmail;
	}
	
	/**
	 * Sets the phone number of the account
	 */
	public void setPhoneNumber(final String thePhoneNumber){
		myPhoneNumber = thePhoneNumber;
	}
	

}
