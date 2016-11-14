package model.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author Mike Nickels
 * @version November 11 2016
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	SerializerTest.class,
	AccountTest.class,
	AuctionTest.class,
	DataTest.class,
	ItemTest.class
})
public class TestSuite {
	// empty class body
}
