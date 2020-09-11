package com.mexel.frmk.db.mapper;

import java.util.ArrayList;
import java.util.List;

public class ResultSetHolder {
	private final List<Object[]> rows = new ArrayList<Object[]>();
	private final String[] header;

	public ResultSetHolder(String[] header) {
		this.header = header;
	}

	public String[] getHeader() {
		return header;
	}

	public List<Object[]> getRows() {
		return rows;
	}

	public void addRow(Object[] row) {
		rows.add(row);
	}

	// public static JSONObject getJsonObject(RsIterator rs, int count, int
	// page,
	// int rows, boolean cellAsArray) {
	// JSONObject jsonObj = new JSONObject();
	// if (rs == null) {
	// return jsonObj;
	// }
	//
	// int size = rs.getColumnCount();
	//
	// jsonObj.put("rows", getCellAsObject(rs));
	//
	// int total = count / rows;
	// if (count % rows != 0) {
	// total++;
	// }
	// jsonObj.put("page", Integer.valueOf(page));
	// jsonObj.put("total", Integer.valueOf(total));
	// jsonObj.put("records", Integer.valueOf(count));
	// return jsonObj;
	// }
	//
	// private static JSONArray getCellAsObject(RsIterator rs) {
	// int i = 0;
	// JSONArray cellArray = new JSONArray();
	// JSONObject cellObj;
	// String[] headers = rs.getColumnNames();
	//
	// for (Object[] row : rs.getRows()) {
	// JSONObject ob = new JSONObject();
	// ob.put("id", Integer.valueOf(++i));
	// for (int j = 0; j < headers.length; j++) {
	// ob.put(headers[j], row[j]);
	// }
	// cellArray.add(ob);
	// }
	// return cellArray;
	// }

}
