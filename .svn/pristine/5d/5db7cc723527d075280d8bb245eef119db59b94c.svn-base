package com.mexel.frmk.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SQLQueryInfo {
	
	private static final Pattern pattern = Pattern
			.compile("\\$\\{([\\w-]+)\\}");

	private static final SQLGenerator generator = new SQLGenerator();
	
	private final String[] paramNames;
	private final String sql;
	private final String mapper;
	private String orignalSql;
	private boolean dynamic=true;

	public SQLQueryInfo(String sql, String[] paramsNames,String mapper) {
		this.paramNames = paramsNames;
		this.sql = sql.toLowerCase();
		this.mapper = mapper;
	}

	public String[] getParamNames() {
		return paramNames;
	}


	public String getSql() {
		return sql;
	}

	public String getMapper() {
		return mapper;
	}

	public String getOrignalSql() {
		return orignalSql;
	}

	public void setOrignalSql(String orignalSql) {
		this.orignalSql = orignalSql;
	}
	public static SQLQueryInfo create(String sql, String mapper, boolean parse, Map<String,Object> params){
		if(!parse){
			SQLQueryInfo q = new SQLQueryInfo(sql, null,mapper);
			q.setOrignalSql(sql);
			return q;
		}
		List<String> paramsNames = new ArrayList<String>();

		int beginOffset = 0;
		Matcher matcher = pattern.matcher(sql);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String paramName = matcher.group(1);
			boolean coll = false;
			
			String append = "?";
			if(params != null){
				Object param = params.get(paramName);
				if(param instanceof Collection){
					coll = true;
					append =  buildCollectionParams(paramName, paramsNames, (Collection)param, params);
				}
			}
			if(!coll){
				paramsNames.add(paramName);
			}
			sb.append(sql.substring(beginOffset, matcher.start())).append(append);
			beginOffset = matcher.end();
		}
		if (beginOffset < sql.length()) {
			sb.append(sql.substring(beginOffset));
		}
		SQLQueryInfo q = new SQLQueryInfo(sb.toString(), paramsNames
				.toArray(new String[paramsNames.size()]),mapper);
		q.setOrignalSql(sql);
		return q;
		
	}
	
	private static String buildCollectionParams(String paramName,List<String> paramsNames,  Collection coll, Map<String,Object> params){
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(Object ob:coll){
			 String pName = paramName+"_"+index++;	
			 paramsNames.add(pName);
			 params.put(pName, ob);
			 sb.append( sb.length() > 0 ? ", ?":"?"); 
		}
		return sb.toString();
	}
	
	public static SQLQueryInfo create(String sql, String mapper){
		return create(sql, mapper,false,null);
	}
	
	public SQLQueryInfo build(Map<String,Object> params){
		if(isDynamic()){
			String sql = generator.buildSql(this.orignalSql, params);
			return create(sql, getMapper(),true,params);
		}
		return this;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}
}
