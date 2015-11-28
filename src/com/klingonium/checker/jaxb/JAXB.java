package com.klingonium.checker.jaxb;

import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Series;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Klingo on 28.11.2015.
 */
public class JAXB {

	public static void marshal(Collection coll) {
		try {
			/* init jaxb marshaller */
			JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			/* set this flag to true to format the output */
			jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);

			/* marshaling of java objects in xml */
			jaxbMarshaller.marshal(coll, System.out);
			jaxbMarshaller.marshal(coll, new File("G:/moviechecker.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void unmarshal(String xmlPath) {
		xmlPath = StringUtils.defaultIfEmpty(xmlPath, "G:/moviechecker.xml");

		try {
			/* init jaxb marshaller */
			JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			Collection coll = (Collection) jaxbUnmarshaller.unmarshal(new File(xmlPath));

			for (Series series : coll.getSeries()) {
				System.out.println(series.getName());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
