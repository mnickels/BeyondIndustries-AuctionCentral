package view;

/**
 * A UI element separator, to be used to section off screens from other screens in a console output.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public final class Separator implements UIComponent {
	
	private static final int DEFAULT_WIDTH = 80;
	
	/** The singleton instance of Separator. */
	private static Separator instance;
	/** The width of the separator. */
	private final int myWidth;
	
	/**
	 * Creates a new separator ui element.
	 * @param theWidth the width of the separator element.
	 */
	private Separator(int theWidth) {
		myWidth = theWidth;
	}
	
	/**
	 * Get the singleton instance of separator.
	 * @return The separator ui element.
	 */
	public static Separator getInstance() {
		if (instance == null) {
			instance = new Separator(DEFAULT_WIDTH);
		}
		return instance;
	}

	@Override
	public void display() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < myWidth; i++) {
			sb.append('-');
		}
		System.out.println(sb.toString());
	}

}
