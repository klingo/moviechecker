package com.klingonium.checker.utilities;

import org.apache.commons.lang3.StringUtils;

import java.util.prefs.Preferences;

/**
 * Created by Klingo on 06.12.2015.
 */
public class Settings {

	private static Preferences preferences = Preferences.userRoot().node(Settings.class.getName());
	private static final String XML_PATH = "XML_PATH";
	private static final String COLLECTION_BASE_PATH = "COLLECTION_BASE_PATH";
	private static final String FILE_NAME = "moviechecker.xml";

	public static String getXMLPath() {
		return preferences.get(XML_PATH, StringUtils.EMPTY);
	}

	public static String getXMLFilePath() {
		return getXMLPath() + FILE_NAME;
	}

	public static String getCollectionBasePath() {
		return preferences.get(COLLECTION_BASE_PATH, StringUtils.EMPTY);
	}

	public static void setXMLPath(String xmlPath) {
		preferences.put(XML_PATH, xmlPath);
	}

	public static void setCollectionBasePath(String collectionBasePath) {
		preferences.put(COLLECTION_BASE_PATH, collectionBasePath);
	}
}
