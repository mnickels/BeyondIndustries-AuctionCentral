package view;

import java.time.LocalDateTime;
import java.time.Month;

import model.Data;

/**
 * Given a certain date, creates a string representation of the upcoming Month schedule and displays it.
 * 
 * @author Matthew Subido
 * @version November 13 2016
 */
public class Calendar implements UIComponent {
	
	private String myCalendar;

	public Calendar(LocalDateTime theDate) {
		StringBuilder sb = new StringBuilder();
		LocalDateTime endDate = theDate.plusMonths(1);
		int dayOfYear = endDate.getDayOfYear();
		boolean datePassed = true;
		Data myData = Data.getInstance();
		sb.append("   Su      M      T      W      T      F      S\n");
		sb = month(sb, theDate.getMonth());
		Month holder = theDate.getMonth();
		while (endDate.getMonth() != theDate.getMonth() &&
				endDate.getDayOfMonth() != endDate.getDayOfMonth()) {
			sb.append("|");
			for (int x = 1; x < 8; x++) {
				datePassed = (datePassed && dayOfYear != theDate.getDayOfYear());
				if (x == theDate.getDayOfWeek().getValue() && 
						theDate.getMonth() == holder && datePassed) {
					sb.append(String.format(" %1$2d:0 |", theDate.getDayOfMonth())); //TODO
					theDate = theDate.plusDays(1);
				} else {
					sb.append("      |");
				}
			}
			sb.append('\n');
			if (theDate.getMonth() != holder) {
				sb = month(sb, theDate.getMonth());
			}
		}
		myCalendar = sb.toString();
	}
	
	private StringBuilder month(StringBuilder theString, Month theMonth) {
		theString.append("               [");
		theString.append(theMonth.name());
		theString.append("]\n");
		return theString;
	}
	
	@Override
	public void display() {
		myCalendar.toString();
	}

}
