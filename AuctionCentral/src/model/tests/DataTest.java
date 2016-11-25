package model.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Data;
import model.users.Bidder;
import model.users.Nonprofit;

import org.junit.After;

public class DataTest {
	
	private Nonprofit np;
	private Auction auc;
	private LocalDateTime myLocalDateTime;
	private String names[] = {"Burt Lawton", "Birdie Furlough", "Riley Egner", "Janice Hill", "Lynne Bickham", "Hans Kettler", 
			"Leola Larger", "Lizzette Villatoro", "Izetta Wiggs", "Kandice Fuss", "Sherice Farlow", "Queen Wilkerson",
			"Rebeca Rebello", "Francine Mondragon", "Roseanna Maupin", "Theo Mulkey", "Genie Player",
			"Bert Locust", "Kirby Rakow", "Sharen Dever", "Lorene Crosbie", "Linn Fuhrman", "Nathanial Morse", 
			"Ma Tutino", "Artie Kirkman"};
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
		np = new Nonprofit("John Smith", "jsmith", "jsmith@email.com", "2535550000", myLocalDateTime.minusYears(1), "FreePuppies");
		auc = new Auction(np, LocalDateTime.now().plusDays(28), "SimpleAuction", "auction description");
		
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
	
	@Test
	public void testAddAuctionOnNoFutureAuctionsForANonprofit() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");

		d.addAuction(auc);
		
		Nonprofit np2 = new Nonprofit(names[1], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld2 = d.getCurrentDateTime().plusWeeks(1);
		Auction auc2 = new Auction(np2, ld2.plusDays(2), "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc2);
		
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionOnOneFutureAuctionsForANonprofit() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");

		d.addAuction(auc);
		
		/* Add another auction for the same nonprofit 'names[0]'. Nonprofit 'names[0]' already has a scheduled 
		   auction - not allowed*/
		Nonprofit np2 = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld2 = d.getCurrentDateTime().plusWeeks(1);
		Auction auc2 = new Auction(np2, ld2.plusDays(2), "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc2);
		
		assertFalse(result);
	}
	
	
	@Test
	public void testAddAuctionOnListOfTwentyFiveUpcomingAuctions() {
		Data d = Data.getInstance();		
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		
		//Add 25 different auctions, two for every next day.
		int j = 1;
		int r = 0;
		for(int i = 0; i < 25; i++) {
			if (r == 2) {
				r = 0;
				j = j + 1;
			}
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
			Auction auc = new Auction(np, ld.plusDays(j), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
			
			r++;
		}
		
		
		//Add a 26th auction
		boolean result = d.addAuction(auc); 

		
		assertFalse(result);
	}
	
	
	@Test
	public void testAddAuctionOnListOfTwentyFourUpcomingAuctions() {
		Data d = Data.getInstance();
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		
		int j = 1;
		int r = 0;
		for(int i = 0; i < 24; i++) {
			if (r == 2) {
				r = 0;
				j = j + 1;
			}
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
			Auction auc = new Auction(np, ld.plusDays(j), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
			
			r++;
		}
	
		
		//add 25th auction
		boolean result = d.addAuction(auc);
		
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionOnListOfLessThanTwentyFourUpcomingAuctions() {
		Data d = Data.getInstance();
		d.addAuction(auc);
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc2 = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc2);
		
		assertTrue(result);
	}
	
	
	
	@Test
	public void testAddAuctionOnNoScheduledAuctionsForThisDay() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		// This day is empty
		Auction auc = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionOnOneScheduledAuctionsForThisDay() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");
		d.addAuction(auc);
		
		//This is auction is added on the same date as the previous one - allowed
		Nonprofit np2 = new Nonprofit(names[1], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc2 = new Auction(np2, ld.plusDays(1), "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc2);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionOnTwoScheduledAuctionsForThisDay() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld.plusDays(1), "AuctionName", "AuctionDescr");
		d.addAuction(auc);
		
		Nonprofit np2 = new Nonprofit(names[1], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc2 = new Auction(np2, ld.plusDays(1), "AuctionName", "AuctionDescr");
		d.addAuction(auc2);
		
		//Trying to add 3rd auction on the same day - not allowed
		Nonprofit np3 = new Nonprofit(names[2], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np3, ld.plusDays(1), "AuctionName", "AuctionDescr");
		boolean result = d.addAuction(auc3);
		
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledExactlyOneMonthIntoTheFuture() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it exactly one month from now
		LocalDateTime ld = d.getCurrentDateTime().plusMonths(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledLessThanOneMonthIntoTheFuture() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it three weeks from now
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(2);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledExactlyOneMonthPlusOneDayIntoTheFuture() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it exactly one month plus one day into the future
		LocalDateTime ld = d.getCurrentDateTime().plusMonths(1).plusDays(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledMoreThanOneMonthPlusOneDayIntoTheFuture() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it more than one month plus one day into the future
		//We schedule it one month plus 6 days into the future
		LocalDateTime ld = d.getCurrentDateTime().plusMonths(6);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledExactlyOneWeekFromCurrentTime() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it exactly one week from now - allowed
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledMoreThanOneWeekFromCurrentTime() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it one week plus one day from now - allowed
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1).plusDays(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledExactlySixDaysFromCurrentTime() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it exactly six days from now - not allowed
		LocalDateTime ld = d.getCurrentDateTime().plusDays(6);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledLessThanSixDaysFromCurrentTime() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it less than six days from now - not allowed
		LocalDateTime ld = d.getCurrentDateTime().plusDays(5);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledOnTheSameDateAsCurrentTime() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it today - not allowed
		LocalDateTime ld = d.getCurrentDateTime();
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForAuctionScheduledForPastDates() {
		Data d = Data.getInstance();
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Schedule it for yesterday - not allowed 'How could you possibly make a schedule for yesterday :)' 
		LocalDateTime ld = d.getCurrentDateTime().minusDays(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
	}
	
	@Test
	public void testAddAuctionForANonprofitWithPastAucitonLessThanOneYearMinusOneDayAgo() {
		Data d = Data.getInstance();
		
		//Nonprofit past auction was 210 days ago (less than one year minus one day ago) - not allowed
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", 
				myLocalDateTime.minusDays(210), 
				"FreePuppies"); 
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
		
	}
	
	@Test
	public void testAddAuctionForANonprofitWithPastAucitonExactlyOneYearMinusOneDayAgo() {
		Data d = Data.getInstance();
		
		//Nonprofit past auction was 364 days ago (one year minus one day 365-1=364) - not allowed
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", 
				myLocalDateTime.minusYears(1).plusDays(1), 
				"FreePuppies"); 
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertFalse(result);
		
	}
	
	@Test
	public void testAddAuctionForANonprofitWithPastAucitonGreaterThanOneYearInThePast() {
		Data d = Data.getInstance();
		
		//Nonprofit past auction was one year and 3 months ago
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", 
				myLocalDateTime.minusYears(1).minusMonths(3), 
				"FreePuppies"); 
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
		
	}
	
	@Test
	public void testAddAuctionForANonprofitWithPastAucitonExactlyOneYearInThePast() {
		Data d = Data.getInstance();
		
		//Nonprofit latest past auction was exactly one year ago 
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", 
				myLocalDateTime.minusYears(1), 
				"FreePuppies"); 
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
		
	}
	
	@Test
	public void testAddAuctionForANonprofitWithNoPastAucitons() {
		Data d = Data.getInstance();
		
		/*Nonprofit with no past auctions. Past auction is set to the date when this 
		  auction will be scheduled */
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		
		Nonprofit np = new Nonprofit(names[0], "username", "email", "phonenumber", 
				ld, "FreePuppies"); 
		
		Auction auc = new Auction(np, ld, "AuctionName", "AuctionDescr");
		
		boolean result = d.addAuction(auc);
		assertTrue(result);
		
	}
	
	
	
	@Test
	public void testTotalNumberOfUpcomingAuctionsCountingTodaysAuctions() {
		
		Data d = Data.getInstance();
		
		LocalDateTime ld = d.getCurrentDateTime();
		
		//Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		//Auction auc3 = new Auction(np2, LocalDateTime.of(2016, 11, 25, 11, 00), "AuctionName", "AuctionDescr");

		//d.addAuction(auc3);

		for(int i = 4; i < 11; i++) {
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
			Auction auc = new Auction(np, ld.plusDays(i), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
		}
		
		//Auctions sheduled for: 11/21; 11/22; 11/23; 11/24; 11/25; 11/25; 11/26; 11/27;
		
		//Set current date to 11/25 - should have 4 scheduled auctions, counting today's auctions
		//d.setCurrentDateTime(LocalDateTime.of(2016, 11, 25, 11, 00));
		
		int result = d.totalNumberOfUpcomingAuctions();
		assertEquals(4, result);
	}
	
	@Test
	public void testNonprofitHasAuctionOnExistingNonprofit() {
		
		Data d = Data.getInstance();
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		
		Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np2, LocalDateTime.now().plusDays(14), "AuctionName", "AuctionDescr");

		d.addAuction(auc3);

		for(int i = 0; i < 7; i++) {
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
			Auction auc = new Auction(np, ld.plusDays(i), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
		}
		
		boolean result = d.nonprofitHasAuction(np2);
		assertTrue(result);
	}
	
	@Test
	public void testNonprofitHasAuctionOnNonExistingNonprofit() {
		
		Data d = Data.getInstance();
		
		LocalDateTime ld = d.getCurrentDateTime().plusWeeks(1);
		
		Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np2, LocalDateTime.of(2016, 11, 25, 11, 00), "AuctionName", "AuctionDescr");

		d.addAuction(auc3);

		for(int i = 0; i < 7; i++) {
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
			Auction auc = new Auction(np, ld.plusDays(i), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
		}
		
		Nonprofit np3 = new Nonprofit("Bruce Willis", "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		
		boolean result = d.nonprofitHasAuction(np3);
		assertFalse(result);
	}
	
	@Test
	public void testGetAuctionForThisNonprofitWhichExists() {
		Data d = Data.getInstance();
		
		Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np2, LocalDateTime.now().plusDays(14), "MyUniqueAuctionName", "AuctionDescr");

		d.addAuction(auc3);

		String result = d.getAuctionForThisNonprofit(np2).getName();
		assertEquals("MyUniqueAuctionName", result);
	}
	
	@Test
	public void testGetAuctionForThisNonprofitWhichDoesNotExists() {
		Data d = Data.getInstance();
		
		
		Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np2, LocalDateTime.of(2016, 11, 25, 11, 00), "MyUniqueAuctionName", "AuctionDescr");

		d.addAuction(auc3);

		Nonprofit np3 = new Nonprofit("IDont Exist", "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		
		Object o = d.getAuctionForThisNonprofit(np3);		
		assertNull(o);
	}
	
	@Test
	public void testAddAnAuction() {
		Nonprofit np2 = new Nonprofit(names[10], "username", "email", "phonenumber", myLocalDateTime.minusYears(1), "FreePuppies");
		Auction auc3 = new Auction(np2, LocalDateTime.of(2016, 11, 25, 11, 00), "MyUniqueAuctionName", "AuctionDescr");
		
		Data.getInstance().addAuction(auc3);
		
		for (Auction a : Data.getInstance().getAuctions()) {
			System.out.println(a);
		}
	}
	
	@Test
	public void testGetUser() {
		Data.getInstance().addUser("abc", new Bidder("name", "abc", "email", "phone", "addr"));
		//System.out.println(Data.getInstance().getUser("abc"));
		assertNotNull(Data.getInstance().getUser("abc"));
	}
	
	@Test
	public void testSetMaxAuctionsToNegative() {
		Data.getInstance().setMaxNumberOfAuctions(-5);
		assertNotEquals(-5, Data.getInstance().getMaxNumberOfAuctions());
	}
	
	@Test
	public void testSetMaxAuctionsToZero() {
		Data.getInstance().setMaxNumberOfAuctions(0);
		assertNotEquals(0, Data.getInstance().getMaxNumberOfAuctions());
	}
	
	@Test
	public void testSetMaxAuctionsToPositive() {
		Data.getInstance().setMaxNumberOfAuctions(5);
		assertEquals(5, Data.getInstance().getMaxNumberOfAuctions());
	}
	
	@After
	public void cleanUp() {
		Data.destroyInstance();
	}

}
