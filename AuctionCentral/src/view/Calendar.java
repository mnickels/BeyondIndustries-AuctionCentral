package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Data;

/**
 * Makes a visible calendar to display the auctions for a specified month.
 * 
 * @author Mike Nickels | mnickels@uw.edu
 * @version December 4 2016
 */
public class Calendar extends JPanel {

	public Calendar(LocalDate theDate) {
		super();
//		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
		setup(theDate);
	}
	
	private void setup(LocalDate theDate) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(month(theDate, LocalDate.of(theDate.getYear(), theDate.getMonth(),
				theDate.getYear() % 4 == 0 ? theDate.getMonth().maxLength() : theDate.getMonth().minLength())));
		add(month(LocalDate.of(theDate.plusMonths(1).getYear(), theDate.plusMonths(1).getMonth(), 1),
				theDate.plusMonths(1).minusDays(1)));
	}
	
	private JPanel month(LocalDate start, LocalDate end) {
		JPanel month = new JPanel(new BorderLayout());
		month.add(new JLabel(start.getMonth().toString(), JLabel.CENTER), BorderLayout.NORTH);
		month.getComponent(0).setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));

		JPanel days = new JPanel(new GridLayout(0, 7, 20, 20));
		
		for (int empty = 0; empty < start.getDayOfWeek().getValue() % 7; empty++) {
			JLabel j = new JLabel();
			j.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
			days.add(j);
		}
		
		int day = start.getDayOfMonth();
		while (day <= end.getDayOfMonth()) {
			JLabel j = new JLabel(String.format("%d : %d", day,
					Data.getInstance().getAuctionsForThisDay(LocalDate.of(start.getYear(), start.getMonth(), day)).size()));
			j.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
			days.add(j);
			day++;
		}
		
		month.add(days, BorderLayout.CENTER);
		
		return month;
	}

}
