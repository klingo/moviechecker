package com.klingonium.checker.swing.table;

import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Episode;
import com.klingonium.checker.jaxb.data.Season;
import com.klingonium.checker.jaxb.data.Series;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Klingo on 09.12.2015.
 */
public class TreeTable {

	private String[] headings = {"Series Collection", "Media Type", "Quality", "File Type", "Played"};
	private RootNode root;
	private DefaultTreeTableModel model;
	private JXTreeTable treeTable;
	private Collection content;

	public TreeTable(Collection collection) {
		this.content = collection;
	}

	public JXTreeTable getTreeTable() {
		root = new RootNode("Root");

		for (Series series : this.content.getSeries()) {
			ChildNode seriesNode = new ChildNode(new String[]{series.getName()});
			for (Season season : series.getSeasons()) {
				ChildNode seasonNode = new ChildNode(new String[]{season.getFullSeasonName()});
				for (Episode episode : season.getEpisodes()) {
					ChildNode episodeNode = new ChildNode(new String[]{episode.getFullEpisodeName(), episode.getMediaType(), episode.getQuality(), episode.getFileType(), Boolean.toString(episode.isPlayed())});
					seasonNode.add(episodeNode);
				}
				seriesNode.add(seasonNode);
			}
			root.add(seriesNode);
		}

		model = new DefaultTreeTableModel(root, Arrays.asList(headings));
		treeTable = new JXTreeTable(model);
		treeTable.setShowGrid(true, true);
		treeTable.setColumnControlVisible(true);
		treeTable.setRowSelectionAllowed(true);
		treeTable.setColumnSelectionAllowed(false);
		treeTable.packAll();

		addTreeSelectionListener(treeTable);

		addKeyListener(treeTable);

//		treeTable.setDefaultRenderer(Object.class, new PlayedRenderer());

		return treeTable;
	}
//
//	final class PlayedRenderer extends DefaultTableCellRenderer {
//		PlayedRenderer() {
//		}
//
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//			JXTreeTable myTable = (JXTreeTable)table;
//			if (value != null && ((String) value).equals("false")) {
////				System.out.println("blub" + value);
//				setBackground(Color.red);
//				setForeground(Color.green);
//			}
//
////				System.out.println(myTable.getRowCount());
////				System.out.println(myTable.getValueAt(1,4));
//
//
//			return this;
//		}
//	}


	private void addKeyListener(JXTreeTable treeTable) {
		treeTable.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode() == 10) {
					if (treeTable.isExpanded(treeTable.getSelectedRow())) {
						treeTable.collapseRow(treeTable.getSelectedRow());
					} else {
						treeTable.expandRow(treeTable.getSelectedRow());
					}
					// Reset the selection to the "previous" row
					treeTable.getTreeSelectionModel().setSelectionPath(treeTable.getPathForRow(treeTable.getSelectedRow()-1));
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
	}

	private void addTreeSelectionListener(JXTreeTable treeTable) {
		treeTable.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath path = e.getPath();
				ChildNode node = (ChildNode) path.getLastPathComponent();
				System.out.println(node.getValueAt(0));
			}
		});
	}
}
