package controller;

import java.time.LocalDateTime;

import model.users.Nonprofit;
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
		myLocalDateTime = LocalDateTime.now();
		myScreen = new Screen(new Nonprofit("Mike Nickels", 
				"mnickels", "mnickels@uw.edu", "(253)123-4567", myLocalDateTime),
				MenuBuilder.NONPROFIT_MAIN_MENU);
	}

	@Override
	public void run() {
		while (true) {
			myScreen.display();
		}
	}

}
