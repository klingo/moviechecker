package com.klingonium.checker.jaxb.data;

import org.apache.commons.lang3.StringUtils;

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
	@XmlElement(name = "Title")
	private String episodeTitle;
	@XmlElement(name = "Quality")
	private String quality;
	@XmlElement(name = "MediaType")
	private String mediaType;
	@XmlElement(name = "FileType")
	private String fileType;
	@XmlElement(name = "Played")
	private boolean played;

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean isPlayed() {
		return played;
	}

	public void setPlayed(boolean played) {
		this.played = played;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Episode) {
			return StringUtils.equals(Integer.toString(getEpisodeNumber()), Integer.toString(((Episode) obj).getEpisodeNumber()));
		}
		return super.equals(obj);
	}
}
