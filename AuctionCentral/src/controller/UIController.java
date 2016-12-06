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
			//loadState();
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
	}



	private void staff() {
		myFrame.remove(myScreen);
		myScreen = new StaffPanel((Staff) myUser);
		myFrame.add(myScreen);
		myFrame.revalidate();
		myFrame.repaint();
	}
	
	private void loadState() {

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
				Data.getInstance().currentDateTime.plusDays(1),
				"ASPCA Annual Fundraiser",
				"An auction to save the the puppies.");
		selectedAuction.addItem(new Item("Football signed by Russell Wilson", "Pete Carroll", 1,
				"Good", "Medium-Small", "Storage unit 1102", new BigDecimal(750),
				"A football signed by Seahawks quarterback Russell Wilson."));
		Data.getInstance().getAuctions().add(selectedAuction);
	}

	private void login() {
		myFrame.remove(myScreen);

		myScreen = new LoginPanel(this);
		myFrame.add(myScreen);
		myFrame.revalidate();
		myFrame.repaint();	
	}

	public void validateLoginInfo(String theUsername) {
		myUser = Data.getInstance().getUser(theUsername); 

		if (myUser == null) {
			JOptionPane.showMessageDialog(null, "Sorry, the username is invalid");
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
