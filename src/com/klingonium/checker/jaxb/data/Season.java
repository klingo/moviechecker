package com.klingonium.checker.jaxb.data;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klingo on 28.11.2015.
 */
@XmlRootElement(name = "Season")
@XmlAccessorType(XmlAccessType.FIELD)
public class Season  {

	@XmlAttribute(name = "Number", required = true)
	private int seasonNumber;
	@XmlElement(name = "Episode")
	private List<Episode> episodes = new ArrayList<Episode>();

	public Season(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public Season() {
	}

	public void addEpisode(Episode episode) {
		getEpisodes().add(episode);
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public String getFullSeasonName() {
		return "Season " + StringUtils.leftPad(Integer.toString(getSeasonNumber()), 2, "0");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Season) {
			return StringUtils.equals(Integer.toString(getSeasonNumber()), Integer.toString(((Season) obj).getSeasonNumber()));
		}
		return super.equals(obj);
	}
}
