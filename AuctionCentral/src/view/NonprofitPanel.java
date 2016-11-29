package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Nonprofit myNonprofit;
	
	private JTextArea myDisplayLabel;
		
	public NonprofitPanel (Nonprofit theNonprofit) {
		super();
		myNonprofit = theNonprofit;
		this.setLayout(null);
		mainMenu();
		setVisible(true);

	}
	
	private void mainMenu() {
		//Create and initialize every button
		
		JButton btnSubmitAuctionRequest, btnCancelAuctionRequest, btnAddItem, btnRemoveItem;
		btnSubmitAuctionRequest = new JButton("Proceed");
		btnCancelAuctionRequest = new JButton("Proceed");
		btnAddItem = new JButton("Proceed");
		btnRemoveItem = new JButton("Proceed");
		
		//Add button to the panel		
		
		initializeMenu("Main Menu");
		
		//Submit Auction Request Button
		btnSubmitAuctionRequest.setBounds(580, 370, 100, 50);
		add(btnSubmitAuctionRequest);
		
		btnSubmitAuctionRequest.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				submitAuctionRequestMenu();
			}
		});
		
		//Cancel Auction Request Button
		btnCancelAuctionRequest.setBounds(580, 440, 100, 50);
		add(btnCancelAuctionRequest);
		
		btnCancelAuctionRequest.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CancelAuctionRequestMenu();
			}
		});

		//Add Item Button
		
		btnAddItem.setBounds(580, 510, 100, 50);
		add(btnAddItem);
		
		btnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddItemMenu();
			}
		});

		//Remove Item Button
		btnRemoveItem.setBounds(580, 580, 100, 50);
		add(btnRemoveItem);
		
		btnRemoveItem.addActionListener(new ActionListener () {

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
		myDisplayLabel.setEditable(false);
		myDisplayLabel.setBounds(new Rectangle(20, 20, 660, 330));
		TitledBorder title;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(
                blackline, theTitle);
		title.setTitleJustification(TitledBorder.CENTER);
		myDisplayLabel.setBorder(title);
		add(myDisplayLabel);
		
		repaint();
	}
}
