package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LongMapper extends DbMapper<Long> {

	@Override
	protected Collection<Long> doMap(ResultSet rs) throws SQLException{
		List<Long> lst = new ArrayList<Long>();
		while (rs.next()) {
			lst.add(getLong(rs, 1));
		}
		return lst;
	}
}
