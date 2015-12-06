package com.klingonium.checker;

import com.klingonium.checker.jaxb.JAXB;
import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Episode;
import com.klingonium.checker.jaxb.data.Season;
import com.klingonium.checker.jaxb.data.Series;
import com.klingonium.checker.utilities.FileUtility;
import com.klingonium.checker.utilities.Settings;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Klingo on 06.12.2015.
 */
public class Checker {

	public void doSomething() {
		Collection coll = new Collection();
		coll.setPath("G:/moviechecker.xml");
		coll.setLastCheck(new Date());

		Series series = new Series("Non Stop Power");
		Season s1 = new Season(1);
		Episode e1 = new Episode(1);
		s1.getEpisodes().add(e1);

		series.getSeasons().add(s1);

		Series series2 = new Series("Everlasting Stopper");

		coll.getSeries().add(series);
		coll.getSeries().add(series2);

//		JAXB.marshal(coll);
//		JAXB.unmarshal("");

		initCheck();
		Collection collection = loadXMLFile();

		collection = initCollection();

		saveXMLFile(collection);

//		GUI gui = new GUI();
//		GUI2 gui = new GUI2();
	}

	private Collection initCollection() {
		checkCollectionBasePath();

		File basePath = new File(Settings.getCollectionBasePath());

		Collection collection = new Collection();

		// set the path to the collection
		collection.setPath(basePath.getAbsolutePath());

		for (File folder : FileUtility.getSubfolderList(basePath)) {
			Series series = new Series(folder.getName());

			for (File subFolder : FileUtility.getSubfolderList(folder)) {
				String folderName = subFolder.getName();
				String strSeason = StringUtils.substringAfter(folderName, StringUtils.SPACE);

				if (!StringUtils.isNumeric(strSeason)) {
					continue;
				}

				int intSeason = Integer.parseInt(strSeason);
				Season season = new Season(intSeason);

				for (File episodeFile : FileUtility.getSubfolderList(subFolder)) {
					String fileName = episodeFile.getName();

//					The.Mentalist.S07E12E13.Brown.Shag.Carpet.and.White.Orchids
//					The.Big.Bang.Theory.S07E03.The.Scavenger.Vortex.720p.HDTV

					Pattern pattern = Pattern.compile("([a-zA-Z0-9.-]*)\\.S(\\d{2,4})E(\\d{2})E\\.([a-zA-Z0-9.-]*)\\.([pSHD.TV01278]{4,5})\\..*([aikmpv4]{3})");
					Matcher matcher = pattern.matcher(fileName);
					System.out.println(matcher.group());

					Episode episode = new Episode(0);
					episode.setEpisodeTitle(fileName);

					season.addEpisode(episode);
				}

				series.addSeason(season);
			}

			// only add the series if there are any seasons
			if (series.getSeasons().size() > 0) {
				collection.addSeries(series);
			}
		}

		return collection;
	}

	private void initCheck() {
		checkXmlPath();
		checkCollectionBasePath();
	}

	private Collection loadXMLFile() {
		checkXmlPath();
		return JAXB.unmarshal();
	}

	private void saveXMLFile(Collection collection) {
		checkXmlPath();
		// update the path when saving the file
		collection.setPath(Settings.getCollectionBasePath());
		JAXB.marshal(collection);
	}

	private void checkXmlPath() {
		if (StringUtils.isEmpty(Settings.getXMLPath())) {
			File folder = FileUtility.getSelectedFolder("Please chose folder for saving moviechecker.xml");
			Settings.setXMLPath(folder.getAbsolutePath());
		}
	}

	private void checkCollectionBasePath() {
		if (StringUtils.isEmpty(Settings.getCollectionBasePath())) {
			File folder = FileUtility.getSelectedFolder("Please chose base folder of movie collection");
			Settings.setCollectionBasePath(folder.getAbsolutePath());
		}
	}

}