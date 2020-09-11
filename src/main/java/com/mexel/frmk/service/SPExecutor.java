package com.mexel.frmk.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class SPExecutor extends StoredProcedure {

	public SPExecutor(JdbcTemplate invoker, String spName,
			SqlParameter... paramTypes) {
		super(invoker, spName);
		if (paramTypes != null) {
			for (SqlParameter p : paramTypes) {
				declareParameter(p);
			}
		}
	}

}
