package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mexel.frmk.service.LocalResultSet;
import com.mexel.frmk.service.RsIterator;

public class LocalResultSetMapper extends DbMapper<RsIterator>{
	
	

	@Override
	protected Collection<RsIterator> doMap(ResultSet rs) throws SQLException {
		String[] headers = null;
		List<String[]> rows = new ArrayList<String[]>();
		while (rs.next()) {
			if (headers == null) {
				ResultSetMetaData metaData = rs.getMetaData();
				headers = new String[metaData.getColumnCount()];
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					headers[i] = metaData.getColumnLabel(i + 1);
				}
			}
			String[] row = new String[headers.length];
			for (int i = 0; i < headers.length; i++) {
				String ob = rs.getString(i + 1);
				if (ob == null) {
					row[i] = "";
				} else {
					row[i] = ob;
				}
			}
			rows.add(row);

		}
		Collection<RsIterator> result = new ArrayList<RsIterator>();
		result.add(new LocalResultSet(headers, rows));
		return result;
	}
}
