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
	
	private LocalDateTime myLocalDateTime;
	private String names[] = {"Burt Lawton", "Birdie Furlough", "Riley Egner", "Janice Hill", "Lynne Bickham", "Hans Kettler", 
			"Leola Larger", "Lizzette Villatoro", "Izetta Wiggs", "Kandice Fuss", "Sherice Farlow", "Queen Wilkerson",
			"Rebeca Rebello", "Francine Mondragon", "Roseanna Maupin", "Theo Mulkey", "Genie Player",
			"Bert Locust", "Kirby Rakow", "Sharen Dever", "Lorene Crosbie", "Linn Fuhrman", "Nathanial Morse", 
			"Ma Tutino", "Artie Kirkman"};
	
	@Before
	public void setUp() {
		myLocalDateTime = LocalDateTime.now();
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
	public void testGetUser() {
		Data.getInstance().addUser("abc", new Bidder("name", "abc", "email", "phone", "addr"));
		//System.out.println(Data.getInstance().getUser("abc"));
		assertNotNull(Data.getInstance().getUser("abc"));
	}
	
	@After
	public void cleanUp() {
		Data.destroyInstance();
	}

}
