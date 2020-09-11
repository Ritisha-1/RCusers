package com.mexel.frmk.db.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mexel.frmk.util.CommonUtils;


public class JSONMapper extends DbMapper<JSONArray> {

	@Override
	protected Collection<JSONArray> doMap(ResultSet rs) throws SQLException{
		JSONArray array = new JSONArray(); 
		String[] headers = null;
		while (rs.next()) {
			if (headers == null) {
				ResultSetMetaData metaData = rs.getMetaData();
				headers = new String[metaData.getColumnCount()];
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					headers[i] = metaData.getColumnLabel(i + 1);
				}
			}
			
			JSONObject ob = new JSONObject();
			
			for (int i = 0; i < headers.length; i++) {
				String str = rs.getString(i + 1);
				try {
					ob.put(headers[i], CommonUtils.emptyIfNull(str));
					} catch (JSONException e) {
						throw new SQLException(e);
					}
			}
			array.put(ob);
		}

		return Collections.singletonList(array);
	}

} 
