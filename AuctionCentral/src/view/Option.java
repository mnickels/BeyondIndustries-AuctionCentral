package view;

/**
 * Represents an option that the user can select from a menu.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Option implements UIComponent {
	
	/** The number associated with this option. */
	private final int myNumber;
	/** The text representation of this option. */
	private final String myText;
	/** The next menu to display if this choice is selected by the user. */
	private final Menu myNextMenu;
	
	/**
	 * Creates an option for user selection in a menu UI element.
	 * @param theNumber the number associated with this option.
	 * @param theText the text representation of this option.
	 * @param theNextMenu the next menu to be displayed if this option is selected.
	 */
	public Option(int theNumber, String theText, Menu theNextMenu) {
		myNumber = theNumber;
		myText = theText;
		myNextMenu = theNextMenu;
	}
	
	public int getNumber() {
		return myNumber;
	}
	
	/**
	 * Gets the next menu to be displayed after this one.
	 * @return The menu that should follow if this option is selected.
	 */
	public Menu getNextMenu() {
		return myNextMenu;
	}

	@Override
	public void display() {
		System.out.printf("%d. %s\n", myNumber, myText);
	}

}
