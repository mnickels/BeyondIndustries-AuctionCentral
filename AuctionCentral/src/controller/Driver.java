package controller;

import java.awt.EventQueue;

import model.Serializer;

/**
 * @author Mike Nickels | mnickels@uw.edu
 * @version November 12 2016
 */
public final class Driver {
	
	private Driver() {
		// private constructor to prevent instantiation
	}

	public static void main(String[] args) {
		Serializer.main(null);
		EventQueue.invokeLater(new UIController());
	}

}
