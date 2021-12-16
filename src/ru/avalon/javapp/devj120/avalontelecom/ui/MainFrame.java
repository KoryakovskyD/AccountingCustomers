package ru.avalon.javapp.devj120.avalontelecom.ui;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import ru.avalon.javapp.devj120.avalontelecom.models.ClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.CompanyClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PersonClientInfo;
import ru.avalon.javapp.devj120.avalontelecom.models.PhoneNumber;

/**
 * Application main window.
 */
public class MainFrame extends JFrame {
	private final ClientListTableModel clientsTableModel = new ClientListTableModel();
	private final JTable clientsTable = new JTable();
	ClientDialog cd = new ClientDialog(this);

	
	public MainFrame() {
		super("AvalonTelecom Ltd. clients list");
		
		initMenu();
		initLayout();
		
		setBounds(300, 200, 600, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add Person", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addClientPerson());

		addMenuItemTo(operations, "Add Company", 'M',
				KeyStroke.getKeyStroke('M', InputEvent.ALT_DOWN_MASK),
				e -> addClientCompany());

        addMenuItemTo(operations, "Change", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeClient());

        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delClient());

        setJMenuBar(menuBar);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAndSave();
			}
		});
	}

	/**
	 * Auxiliary method, which creates menu item with specifies text, mnemonic and accelerator,
	 * installs specified action listener to the item, and adds the item to the specified menu.
	 * 
	 * @param parent menu, which the created item is added to
	 */
    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
            KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

	private void initLayout() {
		clientsTable.setModel(clientsTableModel);
		clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		add(clientsTable.getTableHeader(), BorderLayout.NORTH);
		add(new JScrollPane(clientsTable), BorderLayout.CENTER);
	}

	private void addClientPerson() {
		cd.prepareForAddPerson();
		while(cd.showModal()) {
			try {
				PhoneNumber pn = new PhoneNumber(cd.getAreaCode(), cd.getPhoneNum());
				clientsTableModel.addClientPerson(
						pn,
						cd.getClientName(),
						cd.getClientAddr(),
						cd.getBirth());
				return;
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void addClientCompany() {
		cd.prepareForAddCompany();
		while(cd.showModal()) {
			try {
				PhoneNumber pn = new PhoneNumber(cd.getAreaCode(), cd.getPhoneNum());
				clientsTableModel.addClientCompany(
						pn,
						cd.getClientName(),
						cd.getClientAddr(),
						cd.getDirectorName(),
						cd.getContactName());
				return;
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Client registration error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	private void changeClient() {
		int seldRow = clientsTable.getSelectedRow();
		if (seldRow == -1)
			return;

		ClientInfo ci = clientsTableModel.getClient(seldRow);

		if (ci instanceof PersonClientInfo) {
			cd.prepareForChangePerson(ci);
			if (cd.showModal()) {
				ci.setName(cd.getClientName());
				ci.setAddress(cd.getClientAddr());
				((PersonClientInfo) ci).setBirth(cd.getBirth());
				clientsTableModel.clientChanged(seldRow);
			}
		}

		if (ci instanceof CompanyClientInfo) {
			cd.prepareForChangeCompany(ci);
			if (cd.showModal()) {
				ci.setName(cd.getClientName());
				ci.setAddress(cd.getClientAddr());
				((CompanyClientInfo) ci).setContactName(cd.getContactName());
				((CompanyClientInfo) ci).setDirectorName(cd.getDirectorName());
				clientsTableModel.clientChanged(seldRow);
			}
		}
	}
	
	private void delClient() {
		int seldRow = clientsTable.getSelectedRow();
		if(seldRow == -1)
			return;
		
		ClientInfo ci = clientsTableModel.getClient(seldRow);
		if(JOptionPane.showConfirmDialog(this, 
				"Are you sure you want to delete client\n"
					+ "with phone number " + ci.getPhoneNumber() + "?", 
				"Delete confirmation", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			clientsTableModel.dropClient(seldRow);
		}
	}

	private void exitAndSave() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
				"Exit confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
			return;
		}

		try {
			clientsTableModel.save();
		} catch (IOException ex) {
			if (JOptionPane.showConfirmDialog(this,
					"Error happened while the data was saved: " + ex.getMessage() + ".\n"
							+ "Are you sure you want to exit and loose your data (Yes),"
							+ "or you want to keep working while trying to fix the problem (No)?",
					"Exit confirmation",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE) == JOptionPane.NO_OPTION) {
				return;
			}
		}

		dispose();
	}
}
