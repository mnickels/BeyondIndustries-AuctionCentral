package controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import model.Auction;
import model.Data;
import model.Item;
import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;
import view.Calendar;
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
	
	private static final boolean DEBUG = false;
	
	private Screen myScreen;
	
	private boolean myIsRunning;
	
	UIController() {
		myScreen = new Screen(null, null);
		myIsRunning = true;
	}

	@Override
	public void run() {
		
		setup();
		
		if (DEBUG)
			loadState();
		
		while (myIsRunning) {
			myScreen = new Screen(null, null);
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
					new Input(" -> "),
					new Option(1, "Bid on an item"),
					new Option(2, "Go back"),
					new Option(3, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				// bid
				break;
			case 2:
			case 3:
				shouldLoop = false;
				break;
			}
		}
	}
	
	private void nonprofit() {
		boolean shouldLoop = true;
		
		LocalDateTime time = Data.getInstance().currentDateTime;
		myScreen = new Screen(myScreen.getUser(), myScreen.getMenu(),
				new Text(String.format("%s %d, %d. Total number of upcoming auctions: %d",
						time.getMonth(),
						time.getDayOfMonth(),
						time.getYear(),
						Data.getInstance().totalNumberOfUpcommingAuctions())));

		while (shouldLoop) {
			Menu menu = new Menu(
					"What would you like to do?",
					new Input(" -> "),
					new Option(1, "Submit an auction request"),
					new Option(2, "Add item to auction"),
					new Option(3, "View my inventory list"),
					new Option(4, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				// auction request
				break;
			case 2:
				// add item to auction
				break;
			case 4:
				shouldLoop = false;
				break;
			}
		}
	}
	
	private void staff() {
		boolean shouldLoop = true;

		while (shouldLoop) {
			LocalDateTime time = Data.getInstance().currentDateTime;
			myScreen = new Screen(myScreen.getUser(), myScreen.getMenu(),
					new Text(String.format("%s %d, %d. Total number of upcoming auctions: %d",
							time.getMonth(),
							time.getDayOfMonth(),
							time.getYear(),
							Data.getInstance().totalNumberOfUpcommingAuctions())));
			
			Menu menu = new Menu(
					"What would you like to do?",
					new Input(" -> "),
					new Option(1, "View calendar of upcoming auctions"),
					new Option(2, "Administrative functions"),
					new Option(3, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				Menu calendarView = new OptionlessMenu(
						"Specify a day to view (enter the two digit date), or -1 to go back",
						new Input(" -> "));
				Calendar c = new Calendar(Data.getInstance().currentDateTime);
				
				myScreen = new Screen(myScreen.getUser(), calendarView, c);
				myScreen.display();
				break;
			case 2:
				break;
			case 3:
				shouldLoop = false;
				break;
			}
		}
	}

	private void loadState() {
		myScreen.setMenu(
				new Menu(
						"Would you like to load a serializable Data file?",
						new Input("\tfilename: ")));
		myScreen.display();
		if (!myScreen.getMenu().getInput().isEmpty()) {
			try {
				// Data.setData((Data) Serializer.readFile(myScreen.getMenu().getInput()));
			} catch (Exception e) {
				System.err.println("Incorrect filename for a serialized Data object.");
			}
		}
	}
	
	private void setup() {
		Data.getInstance().addUser("anonprof", new Nonprofit("Nonprofit Mann",
				"anonprof", "nonprof@aspca.org", "(253)555-5555",
				LocalDateTime.MIN, "ASPCA"));
		Data.getInstance().addUser("abidder", new Bidder("Bid McKinsley",
				"abidder", "bid@email.com", "(253)555-5556", "123 Somewhere St., Notown"));
		Data.getInstance().addUser("astaff", new Staff("Staff Guy",
				"astaff", "staffguy@auctioncentral.com", "(253)555-5557"));

		Auction selectedAuction = new Auction(
				(Nonprofit) Data.getInstance().getUser("anonprof"),
				Data.getInstance().currentDateTime.plusDays(5),
				"ASPCA Annual Fundraiser",
				"An auction to save the the puppies.");
		selectedAuction.addItem(new Item("Football signed by Russell Wilson", "Pete Carroll", 1,
				"Good", "Medium-Small", "Storage unit 1102", new BigDecimal(750),
				"A football signed by Seahawks quarterback Russell Wilson."));
		Data.getInstance().addAuction(selectedAuction);
	}
	
	private void login() {
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
