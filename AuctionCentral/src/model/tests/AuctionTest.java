package model.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Item;
import model.users.Nonprofit;

/**
 * 
 * @author Ming Lam
 * @version 11/09/2016
 */
public class AuctionTest {

	Auction myAuction;
	
	Nonprofit myNonprofit;
	
	Item myItem;
	
	LocalDateTime myLocalDateTime;
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
		myItem = new Item("Onion Ring", "Mrs. Field", 10, "New", "Small", "999 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened.");
		myNonprofit = new Nonprofit("Santa Claus", "sclaus", "scalus@email", "2532532532",
				myLocalDateTime, "FreeDogs");

		LocalDateTime date = LocalDateTime.of(2016, 11, 9, 11, 00);
		myAuction = new Auction(myNonprofit, date, "Onion Auction", "This is a boring auction that only sell onion rings.");
	}

	@Test
	public void testGetNonprofit() {
		assertEquals(myNonprofit, myAuction.getNonprofit());
	}

	@Test
	public void testSetNonprofit() {
		Nonprofit  testNonprofit = new Nonprofit("Harry Potter", "hpotter", "hpotter@email", "2532532532",
				myLocalDateTime, "FreeCats");
		myAuction.setNonprofit(testNonprofit);
		assertEquals(testNonprofit, myAuction.getNonprofit());
	}

	@Test
	public void testGetDate() {
		LocalDateTime testDate = LocalDateTime.of(2016, 11, 9, 11, 00);
		assertEquals(testDate, myAuction.getDate());
	}

	@Test
	public void testSetDate() {
		LocalDateTime testDate = LocalDateTime.of(2017, 10, 10, 11, 00);
		myAuction.setDate(testDate);
		assertEquals(testDate, myAuction.getDate());
	}

	@Test
	public void testGetName() {
		assertEquals("Onion Auction", myAuction.getName());
	}

	@Test
	public void testSetName() {
		myAuction.setName("Potato Auction");
		assertEquals("Potato Auction", myAuction.getName());
	}

	@Test
	public void testGetDescription() {
		assertEquals("This is a boring auction that only sell onion rings.", myAuction.getDescription());
	}

	@Test
	public void testSetDescription() {
		myAuction.setDescription("This is an interesting auction that only sell potato.");
		assertEquals("This is an interesting auction that only sell potato.", myAuction.getDescription());
	}


	@Test
	public void testGetSize() {
		assertEquals(0, myAuction.getSize());
	}

	public void testAddItem() {
		assertTrue(myAuction.addItem(myItem));
	}
	
	@Test
	public void testAddItemOnSameItemTwice() {
		myAuction.addItem(myItem);
		
		Item testItem = new Item("Onion Ring", "Mickey Mouse", 5, "New", "Small", "111 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened."); 
		assertFalse(myAuction.addItem(testItem));
	}

	@Test
	public void testRemoveItem() {
		myAuction.addItem(myItem);

		Item testItem = new Item("Onion Ring", "Mrs. Field", 10, "New", "Small", "999 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened.");
		assertTrue(myAuction.removeItem(testItem));

	}
	
	@Test
	public void testRemoveItemOnNotExistItem() {		
		Item testItem = new Item("Onion Ring", "Mrs. Field", 10, "New", "Small", "999 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened.");
		assertFalse(myAuction.removeItem(testItem));
	}

	@Test
	public void testToString() {
		String testOutput = "Auction: Onion Auction\nOrganization: null\nDate: 2016-11-09T11:00"
				+ "\nNumber of items: 0\nDescription: This is a boring auction that only sell onion rings.";
		assertEquals(testOutput, myAuction.toString());
	}
	
	@Test
	public void testIsAuctionAvailable() {
		LocalDateTime testDate = LocalDateTime.of(2016, 11, 9, 11, 00);
		assertFalse(myAuction.isAuctionAvailable(testDate));
	}
}
