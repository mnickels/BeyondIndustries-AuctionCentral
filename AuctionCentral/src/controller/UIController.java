package controller;

import java.time.LocalDateTime;

import model.Auction;
import model.Data;
import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;
import view.Input;
import view.Menu;
import view.Option;
import view.OptionlessMenu;
import view.Screen;
import view.Text;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public final class UIController implements Runnable {
	
	private static final boolean DEBUG = true;
	
	private Screen myScreen;
	
	private boolean myIsRunning;
	
	UIController() {
		myScreen = new Screen(null, null);
		myIsRunning = true;
	}

	@Override
	public void run() {
		
		if (DEBUG)
			loadState();
		
		while (myIsRunning) {
			login();
			
			Account user = myScreen.getUser();
			if (user instanceof Bidder) {
				bidder();
			} else if (user instanceof Nonprofit) {
				nonprofit();
			} else if (user instanceof Staff) {
				staff();
			} else {
				System.err.println("This should never be the case...");
			}
		}
	}
	
	private void bidder() {
		boolean shouldLoop = true;
		
		// starting the bidder UI with an auction selected and open
		// to demonstrate user story 1
		Data.getInstance().addAuction(new Auction(
				(Nonprofit) Data.getInstance().getUser("mnickels"),
				Data.getInstance().currentDateTime.plusDays(5),
				"Marxism for Moms Annual Auction Spectacular",
				"An auction for the everyday, working-class mom."));
		Auction selectedAuction = Data.getInstance().getAuctions().get(0);
		
		LocalDateTime time = Data.getInstance().currentDateTime;
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		myScreen = new Screen(myScreen.getUser(), myScreen.getMenu(),
				new Text(String.format("%s, %s %d, %d, %d%s",
						selectedAuction.getName(),
						time.getMonth(),
						time.getDayOfMonth(),
						time.getYear(),
						currentHour,
						time.getHour() < 12 ? "AM" : "PM")));

		while (shouldLoop) {
			Menu menu = new Menu(
					"What would you like to do?",
					new Input(),
					new Option(1, "Place a bid"),
					new Option(2, ""),
					new Option(3, ""));
			
			myScreen = new Screen(myScreen.getUser(), menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				
			}
		}
	}
	
	private void nonprofit() {
		
	}
	
	private void staff() {
		
	}
	
	private void loadState() {
		myScreen.setMenu(
new Menu(
						"Would you like to load a serializable Data file?",
						new Input("\tfilename: ")));

		if (!myScreen.getMenu().getInput().isEmpty()) {
			try {
				// Data.setData((Data) Serializer.readFile(myScreen.getMenu().getInput()));
			} catch (Exception e) {
				System.err.println("Incorrect filename for a serialized Data object.");
			}
		}
	}
	
	private void login() {
		Data.getInstance().addUser("mnickels", new Nonprofit("Mike Nickels",
				"mnickels", "mnickels@uw.edu", "(253)555-5555",
				LocalDateTime.MAX, "Marxism for Moms"));
		Data.getInstance().addUser("abidder", new Bidder("Bid McKinsley",
				"abidder", "bid@email.com", "(253)555-5556", "123 Somewhere St., Notown"));
		
		myScreen.setMenu(
				new OptionlessMenu(
						"Login",
						new Input("Enter username: ")));
		myScreen.display();
		
		Account user = Data.getInstance().getUser(myScreen.getMenu().getInput());
		while (user == null) {
			System.out.println("Incorrect username.\n");
			myScreen.getMenu().display();
			user = Data.getInstance().getUser(myScreen.getMenu().getInput());
		}
		
		myScreen = new Screen(user, myScreen.getMenu());
	}

}
