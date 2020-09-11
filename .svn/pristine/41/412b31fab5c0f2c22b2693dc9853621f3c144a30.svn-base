package com.mexel.frmk.db;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mexel.frmk.db.mapper.DbMapper;
import com.mexel.frmk.model.IBasicBean;
import com.mexel.frmk.service.BasicService;
import com.mexel.frmk.util.CommonUtils;


public abstract class DbInvoker extends BasicService {

	
	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	public static final String DEFAULT_TIME_FORMAT = "HHmm";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyyMMddHHmm";

	public int executeDelete(String service,  Map<String,Object> params) {
		return getDataBaseService().executeDelete(service,params);
	}

	
	/**
	 * Insert or update a bean in database.
	 * Please ensure bean must have following 3 method correctly implemented
	 * getInsertSQLName
	 * getUpdateSQlName
	 * getSequeneceName
	 * 
	 * and also ensure sequence exists in database with given name
	 * */

	protected void insertUpdateBean(IBasicBean bean) {
		if (bean.getId() == null || bean.getId().intValue()==0) {
			insertBean(bean);
			
		} else {
			updateBean(bean);
		}
	}

	protected void insertBean(IBasicBean bean) {
		Map<String, Object> params = new HashMap<String, Object>();
		bean.dbBinding(params);
		long id =  executeInsert(bean.getInsertQryName(),
				params);
		bean.setId(id);
	}
	

	protected void updateBean(IBasicBean bean) {
		Map<String, Object> params = new HashMap<String, Object>();

		bean.dbBinding(params);
		executeUpdate(bean.getUpdateQryName(),
					params);
	}

	public void executeSQL(String sql) {
		Map<String,Object> params = new HashMap<String,Object>();
		getDataBaseService().executeUpdate(sql, params);
	}

	
	public long executeInsert(String sql, Map<String,Object> params) {
		return getDataBaseService().executeInsert(sql, params);
	}
	
	public int executeUpdate(String sql, Map<String,Object> params) {
		return getDataBaseService().executeUpdate(sql, params);
	}


	public <T> Collection<T> executeSelect(String sql,
			Map<String, Object> params, DbMapper<T> mapper) {
		return getDataBaseService().executeSQL(sql, params, mapper);
	}

	public <T> T executeSelectOne(String sql, Map<String, Object> params,
			DbMapper<T> mapper) {
		Collection<T> result = executeSelect(sql, params, mapper);
		if (!result.isEmpty()) {
			return result.iterator().next();
		}
		return null;
	}

	public static  void addStringParameter(Map<String, Object> params, String key,
			String value) {
		if (!StringUtils.isEmpty(value)) {
			params.put(key, value);
		}
	}

	public static  void addWildCardParameter(Map<String, Object> params, String key,
			String value) {
		if (!StringUtils.isEmpty(value)) {
			params.put(key, value.toUpperCase()+"%");
		}
	}

	public static  void addFullWildCardParameter(Map<String, Object> params, String key,
			String value) {
		if (!StringUtils.isEmpty(value)) {
			params.put(key, "%"+value.toUpperCase()+"%");
		}
	}

	public static void addNumberParameter(Map<String, Object> params, String key,
			Number value) {
		if (value != null) {
			params.put(key, value);
		}
		else{
//			params.put(key, new NULLNumber());
		}
	}
	
	public static final class NULLNumber{};
	
	public static void addIdParameter(Map<String, Object> params, String key,
			Long value) {
		if (value != null && value >0) {
			params.put(key, value);
		}
		else{
//			params.put(key, new NULLNumber());
		}
	}

	public static  void addBooleanParameter(Map<String, Object> params, String key,
			Boolean value) {
		if (value != null) {
			params.put(key, value?1:0);
		}
		else{
//			params.put(key, new NULLNumber());
		}

	}

	public static  void addDateParameter(Map<String, Object> params, String key,
			Date value) {
		if (value != null) {
			params.put(key, CommonUtils.format(value));
		}
	}

	public static  void addCollectionParameter(Map<String, Object> params, String key,
			Collection<?> coll) {
		if (coll != null && !coll.isEmpty()) {
			params.put(key, coll);
		}
	}

	public static  void addCollectionParameter(Map<String, Object> params, String key,	Object... objects) {
		if (objects != null && objects.length>0) {
			params.put(key, Arrays.asList(objects));
		}
	}

	public static  void addTimeParamter(Map<String, Object> params, String key,
			Date value) {
		if (value != null) {
			params.put(key, CommonUtils.formatTime(value));
		}
	}

}
