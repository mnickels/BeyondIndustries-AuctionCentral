package controller;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Data;
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

	private void setup() {
		Data.setInstance((Data) Serializer.readFile("24Auctions.ser"));
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
