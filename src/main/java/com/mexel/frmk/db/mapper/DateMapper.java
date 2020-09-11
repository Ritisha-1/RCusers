package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DateMapper extends DbMapper<Date> {

	@Override
	protected Collection<Date> doMap(ResultSet rs)throws SQLException {
		List<Date> lst = new ArrayList<Date>();
		while (rs.next()) {
			lst.add(getDate(rs,1));
		}
		return lst;
	}
}
