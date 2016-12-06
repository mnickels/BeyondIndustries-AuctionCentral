package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.UIController;
import model.Data;
import model.Serializer;

/**
 * Main frame that will contain the panels.
 * It has two menu items, which are logout and load state.
 * @author Ming Hoi Lam
 *
 */
public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private UIController myController;
	
	public MainFrame(UIController theController) {
		super("Auction Central");
		myController = theController;
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setName("AuctionCentral");

        setPreferredSize(new Dimension(800, 700));
        pack();
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
		
		menu.addSeparator();
		
		JMenuItem fileChooser = new JMenuItem("Load State", KeyEvent.VK_S);
		fileChooser.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		fileChooser.setVisible(true);
		
		fileChooser.addActionListener(new ActionListener() {
		    public void actionPerformed(final ActionEvent e) {
		        JFileChooser choose = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "Serializable files", "ser");
		        choose.setFileFilter(filter);
		        choose.setCurrentDirectory(new File("./"));
		        int returnVal = choose.showOpenDialog(new JFrame("Please choose Serializable State File"));
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		           Data.setInstance((Data) Serializer.readFile(choose.getSelectedFile().getName()));
		        }
		    }
		});
		
		menu.add(fileChooser);
		
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
