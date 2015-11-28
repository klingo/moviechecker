package com.klingonium.checker.jaxb.data;

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

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
}
