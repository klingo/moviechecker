package com.klingonium.checker.jaxb;

import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Series;
import com.klingonium.checker.utilities.FileUtility;
import com.klingonium.checker.utilities.Settings;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.*;
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
//			jaxbMarshaller.marshal(coll, System.out);
			jaxbMarshaller.marshal(coll, new File(Settings.getXMLFilePath()));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static Collection unmarshal() {
		String xmlPath = Settings.getXMLFilePath();

		Collection coll = new Collection();

		try {
			/* init jaxb marshaller */
			JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			File xmlFile = new File(xmlPath);

			if (xmlFile.exists()) {
				coll = (Collection) jaxbUnmarshaller.unmarshal(new File(xmlPath));
			}
		} catch (UnmarshalException e) {
			// the file could not be read - assume it does not exist yet!
			System.err.println(xmlPath + " could not be unmarshalled. File will be ignored.");
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return coll;
	}
}
