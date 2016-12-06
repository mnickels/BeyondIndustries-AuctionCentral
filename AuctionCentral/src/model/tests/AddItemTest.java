package model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class AddItemTest {

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

}
