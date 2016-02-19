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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Klingo on 06.12.2015.
 */
public class Checker {

	private final String REGEX_MAIN_PART = "([\\w.-]*)\\.S(\\d{2,4})E(\\d{2})[E-]?(\\d{2})?\\.(.*)";
	private final String REGEX_END_PART1 = "(.*)\\.([01278p]{4,5})\\.(.*)\\.([aikmpv4]{3})";
	private final String REGEX_END_PART2 = "(.*)()\\.([HSDTV.]{4,6})\\.([aikmpv4]{3})";
	private final String REGEX_END_PART3 = "^([^720p].*)()()\\.([aikmpv4]{3})";
	private final String REGEX_END_PART4 = "^()([01278p]{4,5})\\.(HDTV|HD.TV|WEB-DL|BluRay).*\\.([aikmpv4]{3})$";
	private final String REGEX_END_SRT = ".*(srt|nfo|nfo-orig)$";

	public void doSomething() {
//		JAXB.marshal(coll);
//		JAXB.unmarshal("");

		initCheck();
		Collection xmlCollection = loadXMLFile();

		Collection fileSystemCollection = initCollection();

		fileSystemCollection.removeDuplicatesFrom(xmlCollection);

		xmlCollection.appendDataFrom(fileSystemCollection);

		// this is problematic, fileSystemCollection is handed over as a reference and therefore can become empty after the cleanup!
		saveXMLFile(xmlCollection);
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

				for (File episodeFile : FileUtility.getSubfolderFileList(subFolder)) {
					String fileName = episodeFile.getName();

					Pattern pattern = Pattern.compile(REGEX_END_SRT, Pattern.MULTILINE);
					Matcher matcher = pattern.matcher(fileName);

					if (matcher.matches()) {
						// skip entry, since it's just a subtitle file
						continue;
					}

					pattern = Pattern.compile(REGEX_MAIN_PART);
					matcher = pattern.matcher(fileName);

					String mTitle = StringUtils.EMPTY;
					String mQuality = StringUtils.EMPTY;
					String mType = StringUtils.EMPTY;
					String mFileEnding = StringUtils.EMPTY;
//					String mSeries = StringUtils.EMPTY;
//					String mSeason = StringUtils.EMPTY;
					String mEpisodeOne = StringUtils.EMPTY;
					String mEpisodeTwo = StringUtils.EMPTY;
					String remaining = StringUtils.EMPTY;

					if (matcher.matches()) {
//						mSeries = matcher.group(1);
//						mSeason = matcher.group(2);
						mEpisodeOne = matcher.group(3);
						mEpisodeTwo = matcher.group(4);
						remaining = matcher.group(5);

						pattern = Pattern.compile(REGEX_END_PART1);
						matcher = pattern.matcher(remaining);

						if (matcher.matches()) {
							mTitle = matcher.group(1);
							mQuality = matcher.group(2);
							mType = matcher.group(3);
							mFileEnding = matcher.group(4);
						} else {
							pattern = Pattern.compile(REGEX_END_PART2);
							matcher = pattern.matcher(remaining);

							if (matcher.matches()) {
								mTitle = matcher.group(1);
								mQuality = matcher.group(2);
								mType = matcher.group(3);
								mFileEnding = matcher.group(4);
							} else {
								pattern = Pattern.compile(REGEX_END_PART3);
								matcher = pattern.matcher(remaining);

								if (matcher.matches()) {
									mTitle = matcher.group(1);
									mQuality = matcher.group(2);
									mType = matcher.group(3);
									mFileEnding = matcher.group(4);
								} else {
									pattern = Pattern.compile(REGEX_END_PART4);
									matcher = pattern.matcher(remaining);

									if(matcher.matches()) {
										mTitle = matcher.group(1);
										mQuality = matcher.group(2);
										mType = matcher.group(3);
										mFileEnding = matcher.group(4);
									} else {
										System.err.println("Filename could not be correctly identified: " + fileName);
									}
								}
							}
						}
					}

					if (StringUtils.isEmpty(mEpisodeOne)) {
						System.err.println("File without valid episode number: " + fileName);
						continue;
					}

					Episode episode = new Episode(Integer.parseInt(mEpisodeOne));
					episode.setEpisodeTitle(mTitle);
					episode.setQuality(mQuality);
					episode.setMediaType(mType);
					episode.setFileType(mFileEnding);
					episode.setPlayed(false);
					season.addEpisode(episode);

					if (StringUtils.isNotEmpty(mEpisodeTwo)) {
						Episode episodeTwo = new Episode(Integer.parseInt(mEpisodeTwo));
						episodeTwo.setEpisodeTitle(mTitle);
						episodeTwo.setQuality(mQuality);
						episodeTwo.setMediaType(mType);
						episodeTwo.setFileType(mFileEnding);
						episodeTwo.setPlayed(false);
						season.addEpisode(episodeTwo);
					}
				}

				series.addSeason(season);
			}

			// only add the series if there are any seasons
			if (series.getSeasons().size() > 0) {
				collection.addSeries(series);
			}
		}

		collection.setLastCheck(new Date());

		return collection;
	}

	private void initCheck() {
		checkXmlPath();
		checkCollectionBasePath();
	}

	public Collection loadXMLFile() {
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