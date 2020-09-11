package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringMapper extends DbMapper<String> {

	@Override
	protected Collection<String> doMap(ResultSet rs) throws SQLException{
		List<String> lst = new ArrayList<String>();
		while (rs.next()) {
			lst.add(rs.getString(1));
		}
		return lst;
	}
}
