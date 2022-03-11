package win_client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bus.Customer;
import bus.Manager;
import bus.RaiseException;

public class BankCustomerHomeWindow {

	private JFrame frame;
	private JTextField textLogin;
	private JTextField textFieldPassword;
	
	Manager manager = Manager.getInstance();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankCustomerHomeWindow window = new BankCustomerHomeWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BankCustomerHomeWindow() {
		initialize();
		
		textLogin.setText("123");
		textFieldPassword.setText("123");
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 564, 366);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(55, 77, 61, 26);
		frame.getContentPane().add(lblLogin);

		JLabel lblPassword = new JLabel("Pin:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(55, 129, 82, 26);
		frame.getContentPane().add(lblPassword);

		textLogin = new JTextField();
		textLogin.setBounds(133, 77, 198, 31);
		frame.getContentPane().add(textLogin);
		textLogin.setColumns(10);

		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(133, 129, 198, 31);
		frame.getContentPane().add(textFieldPassword);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Customer c = new Customer(Integer.parseInt(textLogin.getText()),
						Integer.parseInt(textFieldPassword.getText()));

				try {
					c = manager.verifyCustomer(c);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RaiseException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}

				if (c != null) {
					JOptionPane.showMessageDialog(null, "Login successful");

					CustomerTransWindow window = new CustomerTransWindow(c);
					window.getFrame().setVisible(true);
					
				} else {
					JOptionPane.showMessageDialog(null, "Login failed");

				}

			}
		});
		btnLogin.setBounds(133, 194, 89, 46);
		frame.getContentPane().add(btnLogin);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(290, 194, 89, 46);
		frame.getContentPane().add(btnExit);
	}
}
