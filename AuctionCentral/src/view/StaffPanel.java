package view;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Data;
import model.users.Staff;

/**
 * Represents the StaffPanel. A Staff user can login and see the calendar of upcoming auctions and also
 * can change the number of maximum auctions allowed in the system.
 * 
 * @author Gjorgi Stojanov
 * @version 12/3/2016
 *
 */
public class StaffPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Staff myStaff;
	private JLabel myLabel;
	private JButton myViewUpcomingAuctionsBtn;
	private JButton myModifyMaxAucitons;
	private JButton myBackBtn;
	private JButton myChangeBtn;
	private JLabel myCurrentAuctionsLbl;
	private JTextField myChangeField;
	
	/**
	 * Constructor for the StaffPanel.
	 * 
	 * @param theStaff is the staff user that is going to be using the staff panel.
	 */
	public StaffPanel(Staff theStaff) {
		//super(new FlowLayout(FlowLayout.LEFT));
		//super(new BorderLayout());
		super();
		myStaff = theStaff;
		myLabel = new JLabel();
		this.setLayout(null);
		mainMenu();
		setVisible(true);
	}
	
	/**
	 * Main method for initializing the main screen.
	 */
	private void mainMenu() {
		removeAll();
		
		initHeader();
		
		myViewUpcomingAuctionsBtn = new JButton("View calendar of upcoming auctions");
		myViewUpcomingAuctionsBtn.setEnabled(true);
		myViewUpcomingAuctionsBtn.setBounds(new Rectangle(20, 80, 260, 30));
		add(myViewUpcomingAuctionsBtn);
		
		myViewUpcomingAuctionsBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				displayCalendar();
			}
		});
		
		myModifyMaxAucitons = new JButton("View / Modify max number of auctions");
		myModifyMaxAucitons.setEnabled(true);
		myModifyMaxAucitons.setBounds(new Rectangle(20, 600, 250, 30));
		add(myModifyMaxAucitons);
		
		myModifyMaxAucitons.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeMaxNumAuctions();
			}
		});
		
		
		revalidate();
		repaint();
	}
	
	
	/**
	 * Method for initializing the current user's login information and the current date.
	 */
	private void initHeader() {
		removeAll();
		
		myLabel.setOpaque(true);
		myLabel.setFont(myLabel.getFont().deriveFont(16f));
		myLabel.setFont(myLabel.getFont().deriveFont(Font.PLAIN));

		LocalDateTime curr = Data.getInstance().getCurrentDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM d - yyyy");
		String text = curr.format(formatter);

		myLabel.setText("<html>" + myStaff.getName() + " logged in as a Staff <br><br>" + "Current date: " 
						+ text);
		myLabel.setBounds(new Rectangle(20, 5, 760, 60));
		add(myLabel);
		
		repaint();
		
	}
	
	/**
	 * Displays the view/modify screen so the user can view or modify the max number of auctions.
	 */
	private void viewModifyMaxAuctionsHeader() {
		initHeader();
		
		myCurrentAuctionsLbl = new JLabel();
		myCurrentAuctionsLbl.setOpaque(true);
		Font f = new Font("Dialog.plain", Font.PLAIN, 20);
		myCurrentAuctionsLbl.setFont(f);
		myCurrentAuctionsLbl.setBounds(new Rectangle(20, 65, 760, 50));
		myCurrentAuctionsLbl.setText("Current number of maximum auctions allowed: " + Data.getInstance().getMaxNumberOfAuctions());
		add(myCurrentAuctionsLbl);
		
		myBackBtn = new JButton("<- BACK");
		myBackBtn.setEnabled(true);
		myBackBtn.setBounds(new Rectangle(20, 600, 250, 30));
		add(myBackBtn);
		
		myBackBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainMenu();
			}
		});
		
	}
	
	/**
	 * Represents the change display of the view/
	 */
	private void changeMaxNumAuctions() {
		viewModifyMaxAuctionsHeader();
		
		JLabel aLabel2 = new JLabel();
		aLabel2.setOpaque(true);
		Font f2 = new Font("Dialog.plain", Font.PLAIN, 16);
		aLabel2.setFont(f2);
		aLabel2.setBounds(new Rectangle(20, 110, 760, 30));
		aLabel2.setText("Change maximum auctions allowed:");
		add(aLabel2);
		
		myChangeField = new JTextField();
		myChangeField.setBounds(20, 140, 55, 20);
		add(myChangeField);
		
		myChangeBtn = new JButton("Change");
		myChangeBtn.setEnabled(true);
		myChangeBtn.setBounds(new Rectangle(80, 140, 80, 20));
		add(myChangeBtn);
		
		myChangeBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isValidInput(myChangeField.getText())) {
					changeConfirmation();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter positive number (> 0)");
				}
			}
		});
	}
	
	/**
	 * Represents the change confirmation screen.
	 */
	private void changeConfirmation() {
		viewModifyMaxAuctionsHeader();
		
		JLabel aLabel = new JLabel();
		aLabel.setOpaque(true);
		Font f2 = new Font("Dialog.plain", Font.PLAIN, 16);
		aLabel.setFont(f2);
		aLabel.setBounds(new Rectangle(20, 110, 760, 30));
		aLabel.setText("Are you sure you want to change max number of auctions to: " + myChangeField.getText());
		add(aLabel);
		
		JButton aButton = new JButton("Confirm");
		aButton.setEnabled(true);
		aButton.setBounds(new Rectangle(20, 140, 80, 20));
		add(aButton);
		
		aButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int v = Integer.parseInt(myChangeField.getText());
				Data.getInstance().setMaxNumberOfAuctions(v);
				changeMaxNumAuctions();
			}
		});
		
		JButton aButton2 = new JButton("Cancel");
		aButton2.setEnabled(true);
		aButton2.setBounds(new Rectangle(110, 140, 80, 20));
		add(aButton2);
		
		aButton2.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeMaxNumAuctions();
			}
		});
		
	}
	
	/**
	 * Displays the calendar of the upcoming auctions.
	 */
	private void displayCalendar() {
		myViewUpcomingAuctionsBtn.setEnabled(false);
		
//		myCalendarLabel = new JTextArea();
//		myCalendarLabel.setFont(myCalendarLabel.getFont().deriveFont(20f));
//		myCalendarLabel.setEditable(false);
//		myCalendarLabel.setBounds(new Rectangle(20, 120, 760, 330));
//		TitledBorder title;
//		Border blackline = BorderFactory.createLineBorder(Color.black);
//		title = BorderFactory.createTitledBorder(blackline, "Number of upcoming auctions: " + myData.totalNumberOfUpcomingAuctions());
//		title.setTitleJustification(TitledBorder.CENTER);
//		title.setTitleFont(title.getTitleFont().deriveFont(16f));
//		title.setTitleFont(title.getTitleFont().deriveFont(Font.PLAIN));
//		
//		myCalendarLabel.setBorder(title);
//		Calendar c = new Calendar(Data.getInstance().getCurrentDateTime());
//		String txt = c.getCalendar();
//		myCalendarLabel.setText(txt);
//		add(myCalendarLabel);
		
		JLabel aLabel = new JLabel();
		aLabel.setOpaque(true);
		aLabel.setFont(new Font("Dialog.plain", Font.PLAIN, 18));
		aLabel.setBounds(new Rectangle(20, 120, 760, 30));
		aLabel.setText("Upcoming auctions: " + Data.getInstance().totalNumberOfUpcomingAuctions());
		add(aLabel);
		
		Calendar c = new Calendar(Data.getInstance().getCurrentDateTime().plusDays(1).toLocalDate());
		c.setBounds(new Rectangle(20, 150, 760, 330));
		add(c);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Checks if a string can represent an positive integer
	 * 
	 * @param theInput the string to parse
	 * 
	 * @return true if the string can represent a positive integer, false otherwise.
	 */
	private boolean isValidInput(String theInput) {
		boolean result = false;
		try {
			int val = Integer.parseInt(theInput);
			if (val > 0) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;		
	}

}