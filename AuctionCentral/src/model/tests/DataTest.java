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
	public void testAddAuctionOnListOfTwentyFiveAuctions() {
		Data d = Data.getInstance();
		for(int i = 0; i < 25; i++) {
			d.addAuction(auc);
		}
		assertFalse(d.addAuction(auc));
	}
	
	@Test
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
	}
	
	@After
	public void cleanUp() {
		Data.destroyInstance();
	}

}
