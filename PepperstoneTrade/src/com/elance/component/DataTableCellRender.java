package com.elance.component;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DataTableCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6250584546698895932L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected,
			boolean hasFocus, int row, int column) {
		
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
		
		setHorizontalAlignment(JLabel.RIGHT);
		setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 5)));
		
		return this;
	}
}
