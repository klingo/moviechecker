package com.klingonium.checker.data;

import javax.xml.bind.annotation.*;

/**
 * Created by Klingo on 28.11.2015.
 */
@XmlRootElement(name = "Episode")
@XmlAccessorType(XmlAccessType.FIELD)
public class Episode {

	public Episode(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public Episode() {

	}

	@XmlAttribute(name = "Number", required = true)
	private int episodeNumber;
	@XmlElement(name = "Quality")
	private Quality quality;
	@XmlElement(name = "Played")
	private boolean played;

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}
}
