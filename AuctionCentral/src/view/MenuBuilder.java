package view;

/**
 * Constructs all the menus needed for the program.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class MenuBuilder {
	
	// user story 4
	public static Menu NONPROFIT_MAIN_MENU;
	public static Menu NONPROFIT_CALENDAR_VIEW;
	
	// user story 1
	public static Menu BIDDER_AUCTION_MENU;
	
	public static void buildMenus() {
		// set up the menus
		NONPROFIT_MAIN_MENU = new Menu(
				"What would you like to do?",
				new Input());
		
		NONPROFIT_CALENDAR_VIEW = new Menu(
				null,
				new Input("Specify a day to view (enter the two digit date), or -1 to go back\n\n -> ")){
			@Override
			public void display() {
				System.out.println("***CALENDAR***");
				System.out.println();
				getInput().display();
			}
		};
		
//		BIDDER_AUCTION_MENU = new Menu(
//				);
		
		// add options to the menus (needs to happen after menus are instantiated)
		NONPROFIT_MAIN_MENU.addOption(new Option(1, "View calendar of upcoming auctions", NONPROFIT_CALENDAR_VIEW));
		NONPROFIT_MAIN_MENU.addOption(new Option(2, "Administrative functions", null));
		NONPROFIT_MAIN_MENU.addOption(new Option(3, "Exit AuctionCentral", null));
		
		
		NONPROFIT_CALENDAR_VIEW.addOption(new Option(-1, null, NONPROFIT_MAIN_MENU));
		
	}

}
