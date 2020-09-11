package com.mexel.frmk.db.mapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import com.mexel.frmk.util.CommonUtils;

public abstract class DbMapper<T> {

	public Collection<T> map(ResultSet rs) throws SQLException {
		return doMap(rs);
	}

	protected abstract Collection<T> doMap(ResultSet rs) throws SQLException;

	public static Date getDate(ResultSet rs, int index) throws SQLException {
		String str = rs.getString(index);
		return CommonUtils.toDate(str);
	}
	

	public static Date getDate(ResultSet rs, String columnName)
			throws SQLException {
		String str = rs.getString(columnName);
		return CommonUtils.toDate(str);
	}

	public static Date getDateTime(ResultSet rs, String columnName)
			throws SQLException {
		String str = rs.getString(columnName);
		return CommonUtils.toDateTime(str);
	}
	
	public static Object getBlob(ResultSet rs, String columnName)
			throws Exception {
		byte[] st = (byte[]) rs.getObject(columnName);
		ByteArrayInputStream baip = new ByteArrayInputStream(st);
	    ObjectInputStream ois = new ObjectInputStream(baip);
	    return ois.readObject();
	}
	
	public static <T extends Enum<T>> T getEnum(ResultSet rs, String columnName, Class<T> type) throws SQLException {
		String val = rs.getString(columnName);
		if(!StringUtils.isEmpty(val)){
			return Enum.valueOf(type, val);
		}
		return null;
	}

	public static Date getTime(ResultSet rs, int index) throws SQLException {
		String str = rs.getString(index);
		return CommonUtils.toTime(str);
	}

	protected Date getTime(ResultSet rs, String string) throws SQLException {
		String str = rs.getString(string);
		return CommonUtils.toTime(str);
	}

	public static Integer getInt(ResultSet rs, int columnIndex)
			throws SQLException {
		return getInt(rs, columnIndex, null);
	}

	public static Integer getInt(ResultSet rs, String column)
			throws SQLException {
		return getInt(rs, column, null);
	}

	public static Long getLong(ResultSet rs, int columnIndex)
			throws SQLException {
		long val = rs.getLong(columnIndex);
		return val >0 ? val:null;
	}

	public static Long getLong(ResultSet rs, String column)
			throws SQLException {
		long val = rs.getLong(column);
		return val >0 ? val:null;
	}

	public static boolean getBoolean(ResultSet rs, int columnIndex)
			throws SQLException {
		return getInt(rs, columnIndex, 0) == 1;
	}

	public static boolean getBoolean(ResultSet rs, String coulmnIndex)
			throws SQLException {
		return getInt(rs, coulmnIndex, 0) == 1;

	}

	public static Integer getInt(ResultSet rs, int columnIndex,
			Integer defaultValue) throws SQLException {
		try{
			int r =rs.getInt(columnIndex); 
			return r >0 ? r:defaultValue;	
		}catch(Exception ex){
			return defaultValue;
		}
	}

	public static Integer getInt(ResultSet rs, String column,
			Integer defaultValue) throws SQLException {
		String value = rs.getString(column);
		return CommonUtils.asInt(value, defaultValue);
	}

	public static Double getDouble(ResultSet rs, int columnIndex,
			Double defaultValue) throws SQLException {
		String value = rs.getString(columnIndex);
		return CommonUtils.asDouble(value, defaultValue);
	}

	public static Double getDouble(ResultSet rs, String column,
			Double defaultValue) throws SQLException {
		String value = rs.getString(column);
		return CommonUtils.asDouble(value, defaultValue);

	}

	public static String getString(ResultSet rs, int columnIndex)
			throws SQLException {
		String value = rs.getString(columnIndex);
		if (!CommonUtils.isEmpty(value)) {
			return value;
		}
		return null;
	}

	public static String getString(ResultSet rs, String column)
			throws SQLException {
		String value = rs.getString(column);
		if (!CommonUtils.isEmpty(value)) {
			return value;
		}
		return null;
	}

	public static Float getFloat(ResultSet rs, int columnIndex)
			throws SQLException {
		String value = rs.getString(columnIndex);
		if (!CommonUtils.isEmpty(value)) {
			try {
				return Float.parseFloat(value);
			} catch (Exception ex) {
				return null;
			}
		}
		return null;
	}
}
