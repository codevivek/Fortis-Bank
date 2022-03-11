package win_client;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import bus.Account;
import bus.Customer;
import bus.EnumAccountType;
import bus.Manager;
import bus.RaiseException;

public class CloseAccountWindow {

	private JFrame frame;

	private JComboBox<Integer> comboBoxIds;

	private List<Integer> ids = new ArrayList<Integer>();

	Manager manager = Manager.getInstance();

	/**
	 * Create the application.
	 * 
	 * @throws RaiseException
	 */
	public CloseAccountWindow() {
		initialize();

		Manager manager = Manager.getInstance();

		try {
			for (Customer c : manager.getCustomerList()) {
				if (c.getAccounts().size() > 1) {
					comboBoxIds.addItem(c.getCustomerId());
					ids.add(c.getCustomerId());
				}
			}
		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e);

			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
			}
		});
		frame.setBounds(100, 100, 388, 273);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblOpenAccountForCustomer = new JLabel("Close An Account For Customer");
		lblOpenAccountForCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOpenAccountForCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpenAccountForCustomer.setBounds(10, 11, 358, 36);
		frame.getContentPane().add(lblOpenAccountForCustomer);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExit.setBounds(279, 202, 85, 27);
		frame.getContentPane().add(btnExit);

		JLabel lblCustomerID = new JLabel("Customer ID : ");
		lblCustomerID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCustomerID.setBounds(24, 98, 99, 19);
		frame.getContentPane().add(lblCustomerID);

		JComboBox<EnumAccountType> comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setBounds(182, 140, 129, 25);
		frame.getContentPane().add(comboBox);

		comboBoxIds = new JComboBox<Integer>();
		comboBoxIds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String selectedItem = comboBoxIds.getSelectedItem().toString();

					if (selectedItem.length() > 0) {

						Customer c = manager.findCustomerById(Integer.parseInt(selectedItem));

						if (c != null) {

							comboBox.removeAllItems();

							for (Account acc : c.getAccounts()) {

								switch (acc.getAccountType()) {
									case Credit:

										comboBox.addItem(EnumAccountType.Credit);
										break;
									case Saving:

										comboBox.addItem(EnumAccountType.Saving);
										break;
									case Currency:

										comboBox.addItem(EnumAccountType.Currency);
										break;

									default:
										break;
								}
							}
						} else {
							comboBox.removeAllItems();
						}
					}
				} catch (NumberFormatException ex) {
//					JOptionPane.showMessageDialog(null, "Please Enter valid input!");
				} catch (Exception ex) {

				}
			}
		});

		comboBoxIds.setEditable(true);
		comboBoxIds.setBounds(182, 98, 129, 22);
		frame.getContentPane().add(comboBoxIds);

		JLabel lblselectAccountType = new JLabel("Select Account Type : ");
		lblselectAccountType.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblselectAccountType.setBounds(22, 141, 160, 23);
		frame.getContentPane().add(lblselectAccountType);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Customer c = null;
				try {
					c = manager.findCustomerById((int) comboBoxIds.getSelectedItem());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RaiseException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				EnumAccountType accType = (EnumAccountType) comboBox.getSelectedItem();

				if (c != null && accType != null) {

					int dialogResult = JOptionPane.showConfirmDialog(btnSubmit,
							"Are you sure?\nYou want to delete " + accType + " Account");

					if (dialogResult == 0) {

						boolean status = false;
						try {
							status = manager.closeAccount(c, accType);
							
							
							
						} catch (RaiseException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String message;

						if (status)
							message = accType + " account deleted";
						else
							message = "Something went wrong!";

						JOptionPane.showMessageDialog(btnSubmit, message);

						frame.setVisible(false);
					}
				}

				else {
					JOptionPane.showMessageDialog(btnSubmit, "Invalid selection");
				}

			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmit.setBounds(10, 202, 85, 27);
		frame.getContentPane().add(btnSubmit);

	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

	public void comboFilter(String enteredText) {

		if (comboBoxIds.isPopupVisible())
			comboBoxIds.showPopup();

		List<Integer> filterArray = new ArrayList<Integer>();

		for (int i = 0; i < ids.size(); i++) {

			String id = Integer.toString(ids.get(i));

			if (id.contains(enteredText))
				filterArray.add(ids.get(i));
		}

		if (!filterArray.isEmpty()) {

			comboBoxIds.removeAllItems();

			for (Integer id : filterArray)
				comboBoxIds.addItem(id);
		}

		comboBoxIds.getEditor().setItem(enteredText);

	}
}
