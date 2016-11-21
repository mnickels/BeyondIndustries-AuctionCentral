package model.tests;

import static org.junit.Assert.*;

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
 * @version November 9 2016
 */
public class ItemTest {
	
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
	public void testAddBidOnAlreadyBid() {
		myItem.addBid(myBidder, new BigDecimal(200));
		BigDecimal test = new BigDecimal(300);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertFalse(theCheck.get(myBidder).equals(test));
	}
	
	@Test
	public void testAddBidOnYetToBid() {
		BigDecimal test = new BigDecimal(300);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertTrue(theCheck.get(myBidder).equals(test));
	}
	
	@Test
	public void testAddBidOnZeroBid() {
		myItem.addBid(myBidder, BigDecimal.ZERO);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertFalse(theCheck.keySet().equals(myCheck.keySet()));
	}
	
	@Test
	public void testAddBidOnNegativeBid() {
		BigDecimal test = new BigDecimal(-1);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertFalse(theCheck.keySet().equals(myCheck.keySet()));
	}

	@Test
	public void testAddBidOnPositiveBidUnderStartingBid() {
		BigDecimal test = new BigDecimal(99.99);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertFalse(theCheck.keySet().equals(myCheck.keySet()));
	}
	

	@Test
	public void testAddBidOnExactlyStartingBid() {
		BigDecimal test = new BigDecimal(100);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertTrue(theCheck.keySet().equals(myCheck.keySet()));
	}
	

	@Test
	public void testAddBidOnAboveStartingBid() {
		BigDecimal test = new BigDecimal(100.01);
		myItem.addBid(myBidder, test);
		Map<Bidder, BigDecimal> theCheck = myItem.getBids();
		assertTrue(theCheck.keySet().equals(myCheck.keySet()));
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
