package model.tests;

import org.junit.Test;

import model.users.Bidder;

/**
 * Tests constructor for bidder.
 * 
 * @author Ian Richards
 * @version Nov.9.2016
 */
public class BidderTest {

	/**
	 * Private account variable
	 */
	@SuppressWarnings("unused")
	private Bidder myBidder;
	
	/**
	 * Tests construction of bidder
	 */
	@Test
	public void testBidderConstructor() {
		myBidder = new Bidder("one", "two", "three", "four", "five");
	}

}
