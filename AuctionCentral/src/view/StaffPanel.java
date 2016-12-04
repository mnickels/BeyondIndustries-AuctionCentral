package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Data;
import model.users.Staff;

public class StaffPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Staff myStaff;
	private JLabel myLabel;
	private JLabel myErrorMsg;
	private JTextArea myCalendarLabel;
	private JButton myViewUpcomingAuctionsBtn;
	private JButton myModifyMaxAucitons;
	private JButton myBackBtn;
	private JButton myChangeBtn;
	private JLabel myCurrentAuctionsLbl;
	private JTextField myChangeField;
	
	private Data myData;
	
	public StaffPanel(Staff theStaff) {
		//super(new FlowLayout(FlowLayout.LEFT));
		//super(new BorderLayout());
		super();
		myStaff = theStaff;
		myLabel = new JLabel();
		myData = Data.getInstance();
		this.setLayout(null);
		mainMenu();
		setVisible(true);
	}
	
	private void mainMenu() {
		removeAll();
		
		/*myLabel.setOpaque(true);
		myLabel.setFont(myLabel.getFont().deriveFont(16f));
		myLabel.setFont(myLabel.getFont().deriveFont(Font.PLAIN));

		LocalDateTime curr = Data.getInstance().getCurrentDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM d - yyyy");
		String text = curr.format(formatter);

		myLabel.setText("<html>" + myStaff.getName() + " logged in as a Staff <br><br>" + "Current date: " 
						+ text);
		myLabel.setBounds(new Rectangle(20, 5, 760, 60));
		//myLabel.setBackground(Color.white);
		add(myLabel);*/
		
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
		//myLabel.setBackground(Color.white);
		add(myLabel);
		
		repaint();
		
	}
	
	private void viewModifyMaxAuctionsHeader() {
		initHeader();
		
		myCurrentAuctionsLbl = new JLabel();
		myCurrentAuctionsLbl.setOpaque(true);
		Font f = new Font("Dialog.plain", Font.PLAIN, 20);
		myCurrentAuctionsLbl.setFont(f);
		myCurrentAuctionsLbl.setBounds(new Rectangle(20, 65, 760, 50));
		myCurrentAuctionsLbl.setText("Current number of maximum auctions allowed: " + Data.getInstance().getMaxNumberOfAuctions());
		add(myCurrentAuctionsLbl);
		
		/*JLabel aLabel2 = new JLabel();
		aLabel2.setOpaque(true);
		Font f2 = new Font("Dialog.plain", Font.PLAIN, 16);
		aLabel2.setFont(f2);
		aLabel2.setBounds(new Rectangle(20, 110, 760, 30));
		aLabel2.setText("Change maximum auctions allowed:");
		add(aLabel2);
		
		myErrorMsg = new JLabel();
		myErrorMsg.setBounds(new Rectangle(20, 150, 760, 50));
		
		JTextField changeField = new JTextField();
		changeField.setBounds(20, 140, 55, 20);
		add(changeField);
		
		myChangeBtn = new JButton("Change");
		myChangeBtn.setEnabled(true);
		myChangeBtn.setBounds(new Rectangle(80, 140, 80, 20));
		add(myChangeBtn);
		
		myChangeBtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isValidInput(changeField.getText())) {
					int v = Integer.parseInt(changeField.getText());
					Data.getInstance().setMaxNumberOfAuctions(v);
					viewModifyMaxAuctions();
				} else {
					showErrorMessage();
				}
			}
		});*/
		
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
	
	private void changeMaxNumAuctions() {
		viewModifyMaxAuctionsHeader();
		
		JLabel aLabel2 = new JLabel();
		aLabel2.setOpaque(true);
		Font f2 = new Font("Dialog.plain", Font.PLAIN, 16);
		aLabel2.setFont(f2);
		aLabel2.setBounds(new Rectangle(20, 110, 760, 30));
		aLabel2.setText("Change maximum auctions allowed:");
		add(aLabel2);
		
		myErrorMsg = new JLabel();
		myErrorMsg.setBounds(new Rectangle(20, 150, 760, 50));
		
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
					showErrorMessage();
				}
			}
		});
	}
	
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
	
	private void displayCalendar() {
		myViewUpcomingAuctionsBtn.setEnabled(false);
		
		myCalendarLabel = new JTextArea();
		myCalendarLabel.setFont(myCalendarLabel.getFont().deriveFont(20f));
		myCalendarLabel.setEditable(false);
		myCalendarLabel.setBounds(new Rectangle(20, 120, 760, 330));
		TitledBorder title;
		Border blackline = BorderFactory.createLineBorder(Color.black);
		title = BorderFactory.createTitledBorder(blackline, "Number of upcoming auctions: " + myData.totalNumberOfUpcomingAuctions());
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleFont(title.getTitleFont().deriveFont(16f));
		title.setTitleFont(title.getTitleFont().deriveFont(Font.PLAIN));
		
		myCalendarLabel.setBorder(title);
		Calendar c = new Calendar(Data.getInstance().getCurrentDateTime());
		String txt = c.getCalendar();
		myCalendarLabel.setText(txt);
		add(myCalendarLabel);
		
		repaint();
	}
	
	private void showErrorMessage() {
		
		myErrorMsg.setText("Invalid input. Please enter positive number (> 0)");
		add(myErrorMsg);
		repaint();
	}
	
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