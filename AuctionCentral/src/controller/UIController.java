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
		
		LocalDateTime time = selectedAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		Text header = new Text(String.format("%s, %s %d, %d, %d%s",
				selectedAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM"));

		while (shouldLoop) {
			myScreen = new Screen(myScreen.getUser(), myScreen.getMenu(),
					header, new Text(getBidderDisplay((Bidder) myScreen.getUser(), selectedAuction)));
			
			Menu menu = new Menu(
					"What would you like to do?",
					new Input(),
					new Option(1, "Bid on an item"),
					new Option(2, "Go back"),
					new Option(3, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				myScreen.setMenu(new OptionlessMenu(
						"Type item ID to get more information and bid on the item",
						new Input()));
				myScreen.display();
				
				Item selectedItem = selectedAuction.getItems().get(Integer.parseInt(myScreen.getMenu().getInput()));
				myScreen = new Screen(myScreen.getUser(), new Menu(
						"What would you like to do?", new Input(),
						new Option(1, "Place bid on this item"),
						new Option(2, "Go back"),
						new Option(3, "Exit AuctionCentral")),
						header,
						new Text(String.format("%s\t%s\t$%s\n%s", selectedItem.getName(),
								selectedItem.getCondition(), selectedItem.getStartingBid(),
								selectedItem.getDescription())));
				myScreen.display();
				switch (Integer.parseInt(myScreen.getMenu().getInput())) {
				case 1:
					Menu m = new OptionlessMenu("Enter bid of at least $" + selectedItem.getStartingBid()
							+ " (no dollar sign or period after dollar amount):", new Input());
					m.display();
					int bid = Integer.parseInt(m.getInput());
					if (new BigDecimal(bid).compareTo(selectedItem.getStartingBid()) >= 0) {
						myScreen.setMenu(new Menu(
								"What would you like to do?",
								new Input(),
								new Option(1, "Place bid of $" + bid + " on " + selectedItem.getName()),
								new Option(2, "Go back without placing a bid"),
								new Option(3, "Exit AuctionCentral without placing a bid")));
						myScreen.display();
						switch (Integer.parseInt(myScreen.getMenu().getInput())) {
						case 1:
							// bid successfully placed
							selectedItem.addBid((Bidder) myScreen.getUser(), new BigDecimal(bid));
							new Text(String.format("You have just placed a bid of $%d on %s.\n"
									+ "AuctionCentral will notify you after %s %d, %d to let you know if\n"
									+ "yours is the winning bid.", bid, selectedItem.getName(),
									time.getMonth(), time.getDayOfMonth(), time.getYear())).display();
							break;
						case 3:
							shouldLoop = false;
						case 2:
							new Text("No bid was placed.").display();
							break;
						}
					} else {
						new Text("Sorry, you cannot place a bid for that amount.").display();
					}
					break;
				case 2:
					break;
				case 3:
					shouldLoop = false;
					break;
				}
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

		while (shouldLoop) {
			Menu menu = new Menu(
					"What would you like to do?",
					new Input(),
					new Option(1, "Submit an auction request"),
					new Option(2, "Add item to auction"),
					new Option(3, "View my inventory list"),
					new Option(4, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			Nonprofit user = (Nonprofit) myScreen.getUser();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				// auction request
				myScreen = new Screen(user, null,
						new Text("New Auction from " + user.getOrganizationName()));
				myScreen.display();
				Input i = new Input("Please enter the name of the Auction: ");
				i.display();
				String auctionName = i.getInput();
				
				i = new Input("Please enter the date of the auction (MM/DD/YYYY): ");
				i.display();
				String[] dateBits = i.getInput().split("/");
				i = new Input("Please enter the time of the auction (in military time (HH:MM)): ");
				i.display();
				String[] timeBits = i.getInput().split(":");
				LocalDateTime date = LocalDateTime.of(Integer.parseInt(dateBits[2]),
						Integer.parseInt(dateBits[0]), Integer.parseInt(dateBits[1]),
						Integer.parseInt(timeBits[0]), Integer.parseInt(timeBits[1]));
				
				i = new Input("Please enter the anticipated item count: ");
				i.display();
				int itemNum = Integer.parseInt(i.getInput());
				
				i = new Input("Please enter the item description: ");
				i.display();
				String desc = i.getInput();
				
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%s from %s\n", auctionName, user.getOrganizationName()));
				sb.append(String.format("Name: %s (%s)\n", user.getName(), user.getPhoneNumber()));
				sb.append(String.format("Date: %s\n", date));
				sb.append(String.format("Item Count: %d\n", itemNum));
				sb.append(String.format("Description: %s", desc));
				
				myScreen = new Screen(user, new Menu(
						"Are you sure you would like to submit this auction?",
						new Input(),
						new Option(1, "Submit auction"),
						new Option(2, "Go back without submitting"),
						new Option(3, "Exit AuctionCentral without submitting")),
						new Text(sb.toString()));
				myScreen.display();
				
				switch (Integer.parseInt(myScreen.getMenu().getInput())) {
				case 1:
					// submit auction request
					Auction auction = new Auction(user, date, auctionName, desc);
					boolean auctionAdded = Data.getInstance().addAuction(auction);
					if (auctionAdded) {
						new Text(String.format("Your auction %s on %s was successfully submitted!", auctionName, date)).display();
					} else {
						new Text("Your auction was not able to be submitted at this time.").display();
					}
					break;
				case 3:
					shouldLoop = false;
				case 2:
					new Text("Your auction was not submitted.").display();
					break;
				}
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
					new Input(),
					new Option(1, "View calendar of upcoming auctions"),
					new Option(2, "Administrative functions"),
					new Option(3, "Exit AuctionCentral"));
			
			myScreen.setMenu(menu);
			myScreen.display();
			
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				Menu calendarView = new OptionlessMenu(
						"Specify a day to view (enter the two digit date), or -1 to go back",
						new Input());
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
		Data.getInstance().addUser("nonprofit2", new Nonprofit("Abe Lincoln",
				"nonprofit2", "aaaaa@somenonprofit.org", "(253)555-5553",
				LocalDateTime.MIN, "ASPCA"));
		Data.getInstance().addUser("nonprof3", new Nonprofit("Lisa K.",
				"nonprof3", "me@ingvsfdg.org", "(253)555-5554",
				LocalDateTime.MIN, "ASPCA"));
		Data.getInstance().addUser("abidder", new Bidder("Bid McKinsley",
				"abidder", "bid@email.com", "(253)555-5556", "123 Somewhere St., Notown"));
		Data.getInstance().addUser("astaff", new Staff("Staff Guy",
				"astaff", "staffguy@auctioncentral.com", "(253)555-5557"));

		Auction selectedAuction = new Auction(
				(Nonprofit) Data.getInstance().getUser("anonprof"),
				Data.getInstance().currentDateTime.plusDays(10),
				"ASPCA Annual Fundraiser",
				"An auction to save the the puppies.");
		selectedAuction.addItem(new Item("Football signed by Russell Wilson", "Pete Carroll", 1,
				"Good", "Medium-Small", "Storage unit 1102", new BigDecimal(750),
				"A football signed by Seahawks quarterback Russell Wilson."));
		Data.getInstance().getAuctions().add(selectedAuction);
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
	
	/**
	 * Presents a string to display an Auction for a specific bidder.
	 * @param theBidder the bidder user observing the auction.
	 * @param theAuction the Auction being displayed
	 * @return A string representing the view that the user should see.
	 * @author Ming Lam
	 */
	public String getBidderDisplay(Bidder theBidder, Auction theAuction) {
		StringBuilder sb = new StringBuilder();
		sb.append("ID    Item name                             Condition      Min bid  My bid\n");
		for (int i = 0; i < theAuction.getSize(); i++) {
			Item tempItem = theAuction.getItems().get(i);
			String whitespace = "";

			for (int j = 0; j < 6 - Integer.toString(tempItem.getItemID()).length(); j++) {
				whitespace += " ";
			}
				sb.append(tempItem.getItemID() + whitespace);

		    whitespace = "";
			
			if (tempItem.getName().length() > 32) {
				sb.append(tempItem.getName().substring(0, 32) + "...   ");
			} else {
				for (int j = 0; j < 38 - tempItem.getName().length(); j++) {
					whitespace += " ";
				}
				sb.append(tempItem.getName() + whitespace);
			}
			
			
			whitespace = "";
			for (int j = 0; j < 15 - tempItem.getCondition().length(); j++) {
				whitespace += " ";
			}
			sb.append(tempItem.getCondition() + whitespace);
			
			whitespace = "";
			for (int j = 0; j < 8 - tempItem.getStartingBid().toString().length(); j++) {
				whitespace += " ";
			}
			sb.append("$" + tempItem.getStartingBid() + whitespace);
			
			if (tempItem.getBid(theBidder) != null) {
				sb.append("$" + tempItem.getBid(theBidder));
			}
			sb.append("\n");
		}
		return sb.toString();
		
	}

}
