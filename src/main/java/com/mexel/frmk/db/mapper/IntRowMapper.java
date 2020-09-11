package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntRowMapper implements RowMapper<Integer> {

	@Override
	public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
		return rs.getInt(1);
	}

}
