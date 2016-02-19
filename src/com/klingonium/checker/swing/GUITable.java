package com.klingonium.checker.swing;

import com.klingonium.checker.Checker;
import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.swing.table.TreeTable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klingo on 09.12.2015.
 */
public class GUITable {


	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton closeButton;

		// Create a new Checker class
		Checker checker = new Checker();

		// Load the collection XML
		Collection xmlCollection = checker.loadXMLFile();

		// Sort the collection
		xmlCollection.sortCollection();

		// Create the TreeTable based on the collection
		TreeTable treeTable = new TreeTable(xmlCollection);

		// Set the default close operation
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Set the size of the frame
		frame.setSize(800,800);

		// Set the position of the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim.width / 2) - (frame.getSize().width / 2), (dim.height / 2) - (frame.getSize().height / 2));

		// Set the Layout
		frame.setLayout(new BorderLayout());

		// Add the TreeTable to the center
		JScrollPane scrollPane = new JScrollPane(treeTable.getTreeTable());
		frame.add(scrollPane, BorderLayout.CENTER);

		// Create the close button and att it at the bttom
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.add(closeButton, BorderLayout.SOUTH);

		// Show the frame
		frame.setVisible(true);
	}


}
