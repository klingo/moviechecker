package com.klingonium.checker.utilities;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Klingo on 06.12.2015.
 */
public class FileUtility {

	public static List<File> getSubfolderList(File folder) {
		File[] files = folder.listFiles(folderFilter);
		return (List<File>) Arrays.asList(files);
	}

	public static List<File> getSubfolderFileList(File folder) {
		File[] files = folder.listFiles();
		return (List<File>) Arrays.asList(files);
	}

	public static File getSelectedFile(String title) {
		return getSelectedObject(title, false);
	}

	public static File getSelectedFolder(String title) {
		return getSelectedObject(title, true);
	}

	private static File getSelectedObject(String title, boolean foldersOnly) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle(title);
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (foldersOnly) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getAbsoluteFile() : " + fileChooser.getSelectedFile().getAbsoluteFile());
			return fileChooser.getSelectedFile();
		} else {
			System.out.println("No Selection ");
			return new File("");
		}
	}

	private static FileFilter folderFilter = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
}
