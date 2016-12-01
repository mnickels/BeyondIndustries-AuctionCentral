package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Auction;
import model.Data;
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
	
	private JButton myYes;
	
	private JButton myNo;
	
	private JButton myYes2;
	
	private JButton myNo2;
	
	private JButton myBtnAddItem;
	
	private JButton myBtnRemoveItem;
	
	private Data myData;

		
	public NonprofitPanel (Nonprofit theNonprofit) {
		super();
		myNonprofit = theNonprofit;
		myData = Data.getInstance();
		this.setLayout(null);
		mainMenu();
		setVisible(true);
		

	}
	
	private void mainMenu() {
		//Create and initialize every button
		myBtnSubmitAuctionRequest = new JButton("Submit Auction Request");
		myBtnCancelAuctionRequest = new JButton("Cancel Auction Request");
		myBtnAddItem = new JButton("Add Item");
		myBtnRemoveItem = new JButton("Remove Item");
		
		
		initializeMenu("Main Menu");
		myDisplayLabel.setText(myNonprofit.getName() + " logged in as a Nonprofit");
		
		
		//Submit Auction Request Button
		myBtnSubmitAuctionRequest.setBounds(580, 370, 200, 50);
		add(myBtnSubmitAuctionRequest);
		
		myBtnSubmitAuctionRequest.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				submitAuctionRequestMenu();
			}
		});
		
		//Cancel Auction Request Button
		myBtnCancelAuctionRequest.setBounds(580, 440, 200, 50);
		add(myBtnCancelAuctionRequest);
		
		myBtnCancelAuctionRequest.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CancelAuctionRequestMenu();
			}
		});

		//Add Item Button
		
		myBtnAddItem.setBounds(580, 510, 200, 50);
		add(myBtnAddItem);
		
		myBtnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddItemMenu();
			}
		});

		//Remove Item Button
		myBtnRemoveItem.setBounds(580, 580, 200, 50);
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
		myDisplayLabel.setText("In order to submit an auction request,"
				+ "you have to enter the following information:\n"
				+ "-Organization Name\n"
				+ "-Auction Date (YYYY-MM-DDTHH:MM)(MilitaryTime)\n"
				+ "-Number of approximate Items\n"
				+ "-Auction Description\n");
		
		//Initializing the form for submitting an auction request
				JLabel labelOrgName = new JLabel("Organization Name:");
				labelOrgName.setBounds(20, 370, 150, 20);
				add(labelOrgName);
				
				JTextField fieldOrgName = new JTextField();
				fieldOrgName.setBounds(190, 370, 300, 20);
				add(fieldOrgName);
				
				JLabel labelDateAndTime = new JLabel("Auction Date and Time:");
				labelDateAndTime.setBounds(20, 410, 150, 20);
				add(labelDateAndTime);
				
				JTextField fieldDateAndTime = new JTextField();
				fieldDateAndTime.setBounds(190, 410, 300, 20);
				add(fieldDateAndTime);				
				
				JLabel labelItemNumber = new JLabel("Number of approximate Items:");
				labelItemNumber.setBounds(20, 450, 150, 20);
				add(labelItemNumber);
				
				JTextField fieldItemNumber = new JTextField();
				fieldItemNumber.setBounds(190, 450, 300, 20);
				add(fieldItemNumber);
				
				JLabel labelAuctionDescript = new JLabel("Auction Description:");
				labelAuctionDescript.setBounds(20, 490, 150, 20);
				add(labelAuctionDescript);
				
				JTextField fieldAuctionDescript = new JTextField();
				fieldAuctionDescript.setBounds(190, 490, 300, 20);
				add(fieldAuctionDescript);
				
				JButton btnSubmitAuction = new JButton("Submit");
				btnSubmitAuction.setBounds(580, 370, 100, 70);
				add(btnSubmitAuction);
				
				JButton btnBackAuction = new JButton("Back");
				btnBackAuction.setBounds(580, 550, 100, 70);
				add(btnBackAuction);
				
				btnSubmitAuction.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String orgName = fieldOrgName.getText();
						
						String numberItems = fieldItemNumber.getText();
						
						String auctionDesc = fieldAuctionDescript.getText();
						
						String dateTime = fieldDateAndTime.getText();
						DateTimeFormatter newFormat = DateTimeFormatter.ISO_DATE_TIME;
						LocalDateTime auctionDateTime = LocalDateTime.parse(dateTime, newFormat);
						double numberOfItems = Double.parseDouble(numberItems);
						Auction newAuction = new Auction(myNonprofit, auctionDateTime, orgName, auctionDesc);

						if(myData.addAuction(newAuction) == true){
							myBtnCancelAuctionRequest.setEnabled(true);
							myBtnAddItem.setEnabled(true);
							myBtnRemoveItem.setEnabled(false);
							myBtnSubmitAuctionRequest.setEnabled(false);
						} else if(myData.auctionMoreThan2Day(newAuction) == true) {
							JOptionPane.showMessageDialog(btnSubmitAuction, "I'm sorry, you cannot submit this auction as there "
									+ "is already 2 auctions schedualed for the day you specified");
						} else if(myData.auctionExceedsMax(newAuction) == true) {
							JOptionPane.showMessageDialog(btnSubmitAuction, "I'm sorry, you cannot submit this auction at this time as there "
									+ "is already the max number of auctions allowed in the system at this time");
						} else if(myData.auctionPlannedWeekAhead(newAuction) == true) {
							JOptionPane.showMessageDialog(btnSubmitAuction, "I'm sorry, you cannot submit this auction as you "
									+ "must submit your auction at least one week into the future");
						} else {
							JOptionPane.showMessageDialog(btnSubmitAuction, "I'm sorry, you cannot submit this auction as you "
									+ "must submit your auction at most no more than one month into the future");
						}

						
					}
				});
				
				btnBackAuction.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainMenu();
					}
				});
		
	}
	
	private void CancelAuctionRequestMenu() {
		initializeMenu("Cancel Auction Request");
		Auction currentAuction = Data.getInstance().getAuctionForThisNonprofit(myNonprofit);
		String theName = myNonprofit.getName();
		Month theMonth = currentAuction.getDate().getMonth();
		int theDay = currentAuction.getDate().getDayOfMonth();
		int theYear = currentAuction.getDate().getYear();
		myDisplayLabel.append(String.format("You have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
				theName, theMonth, theDay, theYear));
		myDisplayLabel.append("\n\nWould you like to remove this auction?");
		
		myYes = new JButton("Yes");
		myNo = new JButton("No");
		
		//Add Yes Button
		
		myYes.setBounds(480, 510, 200, 50);
		add(myYes);
				
		myYes.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initializeMenu("Confirmation");
				myDisplayLabel.setText("Are you sure you want to remove this auction?");
				myYes2 = new JButton("Yes");
				myNo2 = new JButton("No");
				
				//Add Yes Button
				
				myYes2.setBounds(480, 510, 200, 50);
				add(myYes2);
						
				myYes2.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (myData.removeAuction(currentAuction) == true) {
							JOptionPane.showMessageDialog(myYes2, "You have successfully removed this auction");
							mainMenu();
							myBtnCancelAuctionRequest.setEnabled(false);
							myBtnAddItem.setEnabled(false);
							myBtnRemoveItem.setEnabled(false);
							myBtnSubmitAuctionRequest.setEnabled(true);
						} else {
							JOptionPane.showMessageDialog(myYes2, "You cannot cancel your auction as it is within 2 days of the date your auction is set for.");
						}	
					}
				});
				
				//Add No Button
				
				myNo2.setBounds(480, 580, 200, 50);
				add(myNo2);
						
				myNo2.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						CancelAuctionRequestMenu();
					}
				});
			}
		});
		
		//Add No Button
		
		myNo.setBounds(480, 580, 200, 50);
		add(myNo);
				
		myNo.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainMenu();
			}
		});

		this.revalidate();
		this.repaint();
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
		
		JButton btnAddItem = new JButton("Submit");
		btnAddItem.setBounds(680, 370, 100, 70);
		add(btnAddItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setBounds(680, 460, 100, 70);
		btnCancelItem.setVisible(false);
		add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setBounds(680, 550, 100, 70);
		add(btnBackItem);
		
		btnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnAddItem.getText().equals("Submit")) {
					btnAddItem.setForeground(Color.RED);
					btnAddItem.setText("Comfirm");
					btnCancelItem.setVisible(true);
				} else {
					mainMenu();
				}
			}
		});
		
		btnBackItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainMenu();
			}
		});
		
	}
	
	private void RemoveItemMenu() {
		initializeMenu("Remove Item");
		myDisplayLabel.setBounds(new Rectangle(20, 20, 760, 100));
		myDisplayLabel.setText("Below is a list of all the items in the Auction\n"
				+ "Choose the one you want to remove and click on the remove button.");
		String[] columnNames = {"Name", "Donor", "Quantity", "Condition", "Size", "Starting Bid"};
		Object[][] auctionItems = myData.getAuctionItems(myNonprofit);
		JTable itemTable = new JTable(auctionItems, columnNames);
		itemTable.setBounds(new Rectangle(20, 120, 760, 300));
		itemTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(itemTable);
		itemTable.setFillsViewportHeight(true);
		scrollPane.setBounds(new Rectangle(20, 120, 760, 300));
		add(scrollPane);

		String[] itemNames = new String[auctionItems.length];
		for (int i = 0; i < auctionItems.length; i++) {
			itemNames[i] = (String) auctionItems[i][0];
		}
				
	    JComboBox itemList = new JComboBox(itemNames);
	    itemList.setSelectedIndex(0);
		itemList.setBounds(new Rectangle(20, 440, 250, 20));
		add(itemList);
		
		JButton btnAddItem = new JButton("Submit");
		btnAddItem.setBounds(680, 370, 100, 70);
		add(btnAddItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setBounds(680, 460, 100, 70);
		btnCancelItem.setVisible(false);
		add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setBounds(680, 550, 100, 70);
		add(btnBackItem);
		
		btnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnAddItem.getText().equals("Submit")) {
					btnAddItem.setForeground(Color.RED);
					btnAddItem.setText("Comfirm");
					btnCancelItem.setVisible(true);
				} else {
					mainMenu();
				}
			}
		});
		
		btnBackItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainMenu();
			}
		});
		
		this.revalidate();
		this.repaint();
	}
	
	private void initializeMenu(String theTitle) {
		this.removeAll();

		
		myDisplayLabel = new JTextArea();
		myDisplayLabel.setFont(myDisplayLabel.getFont().deriveFont(16f));
		myDisplayLabel.setEditable(false);
		myDisplayLabel.setBounds(new Rectangle(20, 20, 760, 330));
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
