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
	 * @param theComponents
	 */
	public OptionlessMenu(String theQuery, Input theInput, UIComponent... theComponents) {
		super(theQuery, theInput, theComponents);
	}
	
	@Override
	public void display() {
		for (UIComponent c : myComponents) {
			c.display();
			System.out.println();
		}
		System.out.println(myQuery);
		System.out.println();
		myInput.display();
	}

}
