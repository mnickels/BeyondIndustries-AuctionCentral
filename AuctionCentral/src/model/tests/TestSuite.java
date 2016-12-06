package model.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author Mike Nickels
 * @version November 11 2016
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AccountTest.class,
	AuctionTest.class,
	DataTest.class,
	NonprofitTest.class,
	SerializerTest.class,

	AddItemTest.class,
	CancelAuctionTest.class,
	ChangeMaxAuctionsTest.class,
	PlaceBidTest.class,
	RemoveItemTest.class,
	RequestAuctionTest.class,
	ViewUpcomingAuctionsTest.class
})
public class TestSuite {
	// empty class body
}
