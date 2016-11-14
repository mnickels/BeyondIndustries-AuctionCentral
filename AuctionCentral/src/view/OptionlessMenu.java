/**
 * 
 */
package view;

/**
 *
 * @author Mike Nickels | mnickels@uw.edu
 */
public class OptionlessMenu extends Menu {

	/**
	 * @param theQuery
	 * @param theInput
	 */
	public OptionlessMenu(String theQuery, Input theInput) {
		super(theQuery, theInput);
	}
	
	@Override
	public void display() {
		System.out.println(myQuery);
		System.out.println();
		myInput.display();
	}

}
