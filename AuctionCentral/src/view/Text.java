package view;

/**
 * A simple text ui element. Displays information to the user.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Text implements UIComponent {
	
	/** The text to display to the user. */
	private final String myText;
	
	/**
	 * Creates a new text ui element.
	 * @param theText the text to display to the user.
	 */
	public Text(String theText) {
		myText = theText;
	}

	@Override
	public void display() {
		System.out.println(myText);
	}

}
