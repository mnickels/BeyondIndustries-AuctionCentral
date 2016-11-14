package view;

/**
 * Composite UI component that describes a menu for the AuctionCentral system.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Menu implements UIComponent {
	
	/** The question being asked by this menu element. */
	protected final String myQuery;
	
	/** The options to display to the user as choices. */
	protected final Option[] myOptions;
	/** The input element that retrieves input from the user. */
	protected final Input myInput;
	
	/**
	 * Creates a new menu ui element.
	 * @param theQuery the query describing the decision to be made.
	 * @param theInput the input element that will retrieve user input.
	 */
	public Menu(String theQuery, Input theInput, Option... theOptions) {
		myQuery = theQuery;
		myInput = theInput;
		myOptions = theOptions;
	}
	
	public String getInput() {
		return myInput.getInput();
	}

	@Override
	public void display() {
		System.out.println(myQuery);
		for (Option o : myOptions) {
			o.display();
		}
		System.out.println();
		myInput.display();
	}

}
