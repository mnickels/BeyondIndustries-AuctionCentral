package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.Auction;
import model.Data;
import model.Item;
import model.users.Bidder;

/**
 * @author Matthew Subido | subidomd@uw.edu
 * @version 27 November 2016
 */
public class BidderPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel myLabel;
	
	private Bidder myBidder;
	
	private Data myData;
	
	private FlowLayout myFlowLayout;
	
	/**
	 * Constructs the Panel, for use with bidding on items. Mostly takes care of formatting of myJLabel
	 * and the Layout of most of the buttons.
	 * @param theBidder
	 */
	public BidderPanel(final Bidder theBidder) {
		super(new BorderLayout());
		myBidder = theBidder;
		myFlowLayout = new FlowLayout(FlowLayout.CENTER, 20, 20);
		myLabel = new JLabel();
	    myLabel.setFont(new Font("Verdana", Font.PLAIN ,16));
		myLabel.setOpaque(true);
		myLabel.setVisible(true);
		myLabel.setBorder(new LineBorder(Color.BLACK));
		myLabel.setBackground(Color.WHITE);
	    setBorder(new LineBorder(Color.BLACK));
	    myData = Data.getInstance();
		mainMenu();
	}
	
	/**
	 * The Main Menu of the program, showing the options to, "View List of Auctions," or
	 * to, "View Placed Bids." The Auction button calls choseListOfAuctions, and the
	 * Bids button calls choseBids.
	 */
	private void mainMenu() {
		removeAll();
		myLabel.setText("<html>" + myBidder + 
				"<br><br>Main Menu: What Would You Like to Do?</html>");
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton auctions = new JButton("View List of Auctions");
		auctions.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
//		auctions.setBorder(new LineBorder(Color.BLACK));
		auctions.setOpaque(true);
//		auctions.setBackground(Color.WHITE);
		buttons.add(auctions);
		auctions.setVisible(true);
		auctions.setEnabled(true);
		
		JButton bidButton = new JButton("View Placed Bids");
		bidButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseBids();
		    }
		});
//		bidButton.setBorder(new LineBorder(Color.BLACK));
		bidButton.setOpaque(true);
//		bidButton.setBackground(Color.WHITE);
		buttons.add(bidButton);
		bidButton.setVisible(true);
		bidButton.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called from mainMenu, when user chooses to view their placed bids. Displays placed bids, and
	 * allows user to choose one the bids, or to return to the main menu.
	 */
	private void choseBids() {
		Map<Auction, List<Item>> bids = myData.getItemsForBidder(myBidder);
		
		removeAll();
		myLabel.setText(String.format("<html>" + myBidder + 
				"<br><br>%s</html>",
				!bids.isEmpty() ? "List of Bids: Please Select an Item for More Options" :
					"You Have No Current Bids!"));
		
		add(myLabel, BorderLayout.NORTH);
		
		
		JPanel buttons = new JPanel(new GridLayout(0, 3, 20, 20));
		
		for (final Auction a : bids.keySet()) {
			for (final Item i : bids.get(a)) {
				JButton button = new JButton(itemDisplay(i));
				button.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent e) {
				        choseItem(i, a);
				    }
				});
//				button.setBorder(new LineBorder(Color.BLACK));
				button.setMinimumSize(new Dimension(500, 100));
//				button.setBackground(Color.WHITE);
				button.setOpaque(true);
				buttons.add(button);
				button.setVisible(true);
				button.setEnabled(true);
			}
		}
		
		JButton back = new JButton("Go Back to Main Menu");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        mainMenu();
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
//		back.setBackground(Color.WHITE);
		back.setOpaque(true);
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		buttons.setOpaque(true);
		buttons.setVisible(true);
		//buttons.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane pane = new JScrollPane(buttons, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVisible(true);
		pane.setOpaque(true);
		
		JPanel panePanel = new JPanel(new BorderLayout());
		panePanel.add(pane);
		
		add(panePanel);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user selects List of Auctions from the Main Menu. Displays a list of current
	 * auctions, and allows the user to select one, or go back to the Main Menu.
	 */
	private void choseListOfAuctions() {
		removeAll();
		myLabel.setText("<html>" + myBidder + 
				"<br><br>List of Auctions: Please Select an Auction for More Options</html>");
		add(myLabel, BorderLayout.NORTH);
		List<Auction> auctions = myData.getAuctions();
		
		JPanel buttons = new JPanel(new GridLayout(0, 3, 20, 20));
		
		for (final Auction a : auctions) {
			if (a.getDate().isAfter(LocalDateTime.now())) {
				JButton button = new JButton();
				StringBuilder items = new StringBuilder();
				for (final Item i : a.getItems()) {
					items.append(i.getName());
					items.append("<br>");
				}
				button.setText(String.format("<html>%s : %s<br>%s</html>", 
						a.getName(), a.getNonprofit().getOrganizationName(), items.toString()));
				button.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent e) {
				        choseAuction(a);
				    }
				});
//				button.setBorder(new LineBorder(Color.BLACK));
//				button.setBackground(Color.WHITE);
				button.setOpaque(true);
				button.setMinimumSize(new Dimension(500, 100));
				buttons.add(button);
				button.setVisible(true);
				button.setEnabled(true);
			}
		}
		
		JButton back = new JButton("Go Back to Main Menu");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        mainMenu();
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
//		back.setBackground(Color.WHITE);
		back.setOpaque(true);
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);

		buttons.setOpaque(true);
		buttons.setVisible(true);
		//buttons.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane pane = new JScrollPane(buttons, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVisible(true);
		pane.setOpaque(true);
		
		JPanel panePanel = new JPanel(new BorderLayout());
		panePanel.add(pane);
		
		add(panePanel);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when an auction is chosen from the List of Auctions menu. Displays the list of items
	 * within the auction and allows the user to choose one of the items or return to the
	 * List of Auctions menu.
	 */
	private void choseAuction(final Auction theAuction) {
		LocalDateTime time = theAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>Selected Auction:<br>%s, %s %d, %d, %d%s<br><br>"
				+ "%s</html>",
				myBidder.toString(),
				theAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM",
				!theAuction.getItems().isEmpty() ? 
						"List of Items: Please select an Item for More Options" : 
						"No Items In This Auction!"));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout(0, 3, 20, 20));
		
		for (final Item i : theAuction.getItems()) {
			JButton button = new JButton(itemDisplay(i));
			button.addActionListener(new ActionListener() {
			    public void actionPerformed(final ActionEvent e) {
			        choseItem(i, theAuction);
			    }
			});
//			button.setBorder(new LineBorder(Color.BLACK));
//			button.setBackground(Color.WHITE);
			button.setOpaque(true);
			button.setMinimumSize(new Dimension(500, 100));
			buttons.add(button);
			button.setVisible(true);
			button.setEnabled(true);
		}
		
		JButton back = new JButton("Go Back to Auction List");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
//		back.setBackground(Color.WHITE);
		back.setOpaque(true);
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		buttons.setOpaque(true);
		buttons.setVisible(true);
		//buttons.setPreferredSize(new Dimension(200, 200));
		
		JScrollPane pane = new JScrollPane(buttons, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVisible(true);
		pane.setOpaque(true);
		
		JPanel panePanel = new JPanel(new BorderLayout());
		panePanel.add(pane);
		
		add(panePanel);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when an item is chosen from either the Chose Auction menu or the List of Bids menu.
	 * Allows user to bid on an item if they have not yet bid, or cancel / edit a bid if the auction
	 * is more than two days away.
	 * @param theItem is the item that was chosen.
	 * @param theAuction is the auction that theItem is being sold from.
	 */
	private void choseItem(final Item theItem, final Auction theAuction) {
		LocalDateTime time = theAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>%s, %s %d, %d, %d%s<br><br>"
				+ "Selected Item:<br>%s</html>",
				myBidder.toString(),
				theAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM",
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton button = new JButton("Place Bid on Item");
		button.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		if (theItem.checkBidder(myBidder)) {
		    			throw new Exception("You Have Already Bid on This Item!");
		    		}
		    		choseAddBid(theItem, theAuction);
		    	} catch (final Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"You Have Already Bid on This Item!", "Bid Already Exists", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		button.setBorder(new LineBorder(Color.BLACK));
		button.setOpaque(true);
//		button.setBackground(Color.WHITE);
		buttons.add(button);
		button.setVisible(true);
		button.setEnabled(true);
		
		JButton remove = new JButton("Remove Bid on Item");
		remove.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		if (!theItem.checkBidder(myBidder)) {
		    			throw new Exception("You Have Not Yet Bid on This Item!");
		    		}
		    		choseRemoveBid(theItem, theAuction);
		    	} catch (final Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"You Have Not Yet Bid on This Item!", "Bid Does Not Exists", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		remove.setBorder(new LineBorder(Color.BLACK));
		remove.setOpaque(true);
//		remove.setBackground(Color.WHITE);
		buttons.add(remove);
		remove.setVisible(true);
		remove.setEnabled(true);
		
		JButton edit = new JButton("Edit Bid on Item");
		edit.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		if (!theItem.checkBidder(myBidder)) {
		    			throw new Exception("You Have Not Yet Bid on This Item!");
		    		}
		    		choseEditBid(theItem, theAuction);
		    	} catch (final Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"You Have Not Yet Bid on This Item!", "Bid Does Not Exists", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		edit.setBorder(new LineBorder(Color.BLACK));
		edit.setOpaque(true);
//		edit.setBackground(Color.WHITE);
		buttons.add(edit);
		edit.setVisible(true);
		edit.setEnabled(true);
		
		JButton back = new JButton("Go Back to List of Items");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseAuction(theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user chooses to edit their existing bid in an item. Asks for an amount to bid upon
	 * said item.
	 * @param theItem is the item for which the bid is being changed.
	 * @param theAuction is the auction in which the item is being sold from.
	 */
	private void choseEditBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "%s<br><br>Please Enter the Amount You Would Like to Bid Below."
				+ "<br>Press Confirm When Finished, or Go Back</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JTextField text = new JTextField(50);
		
		text.setEnabled(true);
		text.setBorder(new LineBorder(Color.BLACK));
		buttons.add(text, BorderLayout.NORTH);
		
		JButton confirm = new JButton("Confirm Edit");
		confirm.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		BigDecimal theBid = new BigDecimal(text.getText());
		    		if (theBid.doubleValue() < theItem.getStartingBid().doubleValue()) {
		    			throw new Exception("Bid is Less Than Minimum Bid");
		    		}
		    		confirmEdit(theItem, theAuction, theBid);
		    	} catch (final Exception e) {
		    		JOptionPane.showMessageDialog(new JFrame(), 
							"Bid Amount is Less Than the Minimum Bid Amount", "Bid Too Low", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		confirm.setBorder(new LineBorder(Color.BLACK));
		confirm.setOpaque(true);
//		confirm.setBackground(Color.WHITE);
		buttons.add(confirm);
		confirm.setVisible(true);
		confirm.setEnabled(true);
		
		JButton back = new JButton("Go Back to Item");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back, BorderLayout.EAST);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user submits an updated bid for the given item. Asks if the user is sure they wish
	 * to update the bid. If they are, then the bid is updated in the item. If not, then they are 
	 * returned to the Chose Item window.
	 * @param theItem is the item whose bid is being changed.
	 * @param theAuction is the auction to which the item belongs.
	 * @param theBid is the new bid to be placed upon the item.
	 */
	private void confirmEdit(final Item theItem, final Auction theAuction, final BigDecimal theBid) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want to Change Bid to $%s On:<br><br>%s</html>",
				myBidder.toString(),
				theBid.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton addButton = new JButton("Yes");
		addButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
			    	if (theItem.removeBid(myBidder, theAuction.getDate()) == null) {
		    			 throw new Exception("Too Late to Cancel Your Bid!");
		    		 }
			    	theItem.addBid(myBidder, theBid);
			    	confirmationPage(theItem, theAuction, "Your Bid Was Successfully Changed!");
		    	} catch (final Exception e) {
		    		JOptionPane.showMessageDialog(new JFrame(), 
							"It Is Too Late To Cancel This Bid Now!", "Auction Within Two Days", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		addButton.setBorder(new LineBorder(Color.BLACK));
		addButton.setOpaque(true);
//		addButton.setBackground(Color.WHITE);
		buttons.add(addButton);
		addButton.setVisible(true);
		addButton.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user chooses to add a bid in an item. Asks for an amount to bid upon
	 * said item.
	 * @param theItem is the item for which the bid is being added.
	 * @param theAuction is the auction in which the item is being sold from.
	 */
	private void choseAddBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "%s<br><br>Please Enter the Amount You Would Like to Bid Below."
				+ "<br>Press Confirm When Finished, or Go Back</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JTextField text = new JTextField(50);
		
		text.setEnabled(true);
//		text.setBorder(new LineBorder(Color.BLACK));
		buttons.add(text);
		
		JButton confirm = new JButton("Confirm Bid");
		confirm.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		BigDecimal theBid = new BigDecimal(text.getText());
		    		if (theBid.doubleValue() < theItem.getStartingBid().doubleValue()) {
		    			throw new Exception("Bid is Less Than Minimum Bid");
		    		}
		    		confirmBid(theItem, theAuction, theBid);
		    	} catch (final Exception e) {
		    		JOptionPane.showMessageDialog(new JFrame(), 
							"Bid Amount is Less Than the Minimum Bid Amount", "Bid Too Low", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		confirm.setBorder(new LineBorder(Color.BLACK));
		confirm.setOpaque(true);
//		confirm.setBackground(Color.WHITE);
		buttons.add(confirm);
		confirm.setVisible(true);
		confirm.setEnabled(true);
		
		JButton back = new JButton("Go Back to Item");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when user submits a bid for the given item. Asks if the user is sure they wish
	 * to add the bid. If they are, then the bid is added in the item. If not, then they are 
	 * returned to the Chose Item window.
	 * @param theItem is the item whose bid is being added.
	 * @param theAuction is the auction to which the item belongs.
	 * @param theBid is the new bid to be placed upon the item.
	 */
	private void confirmBid(final Item theItem, final Auction theAuction, final BigDecimal theBid) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want to Bid $%s On:<br><br>%s</html>",
				myBidder.toString(),
				theBid.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton addButton = new JButton("Yes");
		addButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	theItem.addBid(myBidder, theBid);
		    	confirmationPage(theItem, theAuction, "Your Bid Was Successfully Placed!");
		    }
		});
//		addButton.setBorder(new LineBorder(Color.BLACK));
		addButton.setOpaque(true);
//		addButton.setBackground(Color.WHITE);
		buttons.add(addButton);
		addButton.setVisible(true);
		addButton.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called if the user selects to remove their existing bid. Asks if the user is sure
	 * they wish to remove their bid from the item. If yes, then the bid is removed, and the item
	 * is updated. If not, the user is returned to the Chose Item window.
	 * @param theItem is the item for which the user's bid is being removed.
	 * @param theAuction is the auction to which the item belongs.
	 */
	private void choseRemoveBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want To Cancel Your Bid On:<br><br>%s</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton remove = new JButton("Yes");
		remove.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		 if (theItem.removeBid(myBidder, theAuction.getDate()) == null) {
		    			 throw new Exception("Too Late to Cancel Your Bid!");
		    		 }
		    		 confirmationPage(theItem, theAuction, "Your Bid Was Successfully Cancelled!");
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(new JFrame(), 
							"It Is Too Late To Cancel This Bid Now!", "Auction Within Two Days", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
//		remove.setBorder(new LineBorder(Color.BLACK));
		remove.setOpaque(true);
//		remove.setBackground(Color.WHITE);
		buttons.add(remove);
		remove.setVisible(true);
		remove.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		back.setBorder(new LineBorder(Color.BLACK));
		back.setOpaque(true);
//		back.setBackground(Color.WHITE);
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when a change has been officially made to an item. Displays new item information,
	 * as well as a confirmation message to assure user of a successful operation. Allows for
	 * user to return to the main menu, the list of auctions, the list of items in the root
	 * auction, or to the Chose Item window.
	 * @param theItem is the item for which the information has been altered.
	 * @param theAuction is the auction in which the item belongs.
	 * @param theMessage is the confirmation message to be displayed.
	 */
	private void confirmationPage(final Item theItem, final Auction theAuction, final String theMessage) {
		LocalDateTime time = theAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>%s, %s %d, %d, %d%s<br><br>%s"
				+ "<br><br>%s</html>",
				myBidder.toString(),
				theAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM",
				itemDisplay(theItem),
				theMessage));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(myFlowLayout);
		
		JButton backMain = new JButton("Return to Main Menu");
		backMain.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        mainMenu();
		    }
		});
//		backMain.setBorder(new LineBorder(Color.BLACK));
		backMain.setOpaque(true);
//		backMain.setBackground(Color.WHITE);
		buttons.add(backMain);
		backMain.setVisible(true);
		backMain.setEnabled(true);
		
		JButton backList = new JButton("Return to List of Auctions");
		backList.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
//		backList.setBorder(new LineBorder(Color.BLACK));
		backList.setOpaque(true);
//		backList.setBackground(Color.WHITE);
		buttons.add(backList);
		backList.setVisible(true);
		backList.setEnabled(true);
		
		JButton backAuc = new JButton("Return to Auction");
		backAuc.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseAuction(theAuction);
		    }
		});
//		backAuc.setBorder(new LineBorder(Color.BLACK));
		backAuc.setOpaque(true);
//		backAuc.setBackground(Color.WHITE);
		buttons.add(backAuc);
		backAuc.setVisible(true);
		backAuc.setEnabled(true);
		
		JButton backItem = new JButton("Return to Item");
		backItem.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
//		backItem.setBorder(new LineBorder(Color.BLACK));
		backItem.setOpaque(true);
//		backItem.setBackground(Color.WHITE);
		buttons.add(backItem);
		backItem.setVisible(true);
		backItem.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	/**
	 * @return the string representation of a given Item for user display.
	 */
	private String itemDisplay (final Item theItem) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>Item ID: ");
		sb.append(theItem.getItemID());
		sb.append("<br>");
		sb.append(theItem.getName());
		sb.append("<br>Condition: ");
		sb.append(theItem.getCondition());
		sb.append("<br>Minimum Bid: ");
		sb.append(theItem.getStartingBid());
		sb.append("<br>My Bid: ");
		if (theItem.checkBidder(myBidder)) {
			sb.append(theItem.getBid(myBidder));
		} else {
			sb.append("None");
		}
		return sb.toString();
	}
}
