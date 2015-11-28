package com.klingonium.checker;

import com.klingonium.checker.data.Collection;
import com.klingonium.checker.data.Episode;
import com.klingonium.checker.data.Season;
import com.klingonium.checker.data.Series;
import com.klingonium.checker.jaxb.JAXB;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		doSomething();
	}

	public static void doSomething() {
		Collection coll = new Collection();
		coll.setPath("Test-Path");
		coll.setLastCheck(new Date());

		Series series = new Series("Non Stop Power");
		Season s1 = new Season(1);
		Episode e1 = new Episode(1);
		s1.getEpisodes().add(e1);

		series.getSeasons().add(s1);

		Series series2 = new Series("Everlasting Stopper");

		coll.getSeries().add(series);
		coll.getSeries().add(series2);

		JAXB.marshal(coll);
		JAXB.unmarshal("");

	}
}
