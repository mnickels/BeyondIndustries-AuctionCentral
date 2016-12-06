package model.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Item;
import model.users.Nonprofit;

/**
 * @author Ming Lam
 * @author Mike Nickels | mnickels@uw.edu
 * @version December 5 2016
 */
public class RemoveItemTest {
	
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
	public void testRemoveItem() {
		myAuction.addItem(myItem);
		myAuction.setDate(LocalDateTime.now().plusDays(3));

		Item testItem = new Item("Onion Ring", "Mrs. Field", 10, "New", "Small", "999 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened.");
		assertEquals(Auction.SUCCESS, myAuction.removeItem(testItem));

	}
	
	@Test
	public void testRemoveItemOnNotExistItem() {
		myAuction.setDate(LocalDateTime.now().plusDays(3));
		Item testItem = new Item("Onion Ring", "Mrs. Field", 10, "New", "Small", "999 Deadend Road",
				new BigDecimal(3.99), "Tasty snack that has never been opened.");
		assertEquals(Auction.FAILNOITEMEXIST, myAuction.removeItem(testItem));
	}

}
