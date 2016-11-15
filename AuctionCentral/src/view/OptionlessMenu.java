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
	public OptionlessMenu(String theQuery, Input theInput, Option... theOptions) {
		super(theQuery, theInput, theOptions);
	}
	
	@Override
	public void display() {
		System.out.println(myQuery);
		System.out.println();
		myInput.display();
	}

}
