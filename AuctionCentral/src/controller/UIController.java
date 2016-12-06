package controller;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Auction;
import model.Data;
import model.Item;
import model.Serializer;
import model.users.Account;
import model.users.Bidder;
import model.users.Nonprofit;
import model.users.Staff;
import view.BidderPanel;
import view.LoginPanel;
import view.MainFrame;
import view.NonprofitPanel;
import view.StaffPanel;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public final class UIController implements Runnable {

	//private static final boolean DEBUG = true;

	private MainFrame myFrame;
	
	private JPanel myScreen;
	
	private Account myUser;

	UIController() {
		myFrame = new MainFrame(this);
		myScreen = new JPanel(new BorderLayout());
	}

	@Override
	public void run() {
		//if (DEBUG)
			loadState();
		//else
		setup();
		login();
	}

	private void bidder() {
		myFrame.remove(myScreen);
		myScreen = new BidderPanel((Bidder) myUser);
		myScreen.setVisible(true);
		myScreen.setEnabled(true);
		myFrame.add(myScreen);
		myFrame.revalidate();
		myFrame.repaint();
	}
	
	
	private void nonprofit() {
			myFrame.remove(myScreen);
			myScreen = new NonprofitPanel((Nonprofit) myUser);
			myFrame.add(myScreen);
			myFrame.revalidate();
			myFrame.repaint();
			
			/*
			Auction currentAuction = Data.getInstance().getAuctionForThisNonprofit((Nonprofit) myUser);
			if (currentAuction != null) {
				((NonprofitPanel) myScreen).displayUpcomingAuction(currentAuction.getName(), 
						currentAuction.getDate().getMonth(), 
						currentAuction.getDate().getDayOfMonth(), 
						currentAuction.getDate().getYear());
				
				((NonprofitPanel) myScreen).disableButton(NonprofitPanel.BTNSUBMITAUCTIONREQUEST);
				
				if (currentAuction.getSize() == 0) {
					((NonprofitPanel) myScreen).disableButton(NonprofitPanel.BTNREMOVEITEM);
				}
			} else {
				((NonprofitPanel) myScreen).disableButton(NonprofitPanel.BTNCANCELAUCTIONREQUEST);
				((NonprofitPanel) myScreen).disableButton(NonprofitPanel.BTNADDITEM);
				((NonprofitPanel) myScreen).disableButton(NonprofitPanel.BTNREMOVEITEM);
 
			}
			*/
	}


	
	private void staff() {
		myFrame.remove(myScreen);
		myScreen = new StaffPanel((Staff) myUser);
		myFrame.add(myScreen);
		myFrame.revalidate();
		myFrame.repaint();
		
		/*
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
		*/
	}

	private void loadState() {
		String filename = JOptionPane.showInputDialog("Would you like to load a serializable Data file?\nfilename: ");
		if(!filename.isEmpty()){
			try {
				 Data.setInstance((Data) Serializer.readFile(filename));
			} catch (Exception e) {
				System.err.println("Incorrect filename for a serialized Data object.");
			}
		}
			

		/*
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
		*/
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
		
		Data.getInstance().addUser("astaff2", new Staff("Robert Johnson",
				"astaff2", "robertj@auctioncentral.com", "(253)556-5157"));

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
		
		myFrame.remove(myScreen);
		
		//revalidate();
		//repaint();
		
		//while (myUser == null) {
				myScreen = new LoginPanel(this);
				myFrame.add(myScreen);
				myFrame.revalidate();
				myFrame.repaint();

				/*
				String text = JOptionPane.showInputDialog(this, "Please Enter Username: ");
				
				if (text == null) { // User hits cancel button on login.
					System.exit(EXIT_ON_CLOSE);
				}
				if (myUser == null) {
					throw new Exception("Invalid Username");
				}
				*/
		//}
		
		
	}
	
	public void validateLoginInfo(String theUsername) {
			myUser = Data.getInstance().getUser(theUsername); 
			
			if (myUser == null) {
			((LoginPanel) myScreen).showInvalidUsernameError();
			} else {
				if (myUser instanceof Bidder) {
					bidder();
				} else if (myUser instanceof Nonprofit) {
					nonprofit();
				} else if (myUser instanceof Staff) {
					staff();
				}
			}
	}
	
	public void clearProfile() {
		myUser = null;
		myScreen.removeAll();
		login();
	}
}
