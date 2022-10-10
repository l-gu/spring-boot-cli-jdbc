package org.demo.db.commons;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public abstract class GenericRepository<T> {

	private static final SqlParameterSource emptySqlParameterSource = new MapSqlParameterSource();

	protected SqlParameterSource getEmptySqlParameterSource() {
		return emptySqlParameterSource;
	}
	
//	protected DataSource getDataSource() { 
//		// each JdbcTemplate holds a DataSource
//		// return namedParameterJdbcTemplate.getDataSource();
//		return namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
//	}

	protected LocalDate toLocalDate(java.sql.Date sqlDate) { 
		if ( sqlDate != null ) {
			return sqlDate.toLocalDate();
		}
		return null ;
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

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	protected abstract RowMapper<T> getRowMapper();

	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	protected JdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate.getJdbcTemplate();
	}
	
	protected T sqlSelectByPK(String sql, SqlParameterSource sqlParameterSource) {
		return uniqueOrNull(namedParameterJdbcTemplate.query(sql, sqlParameterSource, getRowMapper() ));
	}
	
	protected long sqlCount(String sql) {
		return namedParameterJdbcTemplate.queryForObject(sql, getEmptySqlParameterSource(), Long.class);
	}
	
	protected void sqlInsert(String sql, SqlParameterSource sqlParameterSource) {
		namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}
	
	protected int sqlUpdate(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}
	
	protected int sqlDelete(String sql, SqlParameterSource sqlParameterSource) {
		return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
	}

}
