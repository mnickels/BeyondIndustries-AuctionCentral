package view;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Composite UI component that describes a menu for the AuctionCentral system.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Menu implements UIComponent {
	
	/** The question being asked by this menu element. */
	protected final String myQuery;
	
	protected final UIComponent[] myComponents;
	/** The options to display to the user as choices. */
	protected final Collection<Option> myOptions;
	/** The input element that retrieves input from the user. */
	protected final Input myInput;
	
	/**
	 * Creates a new menu ui element.
	 * @param theQuery the query describing the decision to be made.
	 * @param theInput the input element that will retrieve user input.
	 */
	public Menu(String theQuery, Input theInput, UIComponent... theComponents) {
		myQuery = theQuery;
		myInput = theInput;
		myComponents = theComponents;
		myOptions = new ArrayList<>();
	}
	
	public void addOption(Option theOption) {
		myOptions.add(theOption);
	}
	
	public String getInput() {
		return myInput.getInput();
	}
	
	/**
	 * Gets the next menu to display.
	 * @return The next menu to display after this menu.
	 */
	public Menu nextMenu() {
		Menu next = null;
		while (next == null) {
			try {
				int selected = Integer.parseInt(getInput());
				for (Option o : myOptions) {
					if (o.getNumber() == selected) {
						next = o.getNextMenu();
					}
				}
			} catch (Exception e) {
//				System.err.println("Non-integer input");
			}
			if (next == null) {
				System.out.println("\nInvalid input, try again:\n");
				display();
			}
		}
		return next;
	}

	@Override
	public void display() {
		for (UIComponent c : myComponents) {
			c.display();
			System.out.println();
		}
		System.out.println(myQuery);
		for (Option o : myOptions) {
			o.display();
		}
		System.out.println();
		myInput.display();
	}

}
