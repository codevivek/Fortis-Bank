package win_client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bus.RaiseException;

public class BankManagerHomeWindow {

	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankManagerHomeWindow window = new BankManagerHomeWindow();
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
	public BankManagerHomeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 335, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblManagerHome = new JLabel("Manager Home Page");
		lblManagerHome.setBounds(58, 11, 198, 25);
		lblManagerHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblManagerHome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.getContentPane().add(lblManagerHome);

		JButton btnCreateCustomer = new JButton("Create Customer");
		btnCreateCustomer.setBounds(31, 64, 260, 25);
		btnCreateCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateCustomerWindow window = new CreateCustomerWindow();
				window.getFrame().setVisible(true);
			}
		});
		btnCreateCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(btnCreateCustomer);

		JButton btnFindCustomer = new JButton("Find Customer By ID");
		btnFindCustomer.setBounds(31, 172, 260, 25);
		btnFindCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFindCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FindCustomerWindow window = new FindCustomerWindow();
				window.getFrame().setVisible(true);
			}
		});
		frame.getContentPane().add(btnFindCustomer);

		JButton btnOpenAccount = new JButton("Open An Account");
		btnOpenAccount.setBounds(31, 100, 260, 25);
		btnOpenAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				OpenAccountWindow window;
				try {
					window = new OpenAccountWindow();
					window.getFrame().setVisible(true);
				} catch (RaiseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnOpenAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(btnOpenAccount);

		JButton btnDisplayCustomerList = new JButton("Display Customer List");
		btnDisplayCustomerList.setBounds(31, 208, 260, 27);
		btnDisplayCustomerList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
				ListCustomerLWindow window;
					window = new ListCustomerLWindow();
					window.getFrame().setVisible(true);
				} catch (RaiseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDisplayCustomerList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(btnDisplayCustomerList);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(110, 264, 85, 27);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.getContentPane().add(btnExit);
		
		JButton btnCloseAccount = new JButton("Close Account");
		btnCloseAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CloseAccountWindow window = new CloseAccountWindow();
				
				window.getFrame().setVisible(true);
				
				
			}
		});
		btnCloseAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCloseAccount.setBounds(31, 136, 260, 25);
		frame.getContentPane().add(btnCloseAccount);

		
	}
}
