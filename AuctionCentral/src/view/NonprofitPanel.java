package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.users.Nonprofit;

public class NonprofitPanel extends JPanel {
	
	public static final int BTNSUBMITAUCTIONREQUEST = 1;
	
	public static final int BTNCANCELAUCTIONREQUEST = 2;
	
	public static final int BTNADDITEM = 3;
	
	public static final int BTNREMOVEITEM = 4;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Nonprofit myNonprofit;
	
	private JTextArea myDisplayLabel;
	
	private JButton myBtnSubmitAuctionRequest;
	
	private JButton myBtnCancelAuctionRequest;
	
	private JButton myBtnAddItem;
	
	private JButton myBtnRemoveItem;

		
	public NonprofitPanel (Nonprofit theNonprofit) {
		super();
		myNonprofit = theNonprofit;
		this.setLayout(null);
		mainMenu();
		setVisible(true);

	}
	
	private void mainMenu() {
		//Create and initialize every button
		
		myBtnSubmitAuctionRequest = new JButton("Proceed");
		myBtnCancelAuctionRequest = new JButton("Proceed");
		myBtnAddItem = new JButton("Proceed");
		myBtnRemoveItem = new JButton("Proceed");
		
		
		initializeMenu("Main Menu");
		myDisplayLabel.setText(myNonprofit.getName() + " logged in as a Nonprofit");
		
		
		//Submit Auction Request Button
		myBtnSubmitAuctionRequest.setBounds(580, 370, 100, 50);
		add(myBtnSubmitAuctionRequest);
		
		myBtnSubmitAuctionRequest.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				submitAuctionRequestMenu();
			}
		});
		
		//Cancel Auction Request Button
		myBtnCancelAuctionRequest.setBounds(580, 440, 100, 50);
		add(myBtnCancelAuctionRequest);
		
		myBtnCancelAuctionRequest.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CancelAuctionRequestMenu();
			}
		});

		//Add Item Button
		
		myBtnAddItem.setBounds(580, 510, 100, 50);
		add(myBtnAddItem);
		
		myBtnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddItemMenu();
			}
		});

		//Remove Item Button
		myBtnRemoveItem.setBounds(580, 580, 100, 50);
		add(myBtnRemoveItem);
		
		myBtnRemoveItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveItemMenu();
				
			}
		});

		
		repaint();
	}
	
	private void submitAuctionRequestMenu() {
		initializeMenu("Submit Auction Request");
		
	}
	
	private void CancelAuctionRequestMenu() {
		initializeMenu("Cancel Auction Request");
		myDisplayLabel.setText("2");

	}
	
	private void AddItemMenu() {
		initializeMenu("Add Item");
		
		myDisplayLabel.setText("In order to add an item,"
				+ "you have to enter the following information:\n"
				+ "-Item Name\n"
				+ "-Donor Name\n"
				+ "-Quantity\n"
				+ "-Item Condition\n"
				+ "-Item Size\n"
				+ "-Starting Bid");
		
		//Initializing the form for adding an item
		JLabel labelItemName = new JLabel("Item Name:");
		labelItemName.setBounds(20, 370, 100, 20);
		add(labelItemName);
		
		JTextField fieldItemName = new JTextField();
		fieldItemName.setBounds(140, 370, 300, 20);
		add(fieldItemName);
		
		JLabel labelDonorName = new JLabel("Donor Name:");
		labelDonorName.setBounds(20, 410, 100, 20);
		add(labelDonorName);
		
		JTextField fieldDonorName = new JTextField();
		fieldDonorName.setBounds(140, 410, 300, 20);
		add(fieldDonorName);
		
		JLabel labelQuantity = new JLabel("Quantity:");
		labelQuantity.setBounds(20, 450, 100, 20);
		add(labelQuantity);
		
		JTextField fieldQuantity = new JTextField();
		fieldQuantity.setBounds(140, 450, 300, 20);
		add(fieldQuantity);
		
		JLabel labelItemCondition = new JLabel("Item Condition:");
		labelItemCondition.setBounds(20, 490, 100, 20);
		add(labelItemCondition);
		
		JTextField fieldItemCondition = new JTextField();
		fieldItemCondition.setBounds(140, 490, 300, 20);
		add(fieldItemCondition);
		
		JLabel labelItemSize = new JLabel("Item Size:");
		labelItemSize.setBounds(20, 530, 100, 20);
		add(labelItemSize);
		
		JTextField fieldItemSize = new JTextField();
		fieldItemSize.setBounds(140, 530, 300, 20);
		add(fieldItemSize);
		
		JLabel labelStartingBid = new JLabel("Starting Bid:");
		labelStartingBid.setBounds(20, 570, 100, 20);
		add(labelStartingBid);
		
		JTextField fieldStartingBid = new JTextField();
		fieldStartingBid.setBounds(140, 570, 300, 20);
		add(fieldStartingBid);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(580, 370, 100, 70);
		add(btnSubmit);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(580, 550, 100, 70);
		add(btnBack);
		
	}
	
	private void RemoveItemMenu() {
		initializeMenu("Remove Item");
		myDisplayLabel.setText("4");
	}
	
	private void initializeMenu(String theTitle) {
		this.removeAll();

		
		myDisplayLabel = new JTextArea();
		myDisplayLabel.setFont(myDisplayLabel.getFont().deriveFont(16f));
		myDisplayLabel.setEditable(false);
		myDisplayLabel.setBounds(new Rectangle(20, 20, 660, 330));
		TitledBorder title;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(blackline, theTitle);
		title.setTitleJustification(TitledBorder.CENTER);
		myDisplayLabel.setBorder(title);
		add(myDisplayLabel);
		
		repaint();
	}
	
	public void displayUpcomingAuction(String theName, Month theMonth, int theDay, int theYear) {
		myDisplayLabel.append("\n\n");
		myDisplayLabel.append(String.format("You have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
				theName, theMonth, theDay, theYear));
	}
	
	public void disableButton(int theButtonCode) {
		switch (theButtonCode) {
			case 1: myBtnSubmitAuctionRequest.setEnabled(false);
					break;
			case 2: myBtnCancelAuctionRequest.setEnabled(false);
					break;
			case 3: myBtnAddItem.setEnabled(false);
					break;
			case 4: myBtnRemoveItem.setEnabled(false);
					break;
			default: break;
		}
	}
}
