package org.demo.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.demo.db.EmployeeRepository;
import org.demo.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

//	private JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}

	@Autowired
	private JdbcTemplate jdbcTemplate; // with a single datasource can be autowired
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();
	// private final RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>();
	
	public DataSource getDataSource() {
		// each JdbcTemplate holds a DataSource
		return jdbcTemplate.getDataSource();
	}

	@Override
	public void createTable() {
		String sql = "create table employee ("
				+ " id int ,"
				+ " first_name varchar(30),"
				+ " last_name  varchar(30),"
				+ " birth_date date"
				+ ")";
		jdbcTemplate.execute(sql);
	}

	@Override
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(1) from EMPLOYEE", Integer.class);
	}

	protected LocalDate toLocalDate(java.sql.Date sqlDate) { // TODO : move in generic class
		if ( sqlDate != null ) {
			return sqlDate.toLocalDate();
		}
		return null ;
	}

	protected <T> T uniqueRecordOrNull (List<T> list) { // TODO : move in generic class
		if (list.isEmpty()) {
		    // list is empty => not found 
		    return null; 
		} else if (list.size() == 1) { 
			// list contains exactly 1 element => found
		    return list.get(0); 
		} else { 
			// list contains more than 1 element
		    throw new IllegalStateException("More than one record found");
		}		
	}
	
	@Override
	public Employee findById(Long id) {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		// Query given SQL to create a prepared statement from SQL
		// and a list of arguments to bind to the query,
		// mapping a single result row to a result object via a RowMapper.
		
		// Do not use "queryForObject" in this case
		// queryForObject all such methods expects that executed query will return one and only one row
		// If you get no rows or more than one row that will result in "IncorrectResultSizeDataAccessException"
		// return jdbcTemplate.queryForObject(sql, rowMapper, id);
		/**
		List<Employee> list = jdbcTemplate.query(sql, employeeRowMapper, id);
		if (list.isEmpty()) {
		    return null; // list is empty => not found 
		} else if (list.size() == 1) { // list contains exactly 1 element
		    return list.get(0); // list contains exactly 1 element => found
		} else { // list contains more than 1 element
		    throw new IllegalStateException("More than one record found");
		}	
		**/	
		return uniqueRecordOrNull(jdbcTemplate.query(sql, employeeRowMapper, id));
	}

	@Override
	public Employee findByIdWithParamMap(Long id) {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id";
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("id", id);
		// Query given SQL to create a prepared statement from SQL
		// and a list of arguments to bind to the query,
		// mapping a single result row to a result object via a RowMapper.
		return uniqueRecordOrNull(namedParameterJdbcTemplate.query(sql, parameters, employeeRowMapper));
	}

	private final class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee e = new Employee();
			e.setId(rs.getLong("id"));
			e.setFirstName(rs.getString("first_name"));
			e.setLastName(rs.getString("last_name"));
			e.setBirthDate(toLocalDate(rs.getDate("birth_date")));
			return e;
		}
	}
}
