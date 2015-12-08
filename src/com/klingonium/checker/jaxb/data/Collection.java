package com.klingonium.checker.jaxb.data;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Klingo on 28.11.2015.
 */
@XmlRootElement(name = "Collection")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection {

	@XmlElement(name = "Path")
	private String path;
	@XmlAttribute(name = "LastCheck", required = true)
	private Date lastCheck;
	@XmlElement(name = "Series")
	private List<Series> series = new ArrayList<Series>();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}

	public void addSeries(Series series) {
		getSeries().add(series);
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public void removeDuplicatesFrom(Collection otherCollection) {
		for (Series otherSeries : otherCollection.getSeries()) {

			for (Series thisSeries : getSeries()) {
				if (otherSeries.equals(thisSeries)) {

					for (Season otherSeason : otherSeries.getSeasons()) {

						for (Season thisSeason : thisSeries.getSeasons()) {
							if (otherSeason.equals(thisSeason)) {

								for (Episode otherEpisode : otherSeason.getEpisodes()) {

									for (Episode thisEpisode : thisSeason.getEpisodes()) {
										if (otherEpisode.equals(thisEpisode)) {
											// Episode found!
											thisSeason.getEpisodes().remove(thisEpisode);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// now do the cleanup!
		for (int i = getSeries().size() - 1; i >= 0; i--) {
			Series thisSeries = getSeries().get(i);
			for (int j = thisSeries.getSeasons().size() - 1; j >= 0; j--) {
				Season thisSeason = thisSeries.getSeasons().get(j);
				if (thisSeason.getEpisodes().size() == 0) {
					thisSeries.getSeasons().remove(thisSeason);
				}
			}
			if (thisSeries.getSeasons().size() == 0) {
				getSeries().remove(thisSeries);
			}
		}
	}

	public void appendDataFrom(Collection otherCollection) {
		for (Series otherSeries : otherCollection.getSeries()) {
			// first, check if this season already exists
			boolean seriesFound = false;
			for (Series thisSeries : getSeries()) {
				if (thisSeries.equals(otherSeries)) {
					seriesFound = true;

					for (Season otherSeason : otherSeries.getSeasons()) {
						// then check if the season already exists
						boolean seasonFound = false;
						for (Season thisSeason : thisSeries.getSeasons()) {
							if (thisSeason.equals(otherSeason)) {
								seasonFound = true;

								for (Episode otherEpisode : otherSeason.getEpisodes()) {
									// finally check if the episode already exists
									boolean episodeFound = false;
									for(Episode thisEpisode : thisSeason.getEpisodes()) {
										if (thisEpisode.equals(otherEpisode)) {
											episodeFound = true;
											break;
										}
									}

									// Single episode was not found, add it
									if (!episodeFound) {
										System.out.println("INFO: Episode added: " + otherSeries.getName() + ", Season " + otherSeason.getSeasonNumber() + ", Episode " + otherEpisode.getEpisodeNumber() + " : " + otherEpisode.getEpisodeTitle());
										thisSeason.getEpisodes().add(otherEpisode);
									}
								}
							}
						}

						// Whole season was not found, add it
						if (!seasonFound) {
							System.out.println("INFO: Season added: " + thisSeries.getName() + ", Season " + otherSeason.getSeasonNumber());
							thisSeries.getSeasons().add(otherSeason);
						}
					}
				}
			}

			// whole series was not found, add it
			if (!seriesFound) {
				System.out.println("INFO: Series added: " + otherSeries.getName());
				addSeries(otherSeries);
			}
		}
	}
}
