package com.mexel.frmk.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

public class DataBrokerService extends BasicService {


	public final Map<String, SQLQueryInfo> sqlService = new HashMap<String, SQLQueryInfo>();

	public static Log  LOG = LogFactory.getLog(DataBaseService.class.getName());
	
	private String[] sqlFiles;

	public void register(String serviceName, String sql,String mapperClass) {
		if(sqlService.containsKey(serviceName)){
			throw new ServiceException("Duplicate Service "+serviceName);
		}try{
			LogFactory.getLog(getClass()).debug("Service ="+serviceName);
			sqlService.put(serviceName.toLowerCase(), parse(sql,mapperClass));	
		}catch(Throwable t){
			LOG.error("Error registering service "+serviceName);
			throw new ServiceException("Error registering service ", t);
		}
		
	}

	public void register(String serviceName, String sql) {
		sqlService.put(serviceName, parse(sql,""));
	}
	
	public SQLQueryInfo getSQL(String sql,Map<String, Object> params) {
		SQLQueryInfo q = sqlService.get(sql.toLowerCase());
		if(q ==null){
			throw new ServiceException("No Service found for "+sql);
		}
		params.put("SERVICE_NAME", sql);
		return q.build(params); 

	}

	private SQLQueryInfo parse(String sql,String mapper) {
		return SQLQueryInfo.create(sql, mapper);
	}

	@Override
	protected void init() throws Exception {
		SqlReader reader= new SqlReader();
		InputStream[] streams = getSQLFiles();
		for(InputStream in:streams){
			LogFactory.getLog(getClass()).debug("Reading sql "+in);
			reader.read(in, this);
			in.close();
		}
	}
	
	protected InputStream[] getSQLFiles(){
		String[] sqls = getSqlFiles();
		InputStream[] streams = new InputStream[sqls.length];
		for(int i=0;i<sqls.length;i++){
			ClassPathResource res = new ClassPathResource(sqls[i]);
			try {
				streams[i] = res.getInputStream();
			} catch (IOException e) {
				throw new ServiceException("Error reading config file", e);
			}
			LogFactory.getLog(getClass()).debug("Reading sql Files "+ sqls[i] +"  "+streams[i]);
		}
		return streams;
	}

	public String[] getSqlFiles() {
		return sqlFiles;
	}

	public void setSqlFiles(String[] sqlFiles) {
		LogFactory.getLog(getClass()).debug("seting sql Files "+ sqlFiles[0]+ " seting sql Files" + sqlFiles[1] +"  seting sql Files "+ sqlFiles[2]);
		this.sqlFiles = sqlFiles;
	}

	public Map<String, SQLQueryInfo> getSqlService() {
		return sqlService;
	}

}
