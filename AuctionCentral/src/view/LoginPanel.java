package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.UIController;

public class LoginPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Dimension of components in the Login Panel.
	 */
	private final Dimension labelTitleDim = new Dimension(450, 200);
	
	private final Dimension labelCreditDim = new Dimension(200, 20);
	
	private final Dimension labelUsernameDim = new Dimension(70, 20);
	
	private final Dimension fieldUsernameDim = new Dimension(200, 20);
	
	private final Dimension btnLoginDim = new Dimension(100, 30);
		
	private final Dimension topPanelDim = new Dimension(450, 450);
	
	private final Dimension midPanelDim = new Dimension(400, 160);
	
	private final Dimension botPanelDim = new Dimension(800, 100);
	
	/**
	 * 
	 */
	private UIController myController;
		
	/**
	 * Constructor of the LoginPanel class
	 * @param the controller for this panel
	 */
	public LoginPanel(UIController theController) {
		super();
		myController = theController;
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 0));
		
		setupLoginMenu();
	}

	/**
	 * initialize all the components in the login panel.
	 */
	private void setupLoginMenu() {
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(topPanelDim);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 50));
		add(topPanel);
		
		JLabel labelTitle = new JLabel("Auction Central");
		labelTitle.setFont(labelTitle.getFont().deriveFont(60f));
		labelTitle.setPreferredSize(labelTitleDim);
		topPanel.add(labelTitle);
		
		JPanel midPanel = new JPanel();
		midPanel.setPreferredSize(midPanelDim);
		add(midPanel);
		
		JLabel labelUsername = new JLabel("Username: ");
		labelUsername.setPreferredSize(labelUsernameDim);
		midPanel.add(labelUsername);
		
		JTextField fieldUsername = new JTextField();
		fieldUsername.setPreferredSize(fieldUsernameDim);
		midPanel.add(fieldUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setPreferredSize(btnLoginDim);
		midPanel.add(btnLogin);
		
		JPanel botPanel = new JPanel();
		botPanel.setPreferredSize(botPanelDim);
		botPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		add(botPanel);
		
		JLabel labelCredit = new JLabel("Created by: Beyond Industries");
		labelCredit.setPreferredSize(labelCreditDim);
		botPanel.add(labelCredit);
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fieldUsername.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Sorry, the username cannot be empty");
				} else {
					myController.validateLoginInfo(fieldUsername.getText());
				}
			}
		});
	}
}
