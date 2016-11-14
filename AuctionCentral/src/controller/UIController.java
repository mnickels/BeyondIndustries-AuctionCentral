package controller;

import model.Data;
import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;
import view.MenuBuilder;
import view.Screen;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public final class UIController implements Runnable {
	
	private Screen myScreen;
	
	private LocalDateTime myLocalDateTime;
	
	UIController() {
		MenuBuilder.buildMenus();
		myScreen = new Screen(null, MenuBuilder.MAIN_MENU);
	}

	@Override
	public void run() {
		
		login();
		
		while (true) {
			myScreen.display();
		}
	}
	
	private void login() {
		Data.getInstance().addUser("mnickels", new Nonprofit("Mike Nickels", "mnickels", "mnickels@uw.edu", "(253)555-5555"));
		
		MenuBuilder.MAIN_MENU.display();
		Account user = Data.getInstance().getUser(MenuBuilder.MAIN_MENU.getInput());
		while (user == null) {
			System.out.println("Incorrect username.\n");
			MenuBuilder.MAIN_MENU.display();
			user = Data.getInstance().getUser(MenuBuilder.MAIN_MENU.getInput());
		}
		if (user instanceof Bidder) {
			myScreen = new Screen(user, MenuBuilder.BIDDER_MAIN_MENU);
		} else if (user instanceof Nonprofit) {
			myScreen = new Screen(user, MenuBuilder.NONPROFIT_MAIN_MENU);
		} else if (user instanceof Staff) {
			myScreen = new Screen(user, MenuBuilder.STAFF_MAIN_MENU);
		} else {
			System.err.println("This should never be the case...");
		}
	}

}
