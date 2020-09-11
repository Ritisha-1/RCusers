package com.mexel.frmk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mexel.frmk.db.mapper.BasicDbMapper;
import com.mexel.frmk.db.mapper.DbMapper;


public class DataBaseServiceThreadLocal extends BasicService {

	private TransactionTemplate txTemplate;
	private TransactionTemplate txTemplateNew;
	// private static String LAST_INSERT_ID =

	private static final ThreadLocal<Stack<JdbcTemplate>> local = new ThreadLocal<Stack<JdbcTemplate>>();

	private DataSourceTransactionManager txManager;
	
	@Autowired
	DataBrokerService dataBroker;

	
	public DataSourceTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(DataSourceTransactionManager txManager) {
		this.txManager = txManager;
		this.txTemplate = new TransactionTemplate(txManager);
		this.txTemplate
				.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		this.txTemplateNew = new TransactionTemplate(txManager);
		this.txTemplateNew
				.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}

	public <T> T executeInNewTransaction(TransactionCallback<T> executor)
			throws ServiceException {
		try {
			setThreadLocalJdbcTemplate(getNewJdbcTemplate());
			return txTemplateNew.execute(executor);
		} catch (DataAccessException dae) {
			throw new ServiceException("New Transaction Failed for "
					+ executor.getClass().getName() + ".", dae);
		} catch (TransactionException trxEx) {
			throw new ServiceException(
					"New Transaction Failed for new transaction for "
							+ executor.getClass().getName() + ".", trxEx);
		} finally {
			removeThreadLocalCurrentJdbcTemplate();
		}
	}

	public <T> T executeInCurrentTransaction(
			final TransactionCallback<T> trxContext) throws ServiceException {
		try {
			return txTemplate.execute(trxContext);
		} catch (DataAccessException dae) {
			throw new ServiceException("Current Transaction failed "
					+ trxContext.getClass().getName() + ".", dae);
		} catch (TransactionException trxEx) {
			throw new ServiceException("Current Transaction failed "
					+ trxContext.getClass().getName() + ".", trxEx);
		}
	}

	public void executeSP(String spName, Map<String, Object> params,
			SqlParameter... paramTypes) throws ServiceException {
		JdbcTemplate jdbcInvoker = getThreadLocalJdbcTemplate();

		StoredProcedure sp = new SPExecutor(jdbcInvoker, spName, paramTypes);
		LogFactory.getLog(getClass()).debug("Executing SP " + spName);
		sp.execute(params);

	}

	protected ThreadLocal<Stack<JdbcTemplate>> getLocal() {
		return local;
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
						.getSql(),Statement.RETURN_GENERATED_KEYS);
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

		return jdbcInvoker.query(queryInfo.getSql(), psSetter,
				new ResultSetExtractor<Collection<T>>() {

					@Override
					public Collection<T> extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						return mapper.map(rs);
					}

				});
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
		if (param == null || param instanceof String) {
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

		else {
			throw new ServiceFatalException("Unhandled data type "
					+ param.getClass().getName());
		}
		// handle all other possible type
	}

	public <T> T executeInGlobalTransaction(
			final TransactionCallback<T> trxCallBack) throws ServiceException {
		if (getLocal().get() != null && !getLocal().get().isEmpty()) {
			throw new ServiceFatalException(
					"already in a transaction context can not start global transaction");
		}

		try {
			setThreadLocalJdbcTemplate(getNewJdbcTemplate());
			return txTemplate.execute(trxCallBack);
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			throw new ServiceException("global transaction Failed for "
					+ trxCallBack.getClass().getName(), dae);
		} catch (TransactionException trxEx) {
			throw new ServiceException("global transaction Failed for  "
					+ trxCallBack.getClass().getName(), trxEx);
		} finally {
			getLocal().set(null);
		}
	}

	protected final JdbcTemplate getThreadLocalJdbcTemplate()
			throws ServiceException {
		Stack<JdbcTemplate> trxContext = getLocal().get();
		if (trxContext == null || trxContext.isEmpty()) {
			return getNewJdbcTemplate();
			// setThreadLocalJdbcTemplate(getNewJdbcTemplate());
			// throw new ServiceException("No JdbcTemplate in ThreadLocal");
		}
		return trxContext.peek();
	}

	protected final void setThreadLocalJdbcTemplate(JdbcTemplate template)
			throws ServiceException {
		Stack<JdbcTemplate> trxContext = getLocal().get();
		if (trxContext == null) {
			trxContext = new Stack<JdbcTemplate>();
			getLocal().set(trxContext);
		}
		trxContext.add(template);
	}

	protected final void removeThreadLocalCurrentJdbcTemplate()
			throws ServiceException {
		Stack<JdbcTemplate> trxContext = getLocal().get();
		if (trxContext == null || trxContext.isEmpty()) {
			throw new ServiceException("No JdbcTemplate in ThreadLocal");
		}
		trxContext.pop();
	}

	public DataBrokerService getDataBroker() {
		return dataBroker;
	}

	public void setDataBroker(DataBrokerService dataBroker) {
		this.dataBroker = dataBroker;
	}
}
