package com.klingonium.checker.swing;

import com.klingonium.checker.Checker;
import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.swing.table.TreeTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klingo on 09.12.2015.
 */
public class GUITable {

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		List<String[]> content = new ArrayList<String[]>();
		content.add(new String[] {"Heading 1"});
		content.add(new String[] {"Text 1", "Text 2"});
		content.add(new String[] {"Heading 2"});
		content.add(new String[] {"Text 3", "Text 4"});
		content.add(new String[] {"Heading 3"});
		content.add(new String[] {"Text 5", "Text 6"});

		Checker checker = new Checker();

		Collection xmlCollection = checker.loadXMLFile();

		TreeTable treeTable = new TreeTable(xmlCollection);

		frame.setSize(500,500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(new JScrollPane(treeTable.getTreeTable()), BorderLayout.CENTER);

		frame.setVisible(true);

	}


}
