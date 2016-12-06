package model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import model.Item;
import model.users.Bidder;

/**
 * @author Matthew Subido
 * @author Mike Nickels | mnickels@uw.edu
 * @version December 5 2016
 */
public class CancelBidTest {
	
	private Item myItem;
	
	private Bidder myBidder;
	
	private Map<Bidder, BigDecimal> myCheck;
	
	private LocalDateTime myValidTime;
	
	@Before
	public void setUp() {
		myItem = new Item("Cake", "Lady Grey", 1, "New", "Large", "1234 Cherry Road",
				new BigDecimal(100), "White Frosting with No Frills");
		myBidder = new Bidder("Maxine Caulfield", "janedoe", "email@email", "1234567890",
				"Arcadia Bay");
		myCheck = new HashMap<Bidder, BigDecimal>();
		myCheck.put(myBidder, new BigDecimal(200));
		myValidTime = LocalDateTime.now().plusDays(3);
	}
	
	@Test
	public void testRemoveBidOnNonExistantBid() {
		assertEquals(null, myItem.removeBid(myBidder, myValidTime));
		assertEquals(new HashMap<Bidder, BigDecimal>(), myItem.getBids());
	}
	
	@Test
	public void testRemoveBidOnExistingBid() {
		BigDecimal bid = new BigDecimal(200);
		myItem.addBid(myBidder, bid);
		assertEquals(bid, myItem.removeBid(myBidder, myValidTime));
		assertEquals(new HashMap<Bidder, BigDecimal>(), myItem.getBids());
	}

	@Test
	public void testRemoveBidOnAuctionMoreThanTwoDaysOut() {
		BigDecimal bid = new BigDecimal(200);
		myItem.addBid(myBidder, bid);
		assertEquals(bid, myItem.removeBid(myBidder, myValidTime));
		assertEquals(new HashMap<Bidder, BigDecimal>(), myItem.getBids());
	}

	@Test
	public void testRemoveBidOnAuctionExactlyTwoDaysOut() {
		LocalDateTime twoDays = LocalDateTime.now().plusDays(2);
		BigDecimal bid = new BigDecimal(200);
		myItem.addBid(myBidder, bid);
		assertNull(myItem.removeBid(myBidder, twoDays));
		Map<Bidder, BigDecimal> check = new HashMap<Bidder, BigDecimal>();
		check.put(myBidder, bid);
		assertEquals(check, myItem.getBids());
	}

	@Test
	public void testRemoveBidOnAuctionLessThanTwoDaysOut() {
		LocalDateTime oneDay = LocalDateTime.now().plusDays(1);
		BigDecimal bid = new BigDecimal(200);
		myItem.addBid(myBidder, bid);
		assertNull(myItem.removeBid(myBidder, oneDay));
		Map<Bidder, BigDecimal> check = new HashMap<Bidder, BigDecimal>();
		check.put(myBidder, bid);
		assertEquals(check, myItem.getBids());
	}

	@Test
	public void testRemoveBidOnAuctionToday() {
		LocalDateTime today = LocalDateTime.now();
		BigDecimal bid = new BigDecimal(200);
		myItem.addBid(myBidder, bid);
		assertNull(myItem.removeBid(myBidder, today));
		Map<Bidder, BigDecimal> check = new HashMap<Bidder, BigDecimal>();
		check.put(myBidder, bid);
		assertEquals(check, myItem.getBids());
	}

}
