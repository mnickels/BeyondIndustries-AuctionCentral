package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Auction;
import model.Data;
import model.Item;
import model.users.Nonprofit;

/**
 * Creates the nonprofit panel and all of its components.
 * 
 * @author Ming Lam
 * @author Ian Richards
 * @version Dec.5.2016
 *
 */
public class NonprofitPanel extends JPanel {

	public static final int BTNSUBMITAUCTIONREQUEST = 1;

	public static final int BTNCANCELAUCTIONREQUEST = 2;

	public static final int BTNADDITEM = 3;

	public static final int BTNREMOVEITEM = 4;

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main menu components dimension.
	 */
	private final Dimension btnMainDim = new Dimension(170, 50);

	/**
	 * Submit auction request components dimension.
	 */
	private final Dimension leftPanelAuctionDim = new Dimension(450, 200);

	private final Dimension rightPanelAuctionDim = new Dimension(100, 200);

	private final Dimension labelAuctionDim = new Dimension(150, 20);

	private final Dimension fieldAuctionDim = new Dimension(300, 20);

	private final Dimension btnAddAuctionDim = new Dimension(100, 60);
	
	/**
	 * Cancel auction request components dimension.
	 */
	private final Dimension btnRemoveAuctionDimension = new Dimension(100, 70);

	/**
	 * Add item components dimension.
	 */
	private final Dimension leftPanelItemDim = new Dimension(400, 200);

	private final Dimension rightPanelItemDim = new Dimension(340, 200);

	private final Dimension labelItemDim = new Dimension(100, 20);

	private final Dimension fieldItemDim = new Dimension(300, 20);

	private final Dimension fieldDescriptionDim = new Dimension(340, 140);

	private final Dimension btnAddItemDim = new Dimension(100, 70);

	/**
	 * Remove item components dimension
	 */
	private final Dimension btnRemoveItemDim = new Dimension(100, 50);

	/**
	 * Nonprofit panel components.
	 */
	private JTextArea myDisplayArea;

	private JButton myBtnSubmitAuctionRequest;

	private JButton myBtnCancelAuctionRequest;

	private JButton myBtnAddItem;

	private JButton myBtnRemoveItem;

	private Nonprofit myNonprofit;

	private JButton myBtnSubmit;

	private JButton myBtnBack;

	/**
	 * Data instance.
	 */
	private Data myData;

	/**
	 * layout of the frame
	 */
	private FlowLayout myLayout;

	/**
	 * number of items in the auction
	 */
	private int numberOfItems;

	/**
	 * Constructor for NonprofitPanel class.
	 * @param the nonprofit that login to this panel
	 */
	public NonprofitPanel (Nonprofit theNonprofit) {
		super();
		myNonprofit = theNonprofit;
		myData = Data.getInstance();

		this.setPreferredSize(new Dimension(800, 700));
		myLayout = new FlowLayout();

		mainMenu();
		setVisible(true);
	}

	/**
	 * The main menu of the panel. This will be the first to display
	 * when a nonprofit login to the panel.
	 */
	private void mainMenu() {		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		initializeMenu("Main Menu");

		myBtnSubmitAuctionRequest = new JButton("Submit Auction Request");
		myBtnSubmitAuctionRequest.setPreferredSize(btnMainDim);
		myBtnCancelAuctionRequest = new JButton("Cancel Auction Request");
		myBtnCancelAuctionRequest.setPreferredSize(btnMainDim);
		myBtnAddItem = new JButton("Add Item");
		myBtnAddItem.setPreferredSize(btnMainDim);
		myBtnRemoveItem = new JButton("Remove Item");
		myBtnRemoveItem.setPreferredSize(btnMainDim);

		add(myBtnSubmitAuctionRequest);
		add(myBtnCancelAuctionRequest);
		add(myBtnAddItem);
		add(myBtnRemoveItem);

		myLayout.setVgap(50);

		myDisplayArea.setText(myNonprofit.getName() + " logged in as a Nonprofit");
		if(myData.nonprofitHasAuction(myNonprofit) == true){
			Auction myAuction = myData.getAuctionForThisNonprofit(myNonprofit);
			myDisplayArea.append(String.format("\nYou have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
					myAuction.getName(), myAuction.getDate().getMonth(), 
					myAuction.getDate().getDayOfMonth(), myAuction.getDate().getYear()));
			myBtnSubmitAuctionRequest.setEnabled(false);
			myBtnCancelAuctionRequest.setEnabled(true);
			myBtnAddItem.setEnabled(true);
			myBtnRemoveItem.setEnabled(true);

			if (myAuction.getSize() == 0) {
				myBtnRemoveItem.setEnabled(false);
			}
		} else {
			myBtnSubmitAuctionRequest.setEnabled(true);
			myBtnCancelAuctionRequest.setEnabled(false);
			myBtnAddItem.setEnabled(false);
			myBtnRemoveItem.setEnabled(false);
		}

		//Submit Auction Request Button			
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

	/**
	 * Creates the submit auction gui menu and all components pretaining to it.
	 */
	private void submitAuctionRequestMenu() {
		initializeMenu("Submit Auction Request");

		myLayout = new FlowLayout(FlowLayout.CENTER, 40, 50);
		this.setLayout(myLayout);

		myDisplayArea.setText("In order to submit an auction request,"
				+ "you have to enter the following information:\n"
				+ "-Organization Name\n"
				+ "-Auction Date (YYYY-MM-DDTHH:MM)(MilitaryTime)\n"
				+ "-Number of approximate Items (Optional)\n"
				+ "-Auction Description (Optional)\n");

		//Initializing the form for submitting an auction request
		Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		JPanel leftFormPanel = new JPanel();
		leftFormPanel.setPreferredSize(leftPanelAuctionDim);
		leftFormPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		add(leftFormPanel);

		JLabel labelOrgName = new JLabel("Organization Name:");
		labelOrgName.setPreferredSize(labelAuctionDim);
		leftFormPanel.add(labelOrgName);

		JTextField fieldOrgName = new JTextField();
		fieldOrgName.setPreferredSize(fieldAuctionDim);
		fieldOrgName.setBorder(fieldBorder);
		leftFormPanel.add(fieldOrgName);

		JLabel labelDateAndTime = new JLabel("Auction Date and Time:");
		labelDateAndTime.setPreferredSize(labelAuctionDim);
		leftFormPanel.add(labelDateAndTime);

		JTextField fieldDateAndTime = new JTextField();
		fieldDateAndTime.setPreferredSize(fieldAuctionDim);
		fieldDateAndTime.setBorder(fieldBorder);
		leftFormPanel.add(fieldDateAndTime);				

		JLabel labelItemNumber = new JLabel("Approximate Items:");
		labelItemNumber.setPreferredSize(labelAuctionDim);
		leftFormPanel.add(labelItemNumber);

		JTextField fieldItemNumber = new JTextField();
		fieldItemNumber.setPreferredSize(fieldAuctionDim);
		fieldItemNumber.setBorder(fieldBorder);
		leftFormPanel.add(fieldItemNumber);

		JLabel labelAuctionDescript = new JLabel("Auction Description:");
		labelAuctionDescript.setPreferredSize(labelAuctionDim);
		leftFormPanel.add(labelAuctionDescript);

		JTextArea fieldAuctionDescript = new JTextArea();
		fieldAuctionDescript.setPreferredSize(fieldAuctionDim);
		fieldAuctionDescript.setBorder(fieldBorder);
		fieldAuctionDescript.setLineWrap(true);
		leftFormPanel.add(fieldAuctionDescript);

		JPanel rightFormPanel = new JPanel();
		rightFormPanel.setPreferredSize(rightPanelAuctionDim);
		rightFormPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		add(rightFormPanel);

		myBtnSubmit.setPreferredSize(btnAddAuctionDim);
		rightFormPanel.add(myBtnSubmit);

		myBtnBack.setPreferredSize(btnAddAuctionDim);
		rightFormPanel.add(myBtnBack);

		repaint();

		myBtnSubmit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnSubmit.getText().equals("Submit")) {
					String orgName = fieldOrgName.getText();
					String numberItems = fieldItemNumber.getText();
					String auctionDesc = fieldAuctionDescript.getText();
					String dateTime = fieldDateAndTime.getText();
					DateTimeFormatter newFormat = DateTimeFormatter.ISO_DATE_TIME;
					LocalDateTime auctionDateTime = LocalDateTime.parse(dateTime, newFormat);
					numberOfItems = 0; 
					if(!fieldAuctionDescript.getText().isEmpty()){
						numberOfItems = Integer.parseInt(numberItems);
					}

					Auction newAuction = new Auction(myNonprofit, auctionDateTime, orgName, auctionDesc);

					if(myData.addAuction(newAuction) == true){
						myDisplayArea.setText(String.format("Are you sure you want to submit this auction?"
								+ "\nAuction Name: %s\n\n" + "Date: %s %d, %d\n" + "Description: %s\n"
								+ "Number of approximate Items: %s\n", orgName, auctionDateTime.getMonth(), 
								auctionDateTime.getDayOfMonth(), auctionDateTime.getYear(),  auctionDesc, numberOfItems));

						myBtnSubmit.setForeground(Color.RED);;
						myBtnSubmit.setText("Confirm");
						myBtnBack.setText("Cancel");
					} else if(myNonprofit.isAuctionWithinYear() == true) {
						JOptionPane.showMessageDialog(null, "I'm sorry, you cannot submit this auction as you "
								+ "already have had an auction within the past year ");
					} else if(myData.auctionMoreThan2Day(newAuction) == true) {
						JOptionPane.showMessageDialog(null, "I'm sorry, you cannot submit this auction as there "
								+ "is already 2 auctions scheduled for the day you specified");
					} else if(myData.auctionExceedsMax(newAuction) == true) {
						JOptionPane.showMessageDialog(null, "I'm sorry, you cannot submit this auction at this time as there "
								+ "is already the max number of auctions allowed in the system at this time");
					} else if(myData.auctionPlannedWeekAhead(newAuction) == true) {
						JOptionPane.showMessageDialog(null, "I'm sorry, you cannot submit this auction as you "
								+ "must submit your auction at least one week into the future");
					} else {
						JOptionPane.showMessageDialog(null, "I'm sorry, you cannot submit this auction as you "
								+ "must submit your auction at most no more than one month into the future");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Congrats, you have successfully submitted your auction.");
					mainMenu();
				}
			}
		});

		myBtnBack.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnBack.getText().equals("Cancel")){
					myData.removeAuction(myData.getAuctionForThisNonprofit(myNonprofit));
				}
				if (myBtnBack.getText().equals("Back")) {
					mainMenu();
				} else {
					myBtnSubmit.setText("Submit");
					myBtnBack.setText("Back");
				}
			}
		});

		repaint();		

	}

	/**
	 * Creates the cancel auction menu and all components pretaining to it.
	 */
	private void CancelAuctionRequestMenu() {
		initializeMenu("Cancel Auction Request");

		myLayout = new FlowLayout(FlowLayout.CENTER, 20, 50);
		this.setLayout(myLayout);

		Auction currentAuction = Data.getInstance().getAuctionForThisNonprofit(myNonprofit);
		String theName = myNonprofit.getName();
		Month theMonth = currentAuction.getDate().getMonth();
		int theDay = currentAuction.getDate().getDayOfMonth();
		int theYear = currentAuction.getDate().getYear();
		myDisplayArea.append(String.format("You have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
				theName, theMonth, theDay, theYear));
		myDisplayArea.append("\n\nWould you like to remove this auction?");

		myBtnSubmit.setPreferredSize(btnRemoveAuctionDimension);
		add(myBtnSubmit);

		myBtnBack.setPreferredSize(btnRemoveAuctionDimension);
		add(myBtnBack);

		myBtnSubmit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnSubmit.getText().equals("Submit")) {
					myDisplayArea.setText("Are you sure you want to remove this auction?");
					
					myBtnSubmit.setForeground(Color.RED);
					myBtnSubmit.setText("Confirm");
					myBtnBack.setText("Cancel");
				} else {
					if (myData.removeAuction(currentAuction) == true) {
						JOptionPane.showMessageDialog(null, "You have successfully removed this auction");
						mainMenu();
					} else {
						JOptionPane.showMessageDialog(null, "You cannot cancel your auction as it is within 2 days of the date your auction is set for.");
					}	
				}
			}
		});

		myBtnBack.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnBack.getText().equals("Back")) {
					mainMenu();
				} else {
					CancelAuctionRequestMenu();
				}
			}
		});

		this.repaint();
	}

	/**
	 * Create adding item menu and all the GUI components.
	 */
	private void AddItemMenu() {
		initializeMenu("Add Item");

		myLayout = new FlowLayout(FlowLayout.CENTER, 20, 0);
		this.setLayout(myLayout);

		myDisplayArea.setText("In order to add an item,\n"
				+ "The following information are required:\n"
				+ "-Item Name\n-Quantity\n-Item Condition\n"
				+ "-Item Size\n-Starting Bid\n\n"
				+ "The following information are optional:\n"
				+ "-Donor Name\n-Item Description");

		Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		JPanel leftFormPanel = new JPanel();
		leftFormPanel.setPreferredSize(leftPanelItemDim);
		leftFormPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		add(leftFormPanel);

		JLabel labelItemName = new JLabel("Item Name:");
		labelItemName.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelItemName);

		JTextField fieldItemName = new JTextField();
		fieldItemName.setPreferredSize(fieldItemDim);
		fieldItemName.setBorder(fieldBorder);
		leftFormPanel.add(fieldItemName);

		JLabel labelDonorName = new JLabel("Donor Name:");
		labelDonorName.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelDonorName);

		JTextField fieldDonorName = new JTextField();
		fieldDonorName.setPreferredSize(fieldItemDim);
		fieldDonorName.setBorder(fieldBorder);
		leftFormPanel.add(fieldDonorName);

		JLabel labelQuantity = new JLabel("Quantity:");
		labelQuantity.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelQuantity);

		JTextField fieldQuantity = new JTextField();
		fieldQuantity.setPreferredSize(fieldItemDim);
		fieldQuantity.setBorder(fieldBorder);
		leftFormPanel.add(fieldQuantity);

		String[] itemConditionList = {"Acceptable", "Good", "Very Good", "Like New", "New"};
		String[] itemSizeList = {"Small", "Medium", "Large"};

		JLabel labelItemCondition = new JLabel("Item Condition:");
		labelItemCondition.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelItemCondition);

		JComboBox<String> boxItemCondition = new JComboBox<String>(itemConditionList);
		boxItemCondition.setBackground(Color.WHITE);
		boxItemCondition.setSelectedIndex(0);		
		boxItemCondition.setPreferredSize(fieldItemDim);
		boxItemCondition.setBorder(fieldBorder);
		leftFormPanel.add(boxItemCondition);

		JLabel labelItemSize = new JLabel("Item Size:");
		labelItemSize.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelItemSize);

		JComboBox<String> boxItemSize = new JComboBox<String>(itemSizeList);
		boxItemSize.setBackground(Color.WHITE);
		boxItemSize.setSelectedIndex(0);
		boxItemSize.setPreferredSize(fieldItemDim);
		boxItemSize.setBorder(fieldBorder);
		leftFormPanel.add(boxItemSize);

		JLabel labelStartingBid = new JLabel("Starting Bid:");
		labelStartingBid.setPreferredSize(labelItemDim);
		leftFormPanel.add(labelStartingBid);

		JTextField fieldStartingBid = new JTextField();
		fieldStartingBid.setPreferredSize(fieldItemDim);
		fieldStartingBid.setBorder(fieldBorder);
		leftFormPanel.add(fieldStartingBid);

		JPanel rightFormPanel = new JPanel();
		rightFormPanel.setPreferredSize(rightPanelItemDim);
		rightFormPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		add(rightFormPanel);

		JLabel labelItemDescription = new JLabel("Item Description");
		labelItemDescription.setPreferredSize(labelItemDim);
		rightFormPanel.add(labelItemDescription);

		JTextArea fieldItemDescription = new JTextArea();
		fieldItemDescription.setPreferredSize(fieldDescriptionDim);
		fieldItemDescription.setBorder(fieldBorder);
		fieldItemDescription.setLineWrap(true);
		rightFormPanel.add(fieldItemDescription);

		myBtnSubmit.setPreferredSize(btnAddItemDim);
		add(myBtnSubmit);
		
		myBtnBack.setPreferredSize(btnAddItemDim);
		add(myBtnBack);

		myBtnSubmit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnSubmit.getText().equals("Submit")) {
					myBtnSubmit.setForeground(Color.RED);
					myBtnSubmit.setText("Comfirm");
					myBtnBack.setText("Cancel");
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

		myBtnBack.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnBack.getText().equals("Back")) {
					mainMenu();
				} else {
					myBtnSubmit.setForeground(Color.BLACK);
					myBtnSubmit.setText("Submit");
					myBtnBack.setText("Back");
				}
			}
		});
	}

	/**
	 * Create removing item menu and all the components.
	 */
	private void RemoveItemMenu() {
		initializeMenu("Remove Item");

		myLayout = new FlowLayout();
		this.setLayout(myLayout);
		myDisplayArea.setPreferredSize(new Dimension(760, 150));

		myDisplayArea.setText("Below is a list of all the items in the Auction\n"
				+ "Choose the one you want to remove and click on the remove button.");
		String[] columnNames = {"Name", "Donor", "Quantity", "Condition", "Size", "Starting Bid"};
		Object[][] auctionItems = myData.getAuctionItems(myNonprofit);
		JTable itemTable = new JTable(auctionItems, columnNames);
		itemTable.setPreferredSize(new Dimension(760, 300));
		itemTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		itemTable.setEnabled(false);

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

		JComboBox<String> itemList = new JComboBox<String>(itemNames);
		itemList.setPreferredSize(new Dimension(250, 20));
		itemList.setSelectedIndex(0);
		bottomHalf.add(itemList);

		myBtnSubmit.setPreferredSize(btnRemoveItemDim);
		bottomHalf.add(myBtnSubmit);
		
		myBtnBack.setPreferredSize(btnRemoveItemDim);
		bottomHalf.add(myBtnBack);

		add(bottomHalf);

		myBtnSubmit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnSubmit.getText().equals("Submit")) {
					myBtnSubmit.setForeground(Color.RED);
					myBtnSubmit.setText("Comfirm");
					myBtnBack.setText("Cancel");
					itemList.setEnabled(false);
				} else {
					String itemName = (String) itemList.getSelectedItem();
					Item tempItem = new Item(itemName, null, 0, null, null, null, new BigDecimal(0), null);
					int errorCode = myData.removeItemInAuction(myNonprofit, tempItem);

					if (errorCode == Auction.REMOVEITEMNOTEXIST) {
						JOptionPane.showMessageDialog(null, "Failed to Remove selected item "
								+ "because it doesn't exist.");
					} else if (errorCode == Auction.REMOVEITEMWITHINTWODAYS) {
						JOptionPane.showMessageDialog(null, "Failed to Remove selected item"
								+ " because remove application is submitted within 2 days of the auction.");
					} else {
						JOptionPane.showMessageDialog(null, "You have successfully removed an item");
						mainMenu();
					}
				}
			}
		});

		myBtnBack.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (myBtnBack.getText().equals("Back")) {
					mainMenu();
				} else {
					myBtnSubmit.setForeground(Color.BLACK);
					myBtnSubmit.setText("Submit");
					myBtnBack.setText("Back");
					itemList.setEnabled(true);
				}
			}
		});

		this.repaint();
	}

	/**
	 * Initialize the display area of the panel.
	 * @param text that will be the displayed on the top of the display area
	 */
	private void initializeMenu(String theTitle) {
		this.removeAll();

		myBtnSubmit = new JButton();
		myBtnSubmit.setText("Submit");

		myBtnBack = new JButton();
		myBtnBack.setText("Back");

		myDisplayArea = new JTextArea();
		myDisplayArea.setFont(myDisplayArea.getFont().deriveFont(16f));
		myDisplayArea.setEditable(false);
		myDisplayArea.setPreferredSize(new Dimension(760, 350));

		TitledBorder title;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(blackline, theTitle);
		title.setTitleJustification(TitledBorder.CENTER);
		myDisplayArea.setBorder(title);
		add(myDisplayArea);

		repaint();
	}

	/**
	 * Display the upcoming auction in the text area.
	 * @param the name of the auction
	 * @param the month of the auction
	 * @param the date of the auction
	 * @param the year of the auction
	 */
	public void displayUpcomingAuction(String theName, Month theMonth, int theDay, int theYear) {
		myDisplayArea.append("\n\n");
		myDisplayArea.append(String.format("You have one upcoming auction:\nAuction Name: %s\nDate: %s %d, %d",
				theName, theMonth, theDay, theYear));
	}
}
