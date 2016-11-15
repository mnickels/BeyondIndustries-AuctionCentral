package model.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import model.users.Nonprofit;

/**
 * Tests constructor for nonprofit.
 * 
 * @author Ian Richards
 * @version Nov.13.2016
 */
public class NonprofitTest {

	/**
	 * Private account variable
	 */
	@SuppressWarnings("unused")
	private Nonprofit myNonprofit;
	
	/**
	 * Private LocalDateTime variable
	 */
	private LocalDateTime myLocalDateTime;
	
	/**
	 * Private LocalDateTime variable
	 */
	private LocalDateTime myLocalDateTime2;
	
	/**
	 * Private LocalDateTime variable
	 */
	private LocalDateTime myLocalDateTime3;
	
	/**
	 * Private LocalDateTime variable
	 */
	private LocalDateTime myLocalDateTime4;

	/**
	 * Private LocalDateTime variable
	 */
	private LocalDateTime myLocalDateTime6;
	
	/**
	 * Private nonprofit account for testing
	 */
	private Nonprofit np;
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
		myLocalDateTime2 = LocalDateTime.now().minusYears(1);
		myLocalDateTime3 = LocalDateTime.now().minusYears(1).plusDays(1);
		myLocalDateTime4 = LocalDateTime.now().minusDays(1);
		np = new Nonprofit("John Smith", "jsmith", "jsmith@email.com", "2535550000",
				myLocalDateTime, "FreePuppies");
	}
	
	
	/**
	 * Tests nonprofit constructor
	 */
	@Test
	public void testNonprofitConstructor() {
		myNonprofit = new Nonprofit("one", "two", "three", "four", myLocalDateTime, "five");
	}
	
	/**
	 * Tests if nonprofit has had an auction outside of a year.
	 */
	@Test
	public void testIsAuctionWithinYearOverAYear() {
		np.setLastAuctionDate(myLocalDateTime2);
		assertFalse(np.isAuctionWithinYear());
	}
	
	/**
	 * Tests if nonprofit has had an auction exactly one year minus one day.
	 */
	@Test
	public void testIsAuctionWithinYearEqualsAYear() {
		np.setLastAuctionDate(myLocalDateTime3);
		assertTrue(np.isAuctionWithinYear());
	}
	
	/**
	 * Tests if nonprofit has had an auction within a year.
	 */
	@Test
	public void testIsAuctionWithinYearWithinAYear() {
		np.setLastAuctionDate(myLocalDateTime4);
		assertTrue(np.isAuctionWithinYear());
	}
	
	/**
	 * Tests if nonprofit has an auction.
	 */
	@Test
	public void testIsAuctionWithinYearNoAuction() {
		np.setLastAuctionDate(myLocalDateTime6);
		assertFalse(np.isAuctionWithinYear());
	}

}
