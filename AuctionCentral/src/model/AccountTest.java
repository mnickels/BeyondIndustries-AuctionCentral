package model;


import org.junit.Test;

/**
 * Tests constructor for account
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
	 * Tests construction of account, also includes staff and 
	 * nonprofit as they have the same constructor
	 */
	@Test
	public void testAccountConstructor() {
		myAccount = new Account("one", "two", "three", "four");
	}

}
