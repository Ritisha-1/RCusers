package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetMapper extends BasicDbMapper<ResultSetHolder> {

	@Override
	protected ResultSetHolder doMap(ResultSet rs) throws SQLException {
		ResultSetHolder h = null;
		while (rs.next()) {
			if (h == null) {
				ResultSetMetaData metaData = rs.getMetaData();
				String[] header = new String[metaData.getColumnCount()];
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					header[i] = metaData.getColumnLabel(i + 1);
				}
				h = new ResultSetHolder(header);
			}
			Object[] row = new Object[h.getHeader().length];
			for (int i = 0; i < h.getHeader().length; i++) {
				Object ob = rs.getObject(i + 1);
				if (ob == null) {
					row[i] = "";
				} else {
					row[i] = ob.toString();
				}
			}
			h.addRow(row);

		}
		return h;
	}
}
