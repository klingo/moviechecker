package com.klingonium.checker.data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Klingo on 28.11.2015.
 */
@XmlRootElement(name = "Collection")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection  {

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

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

}
