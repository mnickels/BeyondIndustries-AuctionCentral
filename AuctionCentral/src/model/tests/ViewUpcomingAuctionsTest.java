package model.tests;

import static org.junit.Assert.assertEquals;

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
public class ViewUpcomingAuctionsTest {

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

}
