/**
 * 
 */
package view;

import java.util.Collection;

import model.Auction;
import model.Item;
import model.users.Account;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 */
public class BidMenu extends Menu {
	
	private final Auction myAuction;
	private final Account myUser;

	/**
	 * @param theQuery
	 * @param theInput
	 * @param theComponents
	 */
	public BidMenu(String theQuery, Input theInput, Auction theAuction, Account theUser, UIComponent... theComponents) {
		super(theQuery, theInput, theComponents);
		myAuction = theAuction;
		myUser = theUser;
	}
	
	@Override
	public void display() {
		StringBuilder sb = new StringBuilder();
		Collection<Item> items = myAuction.getItems();
		for (int i = 1; i <= items.size(); i++) {
			sb.append(str);
		}
		new Text(sb.toString()).display();
	}

}
