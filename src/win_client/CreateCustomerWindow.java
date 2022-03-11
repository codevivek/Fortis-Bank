package win_client;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bus.Customer;
import bus.Manager;
import bus.RaiseException;

public class CreateCustomerWindow {

	private JFrame frmAsdf;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldPIN;
	Manager manager = Manager.getInstance();

	/**
	 * Create the application.
	 */
	public CreateCustomerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAsdf = new JFrame();
		frmAsdf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				frmAsdf.setVisible(false);
			}
		});
		frmAsdf.setBounds(100, 100, 328, 275);
		frmAsdf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmAsdf.getContentPane().setLayout(null);

		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFirstName.setBounds(20, 57, 111, 35);
		frmAsdf.getContentPane().add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name :");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLastName.setBounds(20, 95, 111, 35);
		frmAsdf.getContentPane().add(lblLastName);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(141, 65, 147, 27);
		frmAsdf.getContentPane().add(textFieldFirstName);
		textFieldFirstName.setColumns(10);

		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(141, 103, 147, 27);
		frmAsdf.getContentPane().add(textFieldLastName);
		textFieldLastName.setColumns(10);

		JLabel lblPIN = new JLabel("PIN : ");
		lblPIN.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPIN.setBounds(20, 137, 111, 27);
		frmAsdf.getContentPane().add(lblPIN);

		textFieldPIN = new JTextField();
		textFieldPIN.setBounds(141, 141, 147, 27);
		frmAsdf.getContentPane().add(textFieldPIN);
		textFieldPIN.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					Customer c = manager.createCustomer(textFieldFirstName.getText(), textFieldLastName.getText(),
							Integer.parseInt(textFieldPIN.getText()));

					if (c != null) {

						String msg = "New Customer created";
						msg = msg + "\nCustomer id is: " + c.getCustomerId();
						JOptionPane.showMessageDialog(null, msg);
						frmAsdf.setVisible(false);
					}

				} catch (NumberFormatException | RaiseException | SQLException ex) {

					JOptionPane.showMessageDialog(null, ex.getMessage());
				} 
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSubmit.setBounds(10, 195, 111, 28);
		frmAsdf.getContentPane().add(btnSubmit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frmAsdf.setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setBounds(177, 195, 111, 28);
		frmAsdf.getContentPane().add(btnCancel);

		JLabel lblCreateCustomer = new JLabel("Create Customer");
		lblCreateCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCreateCustomer.setBounds(20, 11, 268, 35);
		frmAsdf.getContentPane().add(lblCreateCustomer);
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frmAsdf;
	}
}
