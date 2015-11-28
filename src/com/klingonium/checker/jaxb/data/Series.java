package com.klingonium.checker.jaxb.data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klingo on 28.11.2015.
 */
@XmlRootElement(name = "Series")
@XmlAccessorType(XmlAccessType.FIELD)
public class Series {

	@XmlAttribute(name = "Name", required = true)
	private String name;
	@XmlElement(name = "Season")
	private List<Season> seasons = new ArrayList<Season>();

	public Series() {
	}

	public Series(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}
}
