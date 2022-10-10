package org.demo.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.demo.db.EmployeeRepository;
import org.demo.db.commons.GenericRepository;
import org.demo.domain.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl extends GenericRepository<Employee> implements EmployeeRepository {

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
	
//	private final RowMapper<Employee> employeeRowMapper = new EmployeeRowMapper();
//	// private final RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>();

//	@Override
//	protected RowMapper<Employee> getRowMapper() {
//		return employeeRowMapper;
//	}
//	

	public EmployeeRepositoryImpl() {
		super(new EmployeeRowMapper());
	}


	/**
	 * Returns all SQL parameters for the given object
	 * @param record
	 * @return
	 */
	private SqlParameterSource getAllParameters(Employee record) {
		return new MapSqlParameterSource()
				.addValue("id", record.getId() )
				.addValue("firstName", record.getFirstName() )
				.addValue("lastName", record.getLastName() )
				.addValue("birthDate", record.getBirthDate() )
				;
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
		//return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(sql, Long.class);
		//return namedParameterJdbcTemplate.queryForObject(sql, getEmptySqlParameterSource(), Long.class);
		return sqlCount(sql);
	}

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

	@Override
	public Employee findById(Long id) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id";
		
		// Execute SQL query, return a list of Employee objects created with "row mapper"
		// get unique record in the list returned
		//return uniqueOrNull(namedParameterJdbcTemplate.query(sql, getPKParameters(id), employeeRowMapper));
		return sqlSelectOne(sql, getPKParameters(id));
	}

	@Override
	public List<Employee> findAll() {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "SELECT * FROM EMPLOYEE ";
		
		// Execute SQL query, return a list of Employee objects created with "row mapper"
		// get unique record in the list returned
		//return uniqueOrNull(namedParameterJdbcTemplate.query(sql, getPKParameters(id), employeeRowMapper));
		return sqlSelectList(sql);
	}

	@Override
	public void insert(Employee record) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "insert into employee  (id, first_name, last_name, birth_date) " 
				+ " values (:id, :firstName, :lastName, :birthDate)";
		//namedParameterJdbcTemplate.update(sql, getAllParameters(record));
		sqlInsert(sql, getAllParameters(record));
	}

	@Override
	public int update(Employee record) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "update employee set " +
				" firstName = :firstName, lastName = :latsName, birthDate = :birthDate " +
				" where id = :id " ; 
		//return namedParameterJdbcTemplate.update(sql, getAllParameters(record));
		return sqlUpdate(sql, getAllParameters(record));
	}
	
	@Override
	public int deleteById(Long id) {
		String sql = "delete from employee where id = :id";
		//return namedParameterJdbcTemplate.update(sql, getPKParameters(id));
		return sqlDelete(sql, getPKParameters(id));
	}

}
