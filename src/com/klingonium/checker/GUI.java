package com.klingonium.checker;

import com.klingonium.checker.jaxb.data.Collection;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Klingo on 28.11.2015.
 */
public class GUI extends JFrame {
	private JTree treeTable;
	private JPanel rootPanel;
	private JButton closeButton;
	private JScrollPane scrollPane;

	public GUI() {
		setTitle("My Empty Frame");
		setSize(500,500); // default size is 0,0
		setLocation(400,200); // default is 0,0 (top left corner)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
		TreeModel treeModel = new DefaultTreeModel(top);
		treeModel.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeNodesChanged(TreeModelEvent e) {

			}

			@Override
			public void treeNodesInserted(TreeModelEvent e) {

			}

			@Override
			public void treeNodesRemoved(TreeModelEvent e) {

			}

			@Override
			public void treeStructureChanged(TreeModelEvent e) {

			}
		});

		treeTable = new JTree(treeModel);
		treeTable.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeTable.setShowsRootHandles(true);
		scrollPane = new JScrollPane(treeTable);

		setContentPane(rootPanel);
		setVisible(true);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void setData(Collection data) {
		System.out.println("TESTOMATO");
	}

	public void getData(Collection data) {
	}

	public boolean isModified(Collection data) {
		return false;
	}
}
