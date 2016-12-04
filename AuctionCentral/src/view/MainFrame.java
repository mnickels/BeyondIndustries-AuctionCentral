package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import controller.UIController;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private UIController myController;
	
	public MainFrame(UIController theController) {
		super("Auction Central");
		myController = theController;
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setName("AuctionCentral");

        setMinimumSize(new Dimension(800, 700));
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Logout", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.setVisible(true);
		
		menuItem.addActionListener(new loginActionListener());
		
		menu.add(menuItem);
		
		setJMenuBar(menuBar);
		
		this.setVisible(true);
	}

	private class loginActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			myController.clearProfile();
		}
	}
}
