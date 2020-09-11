package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IntMapper extends DbMapper<Integer> {

	@Override
	protected Collection<Integer> doMap(ResultSet rs) throws SQLException{
		List<Integer> lst = new ArrayList<Integer>();
		while (rs.next()) {
			lst.add(rs.getInt(1));
		}
		return lst;
	}
}
