package com.klingonium.checker;

import com.klingonium.checker.jaxb.data.Collection;

import javax.swing.*;

/**
 * Created by Klingo on 28.11.2015.
 */
public class GUI extends JFrame {
	private JTree tree1;

	public GUI() {
		setTitle("My Empty Frame");
		setSize(300,200); // default size is 0,0
		setLocation(10,200); // default is 0,0 (top left corner)
	}

	public static void main(String[] args) {
		JFrame f = new GUI();
		f.setVisible(true);
	}

	public void setData(Collection data) {
	}

	public void getData(Collection data) {
	}

	public boolean isModified(Collection data) {
		return false;
	}
}
