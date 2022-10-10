package org.demo.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.demo.db.EmployeeRepository;
import org.demo.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	protected DataSource getDataSource() { // TODO : move in generic class
		// each JdbcTemplate holds a DataSource
		// return namedParameterJdbcTemplate.getDataSource();
		return namedParameterJdbcTemplate.getJdbcTemplate().getDataSource();
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
		    throw new IllegalStateException("More than one record found (only one expected)");
		}		
	}

	private static final SqlParameterSource emptySqlParameterSource = new MapSqlParameterSource();
	protected SqlParameterSource getEmptySqlParameterSource() {
		return emptySqlParameterSource;
	}
	
	//------------------------------------------------------------------------------

//	private JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//	}

//	@Autowired
//	private JdbcTemplate jdbcTemplate; // with a single datasource can be autowired directly
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
	private final EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();
	// private final RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>();

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
	 * Returns Primary Key SQL parameter(s) for the given values
	 * @param record
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
		// jdbcTemplate.execute(sql);
		namedParameterJdbcTemplate.getJdbcTemplate().execute(sql);
	}

	@Override
	public long getCount() {
		String sql = "select count(1) from EMPLOYEE";
		//return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(sql, Long.class);
		return namedParameterJdbcTemplate.queryForObject(sql, getEmptySqlParameterSource(), Long.class);
	}

	@Override
	public Employee findById(Long id) {
		
		// SQL request used by jdbcTemplate to create a prepared statement
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		
		// Do not use "queryForObject" in this case
		// queryForObject all such methods expects that executed query will return one and only one row
		// If you get no rows or more than one row that will result in "IncorrectResultSizeDataAccessException"
		// return jdbcTemplate.queryForObject(sql, rowMapper, id);

		// Execute SQL query, return a list of Employee objects created with "row mapper"
		// get unique record in the list returned
		return uniqueRecordOrNull(namedParameterJdbcTemplate.getJdbcTemplate().query(sql, employeeRowMapper, id));
	}

	@Override
	public Employee findByIdWithParamMap(Long id) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = :id";
		
//		// Map of query parameters
//		Map<String,Object> parameters = new HashMap<>();
//		parameters.put("id", id);

		// Execute SQL query, return a list of Employee objects created with "row mapper"
		// get unique record in the list returned
		return uniqueRecordOrNull(namedParameterJdbcTemplate.query(sql, getPKParameters(id), employeeRowMapper));
	}

	@Override
	public void insert(Employee record) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "insert into employee  (id, first_name, last_name, birth_date) " 
				+ " values (:id, :firstName, :lastName, :birthDate)";
		namedParameterJdbcTemplate.update(sql, getAllParameters(record));
	}

	@Override
	public int update(Employee record) {
		// SQL request used by jdbcTemplate to create a prepared statement (with named parameter)
		String sql = "update employee set " +
				" firstName = :firstName, lastName = :latsName, birthDate = :birthDate " +
				" where id = :id " ; 
		return namedParameterJdbcTemplate.update(sql, getAllParameters(record));
	}
	
	@Override
	public int deleteById(Long id) {
		String sql = "delete from employee where id = :id";
		return namedParameterJdbcTemplate.update(sql, getPKParameters(id));
	}
}
