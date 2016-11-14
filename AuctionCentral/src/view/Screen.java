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
	private UIComponent[] myComponents;
	
	public Screen(Account theUser, Menu theStartingMenu, UIComponent... theComponents) {
		myUser = theUser;
		myMenu = theStartingMenu;
		myComponents = theComponents;
	}
	
	public Account getUser() {
		return myUser;
	}
	
	public Menu getMenu() {
		return myMenu;
	}
	
	public void setMenu(Menu theMenu) {
		myMenu = theMenu;
	}

	@Override
	public void display() {
		Separator.getInstance().display();
		System.out.printf("%s\n", SLOGAN);
		if (myUser != null) {
			System.out.printf("%s logged in as %s\n\n", myUser.getName(), myUser.toString());
		}
		if (myComponents != null) {
			for (UIComponent c : myComponents) {
				c.display();
				System.out.println();
			}
		}
		if (myMenu != null) {
			myMenu.display();
		}
	}

}
