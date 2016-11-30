package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
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
	
	public BidderPanel(final Bidder theBidder) {
		super(new BorderLayout());
		myBidder = theBidder;
		myLabel = new JLabel();
	    myLabel.setFont(new Font("Verdana", Font.PLAIN ,16));
		myLabel.setOpaque(true);
		myLabel.setVisible(true);
		myLabel.setBorder(new LineBorder(Color.BLACK));
	    setBorder(new LineBorder(Color.BLACK));
	    myData = Data.getInstance();
		mainMenu();
	}
	
	private void mainMenu() {
		removeAll();
		myLabel.setText("<html>" + myBidder + 
				"<br><br>Main Menu: What Would You Like to Do?</html>");
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton auctions = new JButton("View List of Auctions");
		auctions.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
		auctions.setBorder(new LineBorder(Color.BLACK));
		buttons.add(auctions);
		auctions.setVisible(true);
		auctions.setEnabled(true);
		
		JButton bidButton = new JButton("View Placed Bids");
		bidButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseBids();
		    }
		});
		bidButton.setBorder(new LineBorder(Color.BLACK));
		buttons.add(bidButton);
		bidButton.setVisible(true);
		bidButton.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void choseBids() {
		Map<Auction, List<Item>> bids = myData.getItemsForBidder(myBidder);
		
		removeAll();
		myLabel.setText(String.format("<html>" + myBidder + 
				"<br><br>%s</html>",
				!bids.isEmpty() ? "List of Bids: Please Select an Item for More Options" :
					"You Have No Current Bids!"));
		
		add(myLabel, BorderLayout.NORTH);
		
		ScrollPane pane = new ScrollPane();
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		for (final Auction a : bids.keySet()) {
			for (final Item i : bids.get(a)) {
				JButton button = new JButton(itemDisplay(i));
				button.addActionListener(new ActionListener() {
				    public void actionPerformed(final ActionEvent e) {
				        choseItem(i, a);
				    }
				});
				button.setBorder(new LineBorder(Color.BLACK));
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
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		pane.add(buttons);
		add(pane);
		
		revalidate();
		repaint();
	}
	
	private void choseListOfAuctions() {
		removeAll();
		myLabel.setText("<html>" + myBidder + 
				"<br><br>List of Auctions: Please Select an Auction for More Options</html>");
		add(myLabel, BorderLayout.NORTH);
		List<Auction> auctions = myData.getAuctions();
		
		ScrollPane pane = new ScrollPane();
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
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
				button.setBorder(new LineBorder(Color.BLACK));
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
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		pane.add(buttons);
		
		add(pane);
		
		revalidate();
		repaint();
	}
	
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
		
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		for (final Item i : theAuction.getItems()) {
			JButton button = new JButton(itemDisplay(i));
			button.addActionListener(new ActionListener() {
			    public void actionPerformed(final ActionEvent e) {
			        choseItem(i, theAuction);
			    }
			});
			button.setBorder(new LineBorder(Color.BLACK));
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
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
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
		
		JPanel buttons = new JPanel(new GridLayout());
		
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
		button.setBorder(new LineBorder(Color.BLACK));
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
		remove.setBorder(new LineBorder(Color.BLACK));
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
		edit.setBorder(new LineBorder(Color.BLACK));
		buttons.add(edit);
		edit.setVisible(true);
		edit.setEnabled(true);
		
		JButton back = new JButton("Go Back to List of Items");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseAuction(theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.SOUTH);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void choseEditBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "%s<br><br>Please Reenter the Amount You Would Like to Bid Below."
				+ "<br>Press Confirm When Finished, or Go Back</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new BorderLayout());
		
		JTextField text = new JTextField();
		
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
		confirm.setBorder(new LineBorder(Color.BLACK));
		buttons.add(confirm);
		confirm.setVisible(true);
		confirm.setEnabled(true);
		
		JButton back = new JButton("Go Back to Item");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.EAST);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void confirmEdit(final Item theItem, final Auction theAuction, final BigDecimal theBid) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want to Change Bid to $%s On:<br><br>%s</html>",
				myBidder.toString(),
				theBid.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton addButton = new JButton("Yes");
		addButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	theItem.removeBid(myBidder, theAuction.getDate());
		    	theItem.addBid(myBidder, theBid);
		    	confirmAdditionOfBid(theItem, theAuction);
		    }
		});
		addButton.setBorder(new LineBorder(Color.BLACK));
		buttons.add(addButton);
		addButton.setVisible(true);
		addButton.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void choseAddBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "%s<br><br>Please Enter the Amount You Would Like to Bid Below."
				+ "<br>Press Confirm When Finished, or Go Back</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new BorderLayout());
		
		JTextField text = new JTextField();
		
		text.setEnabled(true);
		text.setBorder(new LineBorder(Color.BLACK));
		buttons.add(text, BorderLayout.NORTH);
		
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
		confirm.setBorder(new LineBorder(Color.BLACK));
		buttons.add(confirm);
		confirm.setVisible(true);
		confirm.setEnabled(true);
		
		JButton back = new JButton("Go Back to Item");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back, BorderLayout.EAST);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void confirmBid(final Item theItem, final Auction theAuction, final BigDecimal theBid) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want to Bid $%s On:<br><br>%s</html>",
				myBidder.toString(),
				theBid.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton addButton = new JButton("Yes");
		addButton.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	theItem.addBid(myBidder, theBid);
		    	confirmAdditionOfBid(theItem, theAuction);
		    }
		});
		addButton.setBorder(new LineBorder(Color.BLACK));
		buttons.add(addButton);
		addButton.setVisible(true);
		addButton.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void confirmAdditionOfBid(final Item theItem, final Auction theAuction) {
		LocalDateTime time = theAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>%s, %s %d, %d, %d%s<br><br>%s"
				+ "<br><br>Your Bid Was Successfully Added!</html>",
				myBidder.toString(),
				theAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM",
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton backMain = new JButton("Return to Main Menu");
		backMain.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        mainMenu();
		    }
		});
		backMain.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backMain);
		backMain.setVisible(true);
		backMain.setEnabled(true);
		
		JButton backList = new JButton("Return to List of Auctions");
		backList.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
		backList.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backList);
		backList.setVisible(true);
		backList.setEnabled(true);
		
		JButton backAuc = new JButton("Return to Auction");
		backAuc.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseAuction(theAuction);
		    }
		});
		backAuc.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backAuc);
		backAuc.setVisible(true);
		backAuc.setEnabled(true);
		
		JButton backItem = new JButton("Return to Item");
		backItem.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		backItem.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backItem);
		backItem.setVisible(true);
		backItem.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void choseRemoveBid(final Item theItem, final Auction theAuction) {
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>"
				+ "Are You Sure You Want To Cancel Your Bid On:<br><br>%s</html>",
				myBidder.toString(),
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton remove = new JButton("Yes");
		remove.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent theEvent) {
		    	try {
		    		 if (theItem.removeBid(myBidder, theAuction.getDate()) == null) {
		    			 throw new Exception("Too Late to Cancel Your Bid!");
		    		 }
		    		 confirmCancel(theItem, theAuction);
		    	} catch (Exception e) {
		    		JOptionPane.showMessageDialog(new JFrame(), 
							"It Is Too Late To Cancel This Bid Now!", "Auction Within Two Days", 
							JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});
		remove.setBorder(new LineBorder(Color.BLACK));
		buttons.add(remove);
		remove.setVisible(true);
		remove.setEnabled(true);
		
		JButton back = new JButton("No");
		back.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		back.setBorder(new LineBorder(Color.BLACK));
		buttons.add(back);
		back.setVisible(true);
		back.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
	private void confirmCancel(final Item theItem, final Auction theAuction) {
		LocalDateTime time = theAuction.getDate();
		int currentHour = time.getHour() % 12;
		if (currentHour == 0) currentHour = 12;
		
		removeAll();
		
		myLabel.setText(String.format("<html>%s<br><br>%s, %s %d, %d, %d%s<br><br>%s"
				+ "<br><br>Your Bid Was Successfully Cancelled!</html>",
				myBidder.toString(),
				theAuction.getName(),
				time.getMonth(),
				time.getDayOfMonth(),
				time.getYear(),
				currentHour,
				time.getHour() < 12 ? "AM" : "PM",
				itemDisplay(theItem)));
		
		add(myLabel, BorderLayout.NORTH);
		
		JPanel buttons = new JPanel(new GridLayout());
		
		JButton backMain = new JButton("Return to Main Menu");
		backMain.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        mainMenu();
		    }
		});
		backMain.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backMain);
		backMain.setVisible(true);
		backMain.setEnabled(true);
		
		JButton backList = new JButton("Return to List of Auctions");
		backList.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseListOfAuctions();
		    }
		});
		backList.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backList);
		backList.setVisible(true);
		backList.setEnabled(true);
		
		JButton backAuc = new JButton("Return to Auction");
		backAuc.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseAuction(theAuction);
		    }
		});
		backAuc.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backAuc);
		backAuc.setVisible(true);
		backAuc.setEnabled(true);
		
		JButton backItem = new JButton("Return to Item");
		backItem.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        choseItem(theItem, theAuction);
		    }
		});
		backItem.setBorder(new LineBorder(Color.BLACK));
		buttons.add(backItem);
		backItem.setVisible(true);
		backItem.setEnabled(true);
		
		add(buttons);
		
		revalidate();
		repaint();
	}
	
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
