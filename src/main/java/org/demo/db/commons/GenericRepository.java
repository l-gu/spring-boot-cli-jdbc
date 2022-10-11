package org.demo.db.commons;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public abstract class GenericRepository<T> {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final RowMapper<T> rowMapper ;
	
	/**
	 * Constructor
	 * @param rowMapper
	 */
	public GenericRepository(RowMapper<T> rowMapper) {
		super();
		this.rowMapper = rowMapper;
		if ( rowMapper == null ) {
		    throw new IllegalArgumentException ("RowMapper is null");
		}
	}
	//----------------------------------------------------------------------------
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	protected JdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate.getJdbcTemplate();
	}

	protected DataSource getDataSource() { 
		// each JdbcTemplate holds a DataSource
		// return namedParameterJdbcTemplate.getDataSource();
		return namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
	}
	
	protected RowMapper<T> getRowMapper() {
		return this.rowMapper;
	}

	//----------------------------------------------------------------------------
	
	protected SqlParameterSource getEmptySqlParameterSource() {
		return new MapSqlParameterSource();
	}
	
	protected T uniqueOrNull (List<T> list) { 
		if (list.isEmpty()) {
		    // list is empty => not found 
		    return null; 
		} else if (list.size() == 1) { 
			// list contains exactly 1 element => found
		    return list.get(0); 
		} else { 
			// list contains more than 1 element
		    throw new IllegalStateException("More than one record found (only one expected)");
		}		
	}

	//----------------------------------------------------------------------------

	protected static LocalDate toLocalDate(java.sql.Date sqlDate) { 
		if ( sqlDate != null ) {
			return sqlDate.toLocalDate();
		}
		return null ;
	}

	//----------------------------------------------------------------------------
	
	protected List<T> sqlSelectList(String sql) {
		return namedParameterJdbcTemplate.query(sql, getRowMapper() );
	}

	protected List<T> sqlSelectList(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.query(sql, sqlParameterSource, getRowMapper() );
	}


	protected T sqlSelectOne(String sql, SqlParameterSource sqlParameterSource) {
		return uniqueOrNull( sqlSelectList(sql, sqlParameterSource) );
	}
	
	protected long sqlCount(String sql) {
		return namedParameterJdbcTemplate.queryForObject(sql, getEmptySqlParameterSource(), Long.class);
	}
	
	protected int sqlInsert(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}
	
	protected int[] sqlInsertBatch(String sql, SqlParameterSource[] sqlParameterSources) {
		return namedParameterJdbcTemplate.batchUpdate(sql, sqlParameterSources);		
	}
	
	protected int sqlUpdate(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}

	protected int[] sqlUpdateBatch(String sql, SqlParameterSource[] sqlParameterSources) {
		return namedParameterJdbcTemplate.batchUpdate(sql, sqlParameterSources);		
	}
	
	protected int sqlDelete(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}

	protected void sqlExecute(String sql) {
		namedParameterJdbcTemplate.getJdbcTemplate().execute(sql);
	}

}
