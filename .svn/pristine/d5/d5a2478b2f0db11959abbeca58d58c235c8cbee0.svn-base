package com.mexel.frmk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mexel.frmk.exception.FunctionalException;

public class BatchInsertUpdate {
	private final String sqlName;
	private final DataBaseService dbService;
	private final int batchSize;
	private final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private final List<Integer> affectedRows = new ArrayList<Integer>();

	public BatchInsertUpdate(DataBaseService dbService, String sqlName,
			int batchSize) {
		this.dbService = dbService;
		this.sqlName = sqlName;
		this.batchSize = batchSize;

	}

	public void addToBatch(Map<String, Object> params) throws FunctionalException {
		data.add(new HashMap<String, Object>(params));
		if (data.size() >= batchSize) {
			flush();
		}
	}

	public void flush() throws FunctionalException {
		if (data.size() > 0) {
			int[] rows = dbService.executeBatchInsertUpdate(sqlName, data);
			for (int i = 0; i < rows.length; i++) {
				affectedRows.add(rows[i]);
			}
		}
		data.clear();
	}

}
