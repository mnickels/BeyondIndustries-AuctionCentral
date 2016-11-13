package view;

import java.util.Scanner;

/**
 * UI element used to display a prompt for user input
 * and return the user input given by the user.
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 11 2016
 */
public class Input implements UIComponent {
	
	private static final String DEFAULT_PROMPT = " -> ";
	
	private static final Scanner INPUT = new Scanner(System.in);
	
	/** The prompt to display before receiving user input. */
	private final String myPrompt;
	/** The input provided by the user. */
	private String myResult;
	
	public Input() {
		this(DEFAULT_PROMPT);
	}
	
	/**
	 * Creates a new input reading UI element.
	 * @param thePrompt the prompt to display before receiving user input.
	 */
	public Input(String thePrompt) {
		myPrompt = thePrompt;
	}
	
	@Override
	public void display() {
		System.out.print(myPrompt);
		myResult = INPUT.nextLine();
	}
	
	/**
	 * Gets the input that the user provided.
	 * @return Input provided by the user.
	 */
	public String getInput() {
		return myResult;
	}

}
