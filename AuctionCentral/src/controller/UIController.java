package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import model.Auction;
import model.Data;
import model.Item;
import model.Serializer;
import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;
import view.BidderPanel;
import view.Calendar;
import view.Input;
import view.Menu;
import view.NonprofitPanel;
import view.Option;
import view.Screen;
import view.Text;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public final class UIController extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	//private static final boolean DEBUG = true;

	private JPanel myScreen;

	private boolean myIsRunning;
	
	private Account myUser;

	UIController() {
		super("AuctionCentral");
		
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setName("AuctionCentral");

        setMinimumSize(new Dimension(800, 700));
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Logout", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.setVisible(true);
		
		menuItem.addActionListener(new loginActionListener());
		
		menu.add(menuItem);
		
		setJMenuBar(menuBar);
		myIsRunning = true;
		
		myScreen = new JPanel(new BorderLayout());
		
		setVisible(myIsRunning);
	}

	@Override
	public void run() {
		//if (DEBUG)
		//	loadState();
		//else
		setup();
		login();
	}

	private void bidder() {
		myScreen = new BidderPanel((Bidder) myUser);
		myScreen.setVisible(true);
		myScreen.setEnabled(true);
		this.add(myScreen);
		revalidate();
		repaint();
	}
	
	
	private void nonprofit() {
		boolean shouldLoop = true;

		//while (shouldLoop) {
			myScreen = new NonprofitPanel((Nonprofit) myUser);
			this.add(myScreen);
			revalidate();
			repaint();
			
			Auction currentAuction = Data.getInstance().getAuctionForThisNonprofit((Nonprofit) myUser);
			if (currentAuction != null) {
				((NonprofitPanel) myScreen).displayUpcomingAuction(currentAuction.getName(), 
						currentAuction.getDate().getMonth(), 
						currentAuction.getDate().getDayOfMonth(), 
						currentAuction.getDate().getYear());
				
				((NonprofitPanel) myScreen).disableButton(((NonprofitPanel) myScreen).BTNSUBMITAUCTIONREQUEST);
			} else {
				((NonprofitPanel) myScreen).disableButton(((NonprofitPanel) myScreen).BTNCANCELAUCTIONREQUEST);
				((NonprofitPanel) myScreen).disableButton(((NonprofitPanel) myScreen).BTNADDITEM);
				((NonprofitPanel) myScreen).disableButton(((NonprofitPanel) myScreen).BTNREMOVEITEM);
 
			}
			
			/*
			switch (Integer.parseInt(myScreen.getMenu().getInput())) {
			case 1:
				// auction request
				myScreen = new Screen(user, null,
						new Text("New Auction from " + user.getOrganizationName()));
				myScreen.display();
				Input i = new Input("Please enter the name of the auction: ");
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

				i = new Input("Please enter the auction description: ");
				i.display();
				String desc = i.getInput();

				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%s from %s\n", auctionName, user.getOrganizationName()));
				sb.append(String.format("Name: %s (%s)\n", user.getName(), user.getPhoneNumber()));
				sb.append(String.format("Date: %s %d, %d @ %d:%d\n", date.getMonth(), date.getDayOfMonth(),
						date.getYear(), date.getHour(), date.getMinute()));
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
				Input in = new Input("Please enter item name: ");
				in.display();
				String name = in.getInput();

				in = new Input("Please enter item description: ");
				in.display();
				String description = in.getInput();

				in = new Input("Please enter item starting bid: $");
				in.display();
				String startBid = in.getInput();

				in = new Input("Please enter donor's name: ");
				in.display();
				String donor = in.getInput();

				in = new Input("Please enter item quantity: ");
				in.display();
				int items = Integer.parseInt(in.getInput());

				in = new Input("Please enter item's condition: ");
				in.display();
				String condition = in.getInput();

				in = new Input("Please enter item size: ");
				in.display();
				String size = in.getInput();

				in = new Input("Please enter storage location: ");
				in.display();
				String address = in.getInput();

				StringBuilder s = new StringBuilder();
				s.append(String.format("%s: $%s\n", name, startBid));
				s.append(String.format("Donated by %s\n", donor));
				s.append(String.format("Quantity: %d\n", items));
				s.append(String.format("Item Size: %s\n", size));
				s.append(String.format("Stored at: %s", address));

				myScreen = new Screen(user, new Menu(
						"Are you sure you would like to submit this item for auction?",
						new Input(),
						new Option(1, "Submit item"),
						new Option(2, "Go back without submitting"),
						new Option(3, "Exit AuctionCentral without submitting")),
						new Text(s.toString()));
				myScreen.display();

				switch (Integer.parseInt(myScreen.getMenu().getInput())) {
				case 1:
					// submit auction request
					Item item = new Item(name, donor, items, condition, size, address, new BigDecimal(startBid), description);
					boolean itemAdded = Data.getInstance().getAuctionForThisNonprofit(user).addItem(item);
					if (itemAdded) {
						new Text(String.format("Your item %s was successfully submitted!", name)).display();
					} else {
						new Text("Your item was not able to be submitted at this time.").display();
					}
					break;
				case 3:
					shouldLoop = false;
				case 2:
					new Text("Your auction was not submitted.").display();
					break;
				}

				break;
			case 3:
				// 
				if (currentAuction == null) {
					new Text("You have no auction currently scheduled.").display();
				} else {
					StringBuilder str = new StringBuilder();
					for (Item it : currentAuction.getItems()) {
						BigDecimal highBid = BigDecimal.ZERO;
						for (BigDecimal bid : it.getBids().values()) {
							if (bid.compareTo(highBid) > 0) {
								highBid = bid;
							}
						}
						str.append(String.format("%s x %d\n", it.getName(), it.getQuantity()));
						str.append(String.format("\tHighest Bid: $%s\n", highBid));
					}
					myScreen = new Screen(myScreen.getUser(), 
							new Menu("Enter anything to return",
									new Input()),
							new Text(str.toString()));
					myScreen.display();
				}
				break;
			case 4:
				shouldLoop = false;
				break;
			}
			*/
		//}
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
							Data.getInstance().totalNumberOfUpcomingAuctions())));

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
				Menu calendarView = new Menu(
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
				 Data.setInstance((Data) Serializer.readFile(myScreen.getMenu().getInput()));
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
		//myScreen.setMenu(new Menu("Login", new Input("Enter username: ")));
		
		remove(myScreen);
		
		revalidate();
		repaint();
		
		while (myUser == null) {
			try {
				String text = JOptionPane.showInputDialog(this, "Please Enter Username: ");
				
				if (text == null) { // User hits cancel button on login.
					System.exit(EXIT_ON_CLOSE);
				}
				myUser = Data.getInstance().getUser(text); 
				if (myUser == null) {
					throw new Exception("Invalid Username");
				}
			} catch (final Exception e){
				JOptionPane.showMessageDialog(this, "This Username does not exist", e.toString(), 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (myUser instanceof Bidder) {
			bidder();
		} else if (myUser instanceof Nonprofit) {
			nonprofit();
		} else if (myUser instanceof Staff) {
			staff();
		}
	}
	
	private class loginActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			myUser = null;
			myScreen.removeAll();
			login();
			
		}
		
	}
}
