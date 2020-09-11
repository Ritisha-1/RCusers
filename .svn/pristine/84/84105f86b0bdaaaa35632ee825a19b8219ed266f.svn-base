package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoubleMapper extends DbMapper<Double> {

	@Override
	protected Collection<Double> doMap(ResultSet rs)throws SQLException {
		List<Double> lst = new ArrayList<Double>();
		while (rs.next()) {
			lst.add(rs.getDouble(1));
		}
		return lst;
	}
}
