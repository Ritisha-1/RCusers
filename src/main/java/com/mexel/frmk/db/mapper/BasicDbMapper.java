package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class BasicDbMapper<T> implements ResultSetExtractor<T> {

	@Override
	public T extractData(ResultSet rs) throws SQLException, DataAccessException {
		return doMap(rs);
	}

	protected abstract T doMap(ResultSet rs) throws SQLException;

	protected Date getDate(ResultSet rs, String columnName) throws SQLException {
		Date sqlDate = rs.getDate(columnName);
		return sqlDate;
	}

	protected Integer getInteger(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if (value != null) {
			return Integer.parseInt(value);
		}
		return null;
	}

	protected Boolean getBoolean(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if (value != null) {
			return Integer.parseInt(value) == 1;
		}
		return null;
	}

	protected String getString(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if (value != null) {
			return value;
		}
		return null;

	}

}
