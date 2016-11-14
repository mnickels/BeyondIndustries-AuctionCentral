package model.tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import model.Auction;
import model.Data;
import model.users.Nonprofit;

import org.junit.After;

public class DataTest {
	
	private Nonprofit np;
	private Auction auc;
	private LocalDateTime myLocalDateTime;
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
		np = new Nonprofit("John Smith", "jsmith", "jsmith@email.com", "2535550000", myLocalDateTime, "FreePuppies");
		auc = new Auction(np, LocalDateTime.of(2016, 11, 11, 9, 0), "SimpleAuction", "auction description");
		
	}
	
	@Test
	public void testAddAuctionOnListOfTwentyFiveUpcomingAuctions() {
		Data d = Data.getInstance();
		
		//25 names
		String names[] = {"Burt Lawton", "Birdie Furlough", "Riley Egner", "Janice Hill", "Lynne Bickham", "Hans Kettler", 
				"Leola Larger", "Lizzette Villatoro", "Izetta Wiggs", "Kandice Fuss", "Sherice Farlow", "Queen Wilkerson",
				"Rebeca Rebello", "Francine Mondragon", "Roseanna Maupin", "Theo Mulkey", "Genie Player",
				"Bert Locust", "Kirby Rakow", "Sharen Dever", "Lorene Crosbie", "Linn Fuhrman", "Nathanial Morse", 
				"Ma Tutino", "Artie Kirkman"};
		
		//Add 25 different auctions with different nonprofit names and different auction dates
		for(int i = 0; i < 24; i++) {
			Nonprofit np = new Nonprofit(names[i], "username", "email", "phonenumber", myLocalDateTime, "FreePuppies");
			Auction auc = new Auction(np, d.getCurrentDateTime().plusDays(i), "AuctionName", "AuctionDescr");
			d.addAuction(auc);
		}
		
		for(Auction a: d.getAuctions()) {
			System.out.println(a.getName() + " " + a.getDate().toLocalDate().toString());
		}
		
		assertFalse(d.addAuction(auc));
	}
	
	/*@Test
	public void testAddAuctionOnListOfTwentyFourAuctions() {
		Data d = Data.getInstance();
		for(int i = 0; i < 24; i++) {
			d.addAuction(auc);
		}
		assertTrue(d.addAuction(auc));
	}
	
	@Test
	public void testAddAuctionOnListOfLessThanTwentyFourAuctions() {
		Data d = Data.getInstance();
		d.addAuction(auc);
		assertTrue(d.addAuction(auc));
	}*/
	
	@After
	public void cleanUp() {
		Data.destroyInstance();
	}

}
