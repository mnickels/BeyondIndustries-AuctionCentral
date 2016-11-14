package view;

import model.users.Account;

/**
 * Describes a screen ui element for directing the interaction with the user.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Screen implements UIComponent {
	
	/** The AuctionCentral slogan. */
	private static final String SLOGAN = "AuctionCentral: the auctioneer for non-profit organizations.";
	
	/** The user currently logged in. */
	private Account myUser;
	/** The menu being displayed. */
	private Menu myMenu;
	
	public Screen(Account theUser, Menu theStartingMenu) {
		myUser = theUser;
		myMenu = theStartingMenu;
	}
	
	public void setUser(Account theUser) {
		myUser = theUser;
	}

	@Override
	public void display() {
		Separator.getInstance().display();
		System.out.printf("%s\n%s logged in as %s\n\n", SLOGAN, myUser.getName(), myUser.toString());
		myMenu.display();
		myMenu = myMenu.nextMenu();
	}

}
