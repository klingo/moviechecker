package com.klingonium.checker.swing.table;

import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Episode;
import com.klingonium.checker.jaxb.data.Season;
import com.klingonium.checker.jaxb.data.Series;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

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
		treeTable.packAll();

		return treeTable;
	}
}
