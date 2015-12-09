package com.klingonium.checker.swing.table;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * Created by Klingo on 09.12.2015.
 */
public class Node extends AbstractMutableTreeTableNode {

	public Node(Object[] data) {
		super(data);
	}

	@Override
	public Object getValueAt(int columnIndex) {
		return getData()[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return getData().length;
	}

	private Object[] getData() {
		return (Object[]) getUserObject();
	}
}
