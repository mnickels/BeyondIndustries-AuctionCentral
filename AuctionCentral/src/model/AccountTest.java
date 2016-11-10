package model;


import org.junit.Test;

/**
 * Tests constructor for account and its subclasses.
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class AccountTest {
	
	/**
	 * Private account variable
	 */
	@SuppressWarnings("unused")
	private Account myAccount;
	
	/**
	 * Private bidder variable
	 */
	@SuppressWarnings("unused")
	private Bidder myBidder;

	/**
	 * Tests construction of account, also includes staff and 
	 * nonprofit as they have the same constructor
	 */
	@Test
	public void testAccountConstructor() {
		myAccount = new Account("one", "two", "three", "four");
	}
	
	/**
	 * Tests construction of bidder
	 */
	@Test
	public void testBidderConstructor() {
		myBidder = new Bidder("one", "two", "three", "four", "five");
	}

}
