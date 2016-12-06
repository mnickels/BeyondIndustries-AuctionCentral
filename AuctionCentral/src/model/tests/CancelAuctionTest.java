package model.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Data;
import model.users.Nonprofit;

/**
 * @author Gjorgi Stojanov
 * @author Mike Nickels | mnickels@uw.edu
 * @version December 5 2016
 */
public class CancelAuctionTest {
	
	private LocalDateTime myLocalDateTime;
	private String names[] = {"Burt Lawton", "Birdie Furlough", "Riley Egner", "Janice Hill", "Lynne Bickham", "Hans Kettler", 
			"Leola Larger", "Lizzette Villatoro", "Izetta Wiggs", "Kandice Fuss", "Sherice Farlow", "Queen Wilkerson",
			"Rebeca Rebello", "Francine Mondragon", "Roseanna Maupin", "Theo Mulkey", "Genie Player",
			"Bert Locust", "Kirby Rakow", "Sharen Dever", "Lorene Crosbie", "Linn Fuhrman", "Nathanial Morse", 
			"Ma Tutino", "Artie Kirkman"};
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
		Data.destroyInstance();
	}
	
	@Test
	public void testRemoveAuctionOnLessThanTwoDaysBeforeAuctionStarts() {
		Data d = Data.getInstance();
		
		// Setup auciton
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");		
		d.addAuction(auc); // Schedule auciton for one week from current time
		
		d.setCurrentDateTime(ld.minusDays(1)); // Forward time to a point one day before auction starts
		
		boolean result = d.removeAuction(auc); // Try to remove auction one day before it starts
		
		assertFalse(result); // Not allowed
	}
	
	@Test
	public void testRemoveAuctionOnExactlyTwoDaysBeforeAuctionStarts() {
		Data d = Data.getInstance();
		
		// Setup auciton
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");		
		d.addAuction(auc); // Schedule auciton for one week from current time
		
		d.setCurrentDateTime(ld.minusDays(2)); // Forward time to a point TWO days before auction starts
		
		boolean result = d.removeAuction(auc); // Try to remove auction TWO days before it starts
		
		assertFalse(result); // Not allowed
	}
	
	@Test
	public void testRemoveAuctionOnMoreThanTwoDaysBeforeAuctionStarts() {
		Data d = Data.getInstance();
		
		// Setup auciton
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");		
		d.addAuction(auc); // Schedule auciton for one week from current time
		
		d.setCurrentDateTime(ld.minusDays(3)); // Forward time to a point THREE days before auction starts
		
		boolean result = d.removeAuction(auc); // Try to remove auction THREE days before it starts
		
		assertTrue(result); // Allowed
	}
	
	@Test
	public void testRemoveAuctionOnNonprofitWithNoAuction() {
		Data d = Data.getInstance();
		
		// Setup aucitons
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");		
		d.addAuction(auc); // Schedule auciton for one week from current time
		
		Nonprofit np2 = new Nonprofit("IDontHaveAnAuction", "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc2 = new Auction(np2, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.removeAuction(auc2); // Nonprofit that is not in the system tries to remove auction 
		
		assertFalse(result); // Not allowed
	}
	
	@Test
	public void testRemoveAuctionOnNonprofitWithAuction() {
		Data d = Data.getInstance();
		
		// Setup aucitons
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");		
		d.addAuction(auc); // Schedule auciton for one week from current time
		
		boolean result = d.removeAuction(auc); // Nonprofit that has scheduled auction tries to cancel it 
		
		assertTrue(result); // Allowed
	}

}
