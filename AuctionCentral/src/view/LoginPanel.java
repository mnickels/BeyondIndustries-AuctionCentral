package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.UIController;

public class LoginPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private UIController myController;
	
	private JLabel myLabelError;
	
	public LoginPanel(UIController theController) {
		super();
		myController = theController;
		this.setLayout(null);
		
		setupLoginMenu();
	}

	private void setupLoginMenu() {
		JLabel labelTitle = new JLabel("Auction Central");
		labelTitle.setFont(labelTitle.getFont().deriveFont(60f));
		labelTitle.setBounds(170, 50, 600, 200);
		add(labelTitle);
		
		JLabel labelCredit = new JLabel("Created by: Beyond Industries");
		labelCredit.setBounds(620, 630, 200, 20);
		add(labelCredit);
		
		JLabel labelUsername = new JLabel("Username: ");
		labelUsername.setBounds(250, 450, 70, 20);
		add(labelUsername);
		
		JTextField fieldUsername = new JTextField();
		fieldUsername.setBounds(330, 450, 200, 20);
		add(fieldUsername);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(330, 490, 100, 30);
		add(btnLogin);
		
		myLabelError = new JLabel();
		myLabelError.setBounds(250, 420, 350, 20);
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fieldUsername.getText().isEmpty()) {
					showEmptyFieldError();
				} else {
					myController.validateLoginInfo(fieldUsername.getText());
				}
			}
		});
	}
	
	public void showInvalidUsernameError() {
		myLabelError.setText("Sorry, the username is invalid");
		add(myLabelError);
		repaint();
	}
	
	public void showEmptyFieldError() {
		myLabelError.setText("Sorry, the username cannot be empty");
		add(myLabelError);
		repaint();
	}
}
