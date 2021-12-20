package ru.avalon.javapp.devj120.avalontelecom;

import ru.avalon.javapp.devj120.avalontelecom.lists.ClientList;
import ru.avalon.javapp.devj120.avalontelecom.ui.MainFrame;

import java.io.IOException;

/**
 * Application entry point, which only purpose is to create and show application main window.
 */
public class Main {
	/**
	 * Application entry point; creates and shows application main window.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		try {
			ClientList.init();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Some kind of problem with reading file " + ClientList.FILE_NAME);
			System.exit(1);
		}
		new MainFrame().setVisible(true);
	}
}
