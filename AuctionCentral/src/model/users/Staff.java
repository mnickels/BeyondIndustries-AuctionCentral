/**
 * 
 */
package model.users;

/**
 * Staff subclass that creates an account for a staff member
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class Staff extends Account {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a staff member account
	 * 
	 * @param theName A string
	 * @param theUsername A string
	 * @param theEmail A string
	 * @param thePhoneNumber A string
	 */
	public Staff(String theName, String theUsername, String theEmail, String thePhoneNumber) {
		super(theName, theUsername, theEmail, thePhoneNumber);
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
		return sb.toString();
	}

}
