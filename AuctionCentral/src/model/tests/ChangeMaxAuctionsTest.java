package model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import model.Data;

/**
 * @author Gjorgi Stojanov
 * @author Mike Nickels | mnickels@uw.edu
 * @version December 5 2016
 */
public class ChangeMaxAuctionsTest {
	
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

}
