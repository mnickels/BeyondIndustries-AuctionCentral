package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import java.awt.FlowLayout;

public class NonprofitPanel extends JPanel {
	
	public static final int BTNSUBMITAUCTIONREQUEST = 1;
	
	public static final int BTNCANCELAUCTIONREQUEST = 2;
	
	public static final int BTNADDITEM = 3;
	
	public static final int BTNREMOVEITEM = 4;
	
	public static final Dimension MAINMENUBTNDIM = new Dimension(170, 50);
	
	/**
	 * 
	 */
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

	private FlowLayout myLayout;
		
	public NonprofitPanel (Nonprofit theNonprofit) {
		super();
		myNonprofit = theNonprofit;
		myData = Data.getInstance();

		this.setPreferredSize(new Dimension(800, 700));
		myLayout = new FlowLayout();
		this.setLayout(myLayout);
		
		mainMenu();
		setVisible(true);
	}
	
	private void mainMenu() {
		//Create and initialize every button
		
		
		initializeMenu("Main Menu");
		
		
		myBtnSubmitAuctionRequest = new JButton("Submit Auction Request");
		myBtnSubmitAuctionRequest.setPreferredSize(MAINMENUBTNDIM);
		myBtnCancelAuctionRequest = new JButton("Cancel Auction Request");
		myBtnCancelAuctionRequest.setPreferredSize(MAINMENUBTNDIM);
		myBtnAddItem = new JButton("Add Item");
		myBtnAddItem.setPreferredSize(MAINMENUBTNDIM);
		myBtnRemoveItem = new JButton("Remove Item");
		myBtnRemoveItem.setPreferredSize(MAINMENUBTNDIM);
					
		add(myBtnSubmitAuctionRequest);
		add(myBtnCancelAuctionRequest);
		add(myBtnAddItem);
		add(myBtnRemoveItem);

		myLayout.setVgap(50);

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
		
		myBtnCancelAuctionRequest.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CancelAuctionRequestMenu();
			}
		});
		
		myBtnAddItem.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddItemMenu();
			}
		});
		
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
		
		myLayout = new FlowLayout(FlowLayout.CENTER, 40, 50);
		this.setLayout(myLayout);
		
		
		myDisplayLabel.setText("In order to submit an auction request,"
				+ "you have to enter the following information:\n"
				+ "-Organization Name\n"
				+ "-Auction Date (YYYY-MM-DDTHH:MM)(MilitaryTime)\n"
				+ "-Number of approximate Items (Optional)\n"
				+ "-Auction Description (Optional)\n");
		
		//Initializing the form for submitting an auction request
				Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
				JPanel leftFormPanel = new JPanel();
		        leftFormPanel.setPreferredSize(new Dimension(450, 200));
		        leftFormPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		        add(leftFormPanel);
				
				JLabel labelOrgName = new JLabel("Organization Name:");
				labelOrgName.setPreferredSize(new Dimension(150, 20));
				leftFormPanel.add(labelOrgName);
				
				JTextField fieldOrgName = new JTextField();
				fieldOrgName.setPreferredSize(new Dimension(300, 20));
				fieldOrgName.setBorder(fieldBorder);
				leftFormPanel.add(fieldOrgName);
				
				JLabel labelDateAndTime = new JLabel("Auction Date and Time:");
				labelDateAndTime.setPreferredSize(new Dimension(150, 20));
				leftFormPanel.add(labelDateAndTime);
				
				JTextField fieldDateAndTime = new JTextField();
				fieldDateAndTime.setPreferredSize(new Dimension(300, 20));
				fieldDateAndTime.setBorder(fieldBorder);
				leftFormPanel.add(fieldDateAndTime);				
				
				JLabel labelItemNumber = new JLabel("Approximate Items:");
				labelItemNumber.setPreferredSize(new Dimension(150, 20));
				leftFormPanel.add(labelItemNumber);
				
				JTextField fieldItemNumber = new JTextField();
				fieldItemNumber.setPreferredSize(new Dimension(300, 20));
				fieldItemNumber.setBorder(fieldBorder);
				leftFormPanel.add(fieldItemNumber);
				
				JLabel labelAuctionDescript = new JLabel("Auction Description:");
				labelAuctionDescript.setPreferredSize(new Dimension(150, 20));
				leftFormPanel.add(labelAuctionDescript);
				
				JTextArea fieldAuctionDescript = new JTextArea();
				fieldAuctionDescript.setPreferredSize(new Dimension(300, 20));
				fieldAuctionDescript.setBorder(fieldBorder);
				fieldAuctionDescript.setLineWrap(true);
				leftFormPanel.add(fieldAuctionDescript);
				
				JPanel rightFormPanel = new JPanel();
				rightFormPanel.setPreferredSize(new Dimension(100, 200));
				rightFormPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		        add(rightFormPanel);
				
				JButton btnConfirmAuction = new JButton("Confirm");
				btnConfirmAuction.setPreferredSize(new Dimension(100, 60));
				rightFormPanel.add(btnConfirmAuction);
				
				JButton btnCancelAuction = new JButton("Cancel");
				btnCancelAuction.setPreferredSize(new Dimension(100, 60));
				rightFormPanel.add(btnCancelAuction);
				
				repaint();
				
				btnConfirmAuction.addActionListener(new ActionListener () {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String orgName = fieldOrgName.getText();
						
						String numberItems = fieldItemNumber.getText();
						
						String auctionDesc = fieldAuctionDescript.getText();
						
						String dateTime = fieldDateAndTime.getText();
						DateTimeFormatter newFormat = DateTimeFormatter.ISO_DATE_TIME;
						LocalDateTime auctionDateTime = LocalDateTime.parse(dateTime, newFormat);
						int numberOfItems = Integer.parseInt(numberItems);
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

									add(myReturn);
									
									myReturn.addActionListener(new ActionListener () {

										@Override
										public void actionPerformed(ActionEvent arg0) {
											mainMenu();
										}
									});
								}
							});
							
							//Add No Button
							
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
		repaint();		
		
	}
	
	private void CancelAuctionRequestMenu() {
		initializeMenu("Cancel Auction Request");
		
		myLayout = new FlowLayout(FlowLayout.CENTER, 20, 50);
		this.setLayout(myLayout);
		
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
		
		myConfirm.setPreferredSize(new Dimension(100, 70));
		add(myConfirm);
				
		myConfirm.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				initializeMenu("Confirmation");
				myDisplayLabel.setText("Are you sure you want to remove this auction?");
				myConfirm2 = new JButton("Confirm");
				myConfirm2.setForeground(Color.RED);
				myCancel2 = new JButton("Cancel");
				
				//Add Yes Button
				
				myConfirm2.setPreferredSize(new Dimension(100, 70));
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
				
				myCancel2.setPreferredSize(new Dimension(100, 70));
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
		
		myCancel.setPreferredSize(new Dimension(100, 70));
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
		
		myLayout = new FlowLayout(FlowLayout.CENTER, 20, 0);
		this.setLayout(myLayout);
		
		myDisplayLabel.setText("In order to add an item,\n"
				+ "The following information are required:\n"
				+ "-Item Name\n-Quantity\n-Item Condition\n"
				+ "-Item Size\n-Starting Bid\n\n"
				+ "The following information are optional:\n"
				+ "-Donor Name\n-Item Description\n-Comment");
		
		//Initializing the form for adding an item
        Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
		
        JPanel leftFormPanel = new JPanel();
        leftFormPanel.setPreferredSize(new Dimension(400, 200));
        leftFormPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        add(leftFormPanel);
        
		JLabel labelItemName = new JLabel("Item Name:");
		labelItemName.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelItemName);
		
		JTextField fieldItemName = new JTextField();
		fieldItemName.setPreferredSize(new Dimension(300, 20));
		fieldItemName.setBorder(fieldBorder);
		leftFormPanel.add(fieldItemName);
		
		JLabel labelDonorName = new JLabel("Donor Name:");
		labelDonorName.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelDonorName);
		
		JTextField fieldDonorName = new JTextField();
		fieldDonorName.setPreferredSize(new Dimension(300, 20));
		fieldDonorName.setBorder(fieldBorder);
		leftFormPanel.add(fieldDonorName);
		
		JLabel labelQuantity = new JLabel("Quantity:");
		labelQuantity.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelQuantity);
		
		JTextField fieldQuantity = new JTextField();
		fieldQuantity.setPreferredSize(new Dimension(300, 20));
		fieldQuantity.setBorder(fieldBorder);
		leftFormPanel.add(fieldQuantity);
		
		String[] itemConditionList = {"Acceptable", "Good", "Very Good", "Like New", "New"};
		String[] itemSizeList = {"Small", "Medium", "Large"};
		
		JLabel labelItemCondition = new JLabel("Item Condition:");
		labelItemCondition.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelItemCondition);
		
		JComboBox boxItemCondition = new JComboBox(itemConditionList);
		boxItemCondition.setBackground(Color.WHITE);
		boxItemCondition.setSelectedIndex(0);		
		boxItemCondition.setPreferredSize(new Dimension(300, 20));
		boxItemCondition.setBorder(fieldBorder);
		leftFormPanel.add(boxItemCondition);
		
		JLabel labelItemSize = new JLabel("Item Size:");
		labelItemSize.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelItemSize);
		
		JComboBox boxItemSize = new JComboBox(itemSizeList);
		boxItemSize.setBackground(Color.WHITE);
		boxItemSize.setSelectedIndex(0);
		boxItemSize.setPreferredSize(new Dimension(300, 20));
		boxItemSize.setBorder(fieldBorder);
		leftFormPanel.add(boxItemSize);
		
		JLabel labelStartingBid = new JLabel("Starting Bid:");
		labelStartingBid.setPreferredSize(new Dimension(100, 20));
		leftFormPanel.add(labelStartingBid);
		
		JTextField fieldStartingBid = new JTextField();
		fieldStartingBid.setPreferredSize(new Dimension(300, 20));
		fieldStartingBid.setBorder(fieldBorder);
		leftFormPanel.add(fieldStartingBid);
						
        JPanel rightFormPanel = new JPanel();
        rightFormPanel.setPreferredSize(new Dimension(340, 200));
        rightFormPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        add(rightFormPanel);
		
		
		JLabel labelItemDescription = new JLabel("Item Description");
		labelItemDescription.setPreferredSize(new Dimension(340, 20));
		rightFormPanel.add(labelItemDescription);
		
		JTextArea fieldItemDescription = new JTextArea();
		fieldItemDescription.setPreferredSize(new Dimension(340, 140));
		fieldItemDescription.setBorder(fieldBorder);
		fieldItemDescription.setLineWrap(true);
		rightFormPanel.add(fieldItemDescription);
		
		JButton btnAddItem = new JButton("Submit");
		btnAddItem.setPreferredSize(new Dimension(100, 70));
		add(btnAddItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setPreferredSize(new Dimension(100, 70));
		btnCancelItem.setVisible(false);
		add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setPreferredSize(new Dimension(100, 70));
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
		
		myLayout = new FlowLayout();
		this.setLayout(myLayout);
		myDisplayLabel.setPreferredSize(new Dimension(760, 150));
		
		myDisplayLabel.setText("Below is a list of all the items in the Auction\n"
				+ "Choose the one you want to remove and click on the remove button.");
		String[] columnNames = {"Name", "Donor", "Quantity", "Condition", "Size", "Starting Bid"};
		Object[][] auctionItems = myData.getAuctionItems(myNonprofit);
		JTable itemTable = new JTable(auctionItems, columnNames);
		itemTable.setPreferredSize(new Dimension(760, 300));
		itemTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(itemTable);
		itemTable.setFillsViewportHeight(true);
		scrollPane.setPreferredSize(new Dimension(760, 300));
		add(scrollPane);

		String[] itemNames = new String[auctionItems.length];
		for (int i = 0; i < auctionItems.length; i++) {
			itemNames[i] = (String) auctionItems[i][0];
		}
				
		JPanel bottomHalf = new JPanel();
		bottomHalf.setPreferredSize(new Dimension(300, 200));
		bottomHalf.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		
	    JComboBox itemList = new JComboBox(itemNames);
	    itemList.setPreferredSize(new Dimension(250, 20));
	    itemList.setSelectedIndex(0);
		bottomHalf.add(itemList);
		
		JButton btnRemoveItem = new JButton("Submit");
		btnRemoveItem.setPreferredSize(new Dimension(100, 50));
		bottomHalf.add(btnRemoveItem);
		
		JButton btnCancelItem = new JButton("Cancel");
		btnCancelItem.setVisible(false);
		btnCancelItem.setPreferredSize(new Dimension(100, 50));
		bottomHalf.add(btnCancelItem);
		
		JButton btnBackItem = new JButton("Back");
		btnBackItem.setPreferredSize(new Dimension(100, 50));
		bottomHalf.add(btnBackItem);
		

		
		add(bottomHalf);
		
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
						
					} else if (errorCode == Auction.REMOVEITEMWITHINTWODAYS) {
						JOptionPane.showMessageDialog(null, "Failed to Remove selected item"
								+ " because remove application is submitted within 2 days.");
						
						btnRemoveItem.setVisible(false);
						btnCancelItem.setVisible(false);
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
		
		this.revalidate();
		this.repaint();
	}
	
	private void initializeMenu(String theTitle) {
		this.removeAll();

		myDisplayLabel = new JTextArea();
		myDisplayLabel.setFont(myDisplayLabel.getFont().deriveFont(16f));
		myDisplayLabel.setEditable(false);
		myDisplayLabel.setPreferredSize(new Dimension(760, 350));
		
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
