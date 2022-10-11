package org.demo.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.demo.db.EmployeeRepository;
import org.demo.db.commons.GenericRepository;
import org.demo.domain.model.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl extends GenericRepository<Employee> implements EmployeeRepository {

	/**
	 * Specific RowMapper for Employee <br>
	 * Creates an instance of Employee for each row in the given ResultSet <br>
	 * and map column values to attributes.
	 */
	private static final class EmployeeRowMapper implements RowMapper<Employee> {
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
	
	// Just to test different RowMapper types  
	private static final boolean specificRowMapper = true; 
	
	private static final RowMapper<Employee> buildRowMapper() {
		if ( specificRowMapper ) {
			// Specific RowMapper 
			return new EmployeeRowMapper() ;
		}
		else {
			// Generic RowMapper 
			// Column values are mapped based on matching the column name as obtained from result set
			// meta-data to public setters for the corresponding properties. The names are matched either
			// directly or by transforming a name separating the parts with underscores to the same name
			// using "camel case".
			return new BeanPropertyRowMapper<Employee>(Employee.class);
		}
	}
	
	/**
	 * Constructor
	 */
	public EmployeeRepositoryImpl() {
		super(buildRowMapper());
	}

	/**
	 * Returns all SQL parameters for the given object
	 * @param record
	 * @return
	 */
	private SqlParameterSource getRecordParameters(Employee record) {
		return new MapSqlParameterSource()
				.addValue("id", record.getId() )
				.addValue("firstName", record.getFirstName() )
				.addValue("lastName", record.getLastName() )
				.addValue("birthDate", record.getBirthDate() )
				;
	}
	private SqlParameterSource[] getBatchParameters(List<Employee> records) {
		SqlParameterSource[] batchParameters = new SqlParameterSource[records.size()];
		for (int i = 0; i < batchParameters.length; i++) {
			batchParameters[i] = getRecordParameters(records.get(i));
		}
		return batchParameters;
	}
	
	/**
	 * Returns Primary Key SQL parameter(s) from the given values
	 * @param id
	 * @return
	 */
	private SqlParameterSource getPKParameters(Long id) {
		return new MapSqlParameterSource()
				.addValue("id", id )
				;
	}

	//------------------------------------------------------------------------------

	@Override
	public void createTable() {
		String sql = "create table employee ("
				+ " id int ,"
				+ " first_name varchar(30),"
				+ " last_name  varchar(30),"
				+ " birth_date date"
				+ ")";
		sqlExecute(sql);
	}

	@Override
	public long countAll() {
		String sql = "select count(1) from EMPLOYEE";
		return sqlCount(sql);
	}

	/***
	//@Override
	public Employee findByIdNative(Long id) {
		
		// SQL request used by jdbcTemplate to create a prepared statement
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		
		// Do not use "queryForObject" in this case
		// queryForObject all such methods expects that executed query will return one and only one row
		// If you get no rows or more than one row that will result in "IncorrectResultSizeDataAccessException"
		// return jdbcTemplate.queryForObject(sql, rowMapper, id);

		// Execute SQL query, return a list of Employee objects created with "row mapper"
		// get unique record in the list returned
		return uniqueOrNull(getJdbcTemplate().query(sql, getRowMapper(), id));
	}
	***/

	@Override
	public Employee findById(Long id) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id";
		// Execute SQL query and return a single Employee object created with the "RowMapper"
		// or null if not found
		return sqlSelectOne(sql, getPKParameters(id));
	}

	@Override
	public List<Employee> findAll() {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter if any)
		String sql = "SELECT * FROM EMPLOYEE ";
		// Execute SQL query and return a list of Employee objects created with the "RowMapper"
		return sqlSelectList(sql);
	}

	// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
	private static final String SQL_INSERT = 
			"insert into employee  (id, first_name, last_name, birth_date) " +
			" values (:id, :firstName, :lastName, :birthDate)";
	
	@Override
	public int insert(Employee record) {
		return sqlInsert(SQL_INSERT, getRecordParameters(record));
	}
	
	@Override
	public int[] insertBatch(List<Employee> records) {
		return sqlInsertBatch(SQL_INSERT, getBatchParameters(records));
	}
	
	// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
	private static final String SQL_UPDATE = 
			"update employee set " +
			" first_name = :firstName, last_name = :lastName, birth_date = :birthDate " +
			" where id = :id " ; 

	@Override
	public int update(Employee record) {
		return sqlUpdate(SQL_UPDATE, getRecordParameters(record));
	}

	@Override
	public int[] updateBatch(List<Employee> records) {
		return sqlInsertBatch(SQL_UPDATE, getBatchParameters(records));
	}

	@Override
	public int deleteById(Long id) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "delete from employee where id = :id";
		return sqlDelete(sql, getPKParameters(id));
	}

}
