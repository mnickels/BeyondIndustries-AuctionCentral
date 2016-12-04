package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import model.Item;
import model.users.Nonprofit;

public class NonprofitPanel extends JPanel {
	
	public static final int BTNSUBMITAUCTIONREQUEST = 1;
	
	public static final int BTNCANCELAUCTIONREQUEST = 2;
	
	public static final int BTNADDITEM = 3;
	
	public static final int BTNREMOVEITEM = 4;
	
	private static final long serialVersionUID = 1L;
	
	private Nonprofit myNonprofit;
	
	private JTextArea myDisplayLabel;
	
	private JButton myBtnSubmitAuctionRequest;
	
	private JButton myBtnCancelAuctionRequest;
	
	private JButton myConfirm;
	
	private JButton myCancel;
	
	private JButton myConfirm2;
	
	private JButton myCancel2;
	
	private JButton myConfirm3;
	
	private JButton myCancel3;
	
	private JButton myReturn;
	
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
		if(myData.nonprofitHasAuction(myNonprofit) == true){
			Auction myAuction = myData.getAuctionForThisNonprofit(myNonprofit);
			myDisplayLabel.append(String.format("\nYou have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
					myAuction.getName(), myAuction.getDate().getMonth(), 
					myAuction.getDate().getDayOfMonth(), myAuction.getDate().getYear()));
			myBtnCancelAuctionRequest.setEnabled(true);
			myBtnAddItem.setEnabled(true);
			myBtnRemoveItem.setEnabled(true);
			myBtnSubmitAuctionRequest.setEnabled(false);
			
			if (myAuction.getSize() == 0) {
				myBtnRemoveItem.setEnabled(false);
			}
		} else {
			myBtnCancelAuctionRequest.setEnabled(false);
			myBtnAddItem.setEnabled(false);
			myBtnRemoveItem.setEnabled(false);
			myBtnSubmitAuctionRequest.setEnabled(true);
		}
		
		
		
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
				+ "-Number of approximate Items (Optional)\n"
				+ "-Auction Description (Optional)\n");
		
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
				
				JButton btnConfirmAuction = new JButton("Confirm");
				btnConfirmAuction.setBounds(580, 370, 100, 70);
				add(btnConfirmAuction);
				
				JButton btnCancelAuction = new JButton("Cancel");
				btnCancelAuction.setBounds(580, 550, 100, 70);
				add(btnCancelAuction);
				
				btnConfirmAuction.addActionListener(new ActionListener () {

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
							initializeMenu("Confirmation");
							myDisplayLabel.append(String.format("Are you sure you want to submit this auction?"
									+ "\nAuction Name: %s\n\n"
									+ "Date: %s %d, %d\n"
									+ "Description: %s\n"
									+ "Number of approximate Items: %s\n"
									, orgName, auctionDateTime.getMonth(), 
									auctionDateTime.getDayOfMonth(), auctionDateTime.getYear(),
									auctionDesc, numberOfItems));
							myConfirm3 = new JButton("Confirm");
							myCancel3 = new JButton("Cancel");
							
							myConfirm3.setBounds(480, 510, 200, 50);
							add(myConfirm3);
							
							myConfirm3.addActionListener(new ActionListener () {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									initializeMenu("Congrats");
									myDisplayLabel.append(String.format("You have successfully submitted your auction"
											+ "\nAuction Name: %s\n\n"
											+ "Date: %s %d, %d\n"
											+ "Description: %s\n"
											+ "Number of approximate Items: %s\n"
											, orgName, auctionDateTime.getMonth(), 
											auctionDateTime.getDayOfMonth(), auctionDateTime.getYear(),
											auctionDesc, numberOfItems));
									myReturn = new JButton("Return");
									
									myReturn.setBounds(480, 510, 200, 50);
									add(myReturn);
									
									myReturn.addActionListener(new ActionListener () {

										@Override
										public void actionPerformed(ActionEvent arg0) {
											mainMenu();
											myBtnRemoveItem.setEnabled(false);
										}
									});
								}
							});
							
							//Add No Button
							
							myCancel3.setBounds(480, 580, 200, 50);
							add(myCancel3);
									
							myCancel3.addActionListener(new ActionListener () {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									CancelAuctionRequestMenu();
								}
							});
							
						} else if(myData.auctionMoreThan2Day(newAuction) == true) {
							JOptionPane.showMessageDialog(btnConfirmAuction, "I'm sorry, you cannot submit this auction as there "
									+ "is already 2 auctions schedualed for the day you specified");
						} else if(myData.auctionExceedsMax(newAuction) == true) {
							JOptionPane.showMessageDialog(btnConfirmAuction, "I'm sorry, you cannot submit this auction at this time as there "
									+ "is already the max number of auctions allowed in the system at this time");
						} else if(myData.auctionPlannedWeekAhead(newAuction) == true) {
							JOptionPane.showMessageDialog(btnConfirmAuction, "I'm sorry, you cannot submit this auction as you "
									+ "must submit your auction at least one week into the future");
						} else {
							JOptionPane.showMessageDialog(btnConfirmAuction, "I'm sorry, you cannot submit this auction as you "
									+ "must submit your auction at most no more than one month into the future");
						}

						
					}
				});
				
				btnCancelAuction.addActionListener(new ActionListener () {

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
		
		myConfirm = new JButton("Confirm");
		myCancel = new JButton("Cancel");
		
		//Add Yes Button
		
		myConfirm.setBounds(480, 510, 200, 50);
		add(myConfirm);
				
		myConfirm.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initializeMenu("Confirmation");
				myDisplayLabel.setText("Are you sure you want to remove this auction?");
				myConfirm2 = new JButton("Confrim");
				myCancel2 = new JButton("Cancel");
				
				//Add Yes Button
				
				myConfirm2.setBounds(480, 510, 200, 50);
				add(myConfirm2);
						
				myConfirm2.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (myData.removeAuction(currentAuction) == true) {
							JOptionPane.showMessageDialog(myConfirm2, "You have successfully removed this auction");
							mainMenu();
						} else {
							JOptionPane.showMessageDialog(myConfirm2, "You cannot cancel your auction as it is within 2 days of the date your auction is set for.");
						}	
					}
				});
				
				//Add No Button
				
				myCancel2.setBounds(480, 580, 200, 50);
				add(myCancel2);
						
				myCancel2.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						CancelAuctionRequestMenu();
					}
				});
			}
		});
		
		//Add No Button
		
		myCancel.setBounds(480, 580, 200, 50);
		add(myCancel);
				
		myCancel.addActionListener(new ActionListener () {

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
		
		myDisplayLabel.setText("In order to add an item,\n"
				+ "The following information are required:\n"
				+ "-Item Name\n-Quantity\n-Item Condition\n"
				+ "-Item Size\n-Starting Bid\n\n"
				+ "The following information are optional:\n"
				+ "-Donor Name\n-Item Description\n-Comment");
		
		//Initializing the form for adding an item
        Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		JLabel labelItemName = new JLabel("Item Name:");
		labelItemName.setBounds(20, 370, 80, 20);
		add(labelItemName);
		
		JTextField fieldItemName = new JTextField();
		fieldItemName.setBounds(120, 370, 300, 20);
		fieldItemName.setBorder(fieldBorder);
		add(fieldItemName);
		
		JLabel labelDonorName = new JLabel("Donor Name:");
		labelDonorName.setBounds(20, 400, 80, 20);
		add(labelDonorName);
		
		JTextField fieldDonorName = new JTextField();
		fieldDonorName.setBounds(120, 400, 300, 20);
		fieldDonorName.setBorder(fieldBorder);
		add(fieldDonorName);
		
		JLabel labelQuantity = new JLabel("Quantity:");
		labelQuantity.setBounds(20, 430, 80, 20);
		add(labelQuantity);
		
		JTextField fieldQuantity = new JTextField();
		fieldQuantity.setBounds(120, 430, 300, 20);
		fieldQuantity.setBorder(fieldBorder);
		add(fieldQuantity);
		
		String[] itemConditionList = {"Acceptable", "Good", "Very Good", "Like New", "New"};
		String[] itemSizeList = {"Small", "Medium", "Large"};
		
		JLabel labelItemCondition = new JLabel("Item Condition:");
		labelItemCondition.setBounds(20, 460, 100, 20);
		add(labelItemCondition);
		
		JComboBox boxItemCondition = new JComboBox(itemConditionList);
		boxItemCondition.setBackground(Color.WHITE);
		boxItemCondition.setSelectedIndex(0);		
		boxItemCondition.setBounds(120, 460, 300, 20);
		boxItemCondition.setBorder(fieldBorder);
		add(boxItemCondition);
		
		JLabel labelItemSize = new JLabel("Item Size:");
		labelItemSize.setBounds(20, 490, 80, 20);
		add(labelItemSize);
		
		JComboBox boxItemSize = new JComboBox(itemSizeList);
		boxItemSize.setBackground(Color.WHITE);
		boxItemSize.setSelectedIndex(0);
		boxItemSize.setBounds(120, 490, 300, 20);
		boxItemSize.setBorder(fieldBorder);
		add(boxItemSize);
		
		JLabel labelStartingBid = new JLabel("Starting Bid:");
		labelStartingBid.setBounds(20, 520, 80, 20);
		add(labelStartingBid);
		
		JTextField fieldStartingBid = new JTextField();
		fieldStartingBid.setBounds(120, 520, 300, 20);
		fieldStartingBid.setBorder(fieldBorder);
		add(fieldStartingBid);
				
		JLabel labelItemDescription = new JLabel("Item Description");
		labelItemDescription.setBounds(440, 370, 100, 20);
		add(labelItemDescription);
		
		JTextArea fieldItemDescription = new JTextArea();
		fieldItemDescription.setBounds(440, 400, 340, 140);
		fieldItemDescription.setBorder(fieldBorder);
		fieldItemDescription.setLineWrap(true);
		add(fieldItemDescription);
		
		/*
		JLabel labelComment = new JLabel("Comment");
		labelComment.setBounds(440, 460, 100, 20);
		add(labelComment);
		
		JTextArea fieldComment = new JTextArea();
		fieldComment.setBounds(440, 490, 340, 50);
		fieldComment.setBorder(fieldBorder);
		fieldComment.setLineWrap(true);
		add(fieldComment);
		*/
		
		JButton btnAddItem = new JButton("Submit");
		btnAddItem.setBounds(560, 560, 100, 70);
		add(btnAddItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setBounds(680, 560, 100, 70);
		btnCancelItem.setVisible(false);
		add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setBounds(680, 560, 100, 70);
		add(btnBackItem);
		
		btnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnAddItem.getText().equals("Submit")) {
					btnAddItem.setForeground(Color.RED);
					btnAddItem.setText("Comfirm");
					btnCancelItem.setVisible(true);
					btnBackItem.setVisible(false);
				} else {
					if (fieldItemName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Item name is required!");
					} else if (fieldQuantity.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Item quantity is required!");
					} else if (fieldStartingBid.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Starting bid is required!");
					} else {
						Item tempItem = new Item(fieldItemName.getText(), fieldDonorName.getText(), 
								Integer.parseInt(fieldQuantity.getText()), (String) boxItemCondition.getSelectedItem(), 
								(String) boxItemSize.getSelectedItem(), null, 
								new BigDecimal(Integer.parseInt(fieldStartingBid.getText())), fieldItemDescription.getText());
						Auction currentAuction = myData.getInstance().getAuctionForThisNonprofit(myNonprofit);
						boolean result = currentAuction.addItem(tempItem);
						if (result) {
							JOptionPane.showMessageDialog(null, "You have successfully added an item");
							mainMenu();
						} else {
							JOptionPane.showMessageDialog(null, "Sorry, you cannot add the same item twice");
						}

					}
					
					
				}
			}
		});
		
		btnCancelItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnAddItem.setForeground(Color.BLACK);
				btnAddItem.setText("Submit");
				
				btnCancelItem.setVisible(false);
				btnBackItem.setVisible(true);
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
		itemList.setBounds(new Rectangle(225, 440, 250, 20));
		add(itemList);
		
		JButton btnRemoveItem = new JButton("Submit");
		btnRemoveItem.setBounds(240, 550, 100, 70);
		add(btnRemoveItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setBounds(360, 550, 100, 70);
		btnCancelItem.setVisible(false);
		add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setBounds(360, 550, 100, 70);
		add(btnBackItem);
		
		JButton btnConfirm = new JButton("OK");
		btnConfirm.setBounds(300, 550, 100, 70);
		btnConfirm.setVisible(false);
		add(btnConfirm);
		
		btnRemoveItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnRemoveItem.getText().equals("Submit")) {
					btnRemoveItem.setForeground(Color.RED);
					btnRemoveItem.setText("Comfirm");
					btnCancelItem.setVisible(true);
					btnBackItem.setVisible(false);
					itemList.setEnabled(false);
				} else {
					String itemName = (String) itemList.getSelectedItem();
					Item tempItem = new Item(itemName, null, 0, null, null, null, new BigDecimal(0), null);
					int errorCode = myData.removeItemInAuction(myNonprofit, tempItem);


					if (errorCode == Auction.REMOVEITEMNOTEXIST) {
						JOptionPane.showMessageDialog(null, "Failed to Remove selected item "
								+ "because it doesn't exist!");
						
						btnRemoveItem.setVisible(false);
						btnCancelItem.setVisible(false);
						btnConfirm.setVisible(true);
						
					} else if (errorCode == Auction.REMOVEITEMWITHINTWODAYS) {
						JOptionPane.showMessageDialog(null, "Failed to Remove selected item"
								+ " because remove application is submitted within 2 days.");
						
						btnRemoveItem.setVisible(false);
						btnCancelItem.setVisible(false);
						btnConfirm.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "You have successfully removed an item");
						mainMenu();
					}
				}
			}
		});
		
		btnCancelItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnRemoveItem.setForeground(Color.BLACK);
				btnRemoveItem.setText("Submit");
				btnCancelItem.setVisible(false);
				btnBackItem.setVisible(true);
				itemList.setEnabled(true);
			}
		});
		
		btnBackItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainMenu();
			}
		});
		
		btnConfirm.addActionListener(new ActionListener () {

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
}
