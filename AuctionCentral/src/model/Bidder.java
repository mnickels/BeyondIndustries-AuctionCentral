/**
 * 
 */
package model;

/**
 * Bidder subclass that creates an account for a bidder
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class Bidder extends Account {
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * private address of a bidder
	 */
	private String myAddress;

	/**
	 * Creates a bidder requiring an address in addition to accounts other requirements.
	 * 
	 * @param theAddress
	 * @param theName
	 * @param theUsername
	 * @param theEmail
	 * @param thePhoneNumber
	 */
	public Bidder(String theName, String theUsername, String theEmail, String thePhoneNumber, String theAddress) {
		super(theName, theUsername, theEmail, thePhoneNumber);
		myAddress = theAddress;
	}
	
	/**
	 * Returns the address of the bidder
	 * 
	 * @return A string representing a address
	 */
	public String getAddress(){
		return myAddress;
	}
	
	/**
	 * Sets the address of a bidder
	 * 
	 * @param theAddress A string
	 */
	public void setAddress(String theAddress){
		myAddress = theAddress;
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
		sb.append(getAddress());
		return sb.toString();
	}

}
