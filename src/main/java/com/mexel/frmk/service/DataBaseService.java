package com.mexel.frmk.service;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mexel.frmk.db.DbInvoker.NULLNumber;
import com.mexel.frmk.db.mapper.BasicDbMapper;
import com.mexel.frmk.db.mapper.DbMapper;


public class DataBaseService extends BasicService {

	private DataSourceTransactionManager txManager;
	
	@Autowired
	DataBrokerService dataBroker;

	public DataSourceTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(DataSourceTransactionManager txManager) {
		this.txManager = txManager;
	}

	public void executeSP(String spName, Map<String, Object> params,
			SqlParameter... paramTypes) throws ServiceException {
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		StoredProcedure sp = new SPExecutor(jdbcInvoker, spName, paramTypes);
		LogFactory.getLog(getClass()).debug("Executing SP " + spName);
		sp.execute(params);

	}

	protected final JdbcTemplate getNewJdbcTemplate() {
		return new JdbcTemplate(getTxManager().getDataSource());
	}

	
	public int executeDelete(final String sql,
			final Map<String, Object> params) throws ServiceException {
		return executeUpdateSQL(sql, params);
	}

	public int executeUpdate(final String sql,
			final Map<String, Object> params) throws ServiceException {
		return executeUpdateSQL(sql, params);
	}


	/**
	 * must be executed in current transaction
	 * */
	public int executeUpdateSQL(final String sql,
			final Map<String, Object> params) throws ServiceException {
		final SQLQueryInfo queryInfo = dataBroker.getSQL(sql,params);

		PreparedStatementSetter psSetter = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				bindParameter(ps, queryInfo, params);
			}
		};
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();
		return jdbcInvoker.update(queryInfo.getSql(), psSetter);
	}

	public long executeInsert(final String sql,
			final Map<String, Object> params) throws ServiceException {

		final SQLQueryInfo queryInfo = dataBroker.getSQL(sql,params);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		int row = jdbcInvoker.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(queryInfo
						.getSql(),new String [] {"_id"});
				bindParameter(ps, queryInfo, params);
				return ps;
			}
		}, keyHolder);

		return row >0 ? keyHolder.getKey().longValue():null;
	}

	/**
	 * must be executed in current transaction
	 * */
	public <T> List<T> executeSelect(final String sql,
			final Map<String, Object> params, RowMapper<T> mapper)
			throws ServiceException {
		
		final SQLQueryInfo queryInfo = dataBroker.getSQL(sql,params);

		PreparedStatementSetter psSetter = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				bindParameter(ps, queryInfo, params);
			}
		};
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();
		return jdbcInvoker.query(queryInfo.getSql(), psSetter, mapper);
	}

	public <T> T executeSelect(final String sql,
			final Map<String, Object> params, BasicDbMapper<T> mapper)
			throws ServiceException {

		final SQLQueryInfo queryInfo = dataBroker.getSQL(sql,params);

		PreparedStatementSetter psSetter = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				bindParameter(ps, queryInfo, params);
			}
		};
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		return jdbcInvoker.query(queryInfo.getSql(), psSetter, mapper);
	}


	public <T> Collection<T> executeSQL(final String sql,
			final Map<String, Object> params, final DbMapper<T> mapper)
			throws ServiceException {

		final SQLQueryInfo queryInfo = dataBroker.getSQL(sql,params);

		PreparedStatementSetter psSetter = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				bindParameter(ps, queryInfo, params);
			}
		};
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();
		final long startTime = System.currentTimeMillis();
		

		Collection<T> result =  jdbcInvoker.query(queryInfo.getSql(), psSetter,
				new ResultSetExtractor<Collection<T>>() {

					@Override
					public Collection<T> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						return mapper.map(rs);
					}

				});
		long endTime = System.currentTimeMillis();
		if( endTime > startTime+2000){
			LogFactory.getLog(getClass()).error("Please optimize sql >>" + sql +" Took "+(endTime-startTime)/1000 +" Seconds with param["+params+"]");
		}
		return result;
	}
	

	public <T> Collection<T> executeSQL(final String sql, final Object[] args,
			final DbMapper<T> mapper) throws ServiceException {
		
		final SQLQueryInfo sqlInfo = dataBroker.getSQL(sql, new HashMap<String,Object>());
		
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		if (args.length > 0) {
			return jdbcInvoker.query(sqlInfo.getSql(), args,
					new ResultSetExtractor<Collection<T>>() {

						@Override
						public Collection<T> extractData(ResultSet rs)
								throws SQLException, DataAccessException {

							return mapper.map(rs);
						}

					});
		} else {
			return jdbcInvoker.query(sqlInfo.getSql(),
					new ResultSetExtractor<Collection<T>>() {

						@Override
						public Collection<T> extractData(ResultSet rs)
								throws SQLException, DataAccessException {

							return mapper.map(rs);
						}

					});

		}
	}

	public ResultSet executeSQL(final String sql, final String[] args)
			throws ServiceException {

		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		return jdbcInvoker.query(sql, args,
				new ResultSetExtractor<ResultSet>() {

					@Override
					public ResultSet extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						return rs;
					}

				});
	}

	/**
	 * must be executed in current transaction
	 * */

	public int[] executeBatchInsertUpdate(final String serviceName,
			final List<Map<String, Object>> data) throws ServiceException {
		final SQLQueryInfo queryInfo = dataBroker.getSQL(serviceName, new HashMap<String,Object>());

		BatchPreparedStatementSetter batch = new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int index)
					throws SQLException {
				bindParameter(ps, queryInfo, data.get(index));
			}

			@Override
			public int getBatchSize() {
				return data.size();
			}
		};
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();
		return jdbcInvoker.batchUpdate(queryInfo.getSql(), batch);

	}

	protected void bindParameter(PreparedStatement ps, SQLQueryInfo query,
			Map<String, Object> params) throws SQLException {
		int i = 0;
		for (String s : query.getParamNames()) {
			setParameter(ps, ++i, params.get(s));
		}
	}

	protected void setParameter(PreparedStatement ps, int index, Object param)
			throws SQLException {
		if (param instanceof NULLNumber) {
			ps.setNull(index, Types.NUMERIC);
		}
		else if (param == null || param instanceof String) {
			ps.setString(index, (String) param);
		} else if (param instanceof Integer) {
			ps.setInt(index, (Integer) param);
		} else if (param instanceof Long) {
			ps.setLong(index, (Long) param);
		} else if (param instanceof Boolean) {
			ps.setBoolean(index, (Boolean) param);
		} else if (param instanceof Double) {
			ps.setDouble(index, (Double) param);
		} else if (param instanceof Float) {
			ps.setFloat(index, (Float) param);
		}
		else if (param instanceof Date) {
			java.sql.Date d = new java.sql.Date(((Date) param).getTime());
			ps.setDate(index, d);
		}
		else if(param instanceof Blob){
			ps.setBlob(index, (Blob)param);
		}

		else {
			throw new ServiceFatalException("Unhandled data type "
					+ param.getClass().getName());
		}
		// handle all other possible type
	}

	protected final JdbcTemplate getThreadLocalJdbcTemplate()
			throws ServiceException {
		return getNewJdbcTemplate();
	}	

	public DataBrokerService getDataBroker() {
		return dataBroker;
	}

	public void setDataBroker(DataBrokerService dataBroker) {
		this.dataBroker = dataBroker;
	}
}
