package com.mexel.frmk.service;

import java.util.Date;

public interface RsIterator {
	public boolean next();

	public String getString(int index);

	public String getString(String column);

	public String[] getColumnNames();

	public int getColumnCount();

	public String getColumnName(int index);

	public Integer getInt(int index);

	public Integer getInt(String columnName);
	
	public Long getLong(String columnName);
	
	public Date getDate(String columnName);

	public int getRowCount();
}
