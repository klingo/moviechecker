package com.klingonium.checker;

import com.klingonium.checker.jaxb.data.Collection;
import com.klingonium.checker.jaxb.data.Episode;
import com.klingonium.checker.jaxb.data.Season;
import com.klingonium.checker.jaxb.data.Series;
import com.klingonium.checker.jaxb.JAXB;
import com.klingonium.checker.utilities.FileUtility;
import com.klingonium.checker.utilities.Settings;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

public class Main {

	public static void main(String[] args) {
		Checker checker = new Checker();
		checker.doSomething();
	}
}
