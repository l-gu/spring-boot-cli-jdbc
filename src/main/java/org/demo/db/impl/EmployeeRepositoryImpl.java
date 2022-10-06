package org.demo.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.demo.db.EmployeeRepository;
import org.demo.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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

	@Override
	public Employee findById(Long id) {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		//RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>();
		RowMapper<Employee> rowMapper = new EmployeeRowMapper();
		// Query given SQL to create a prepared statement from SQL
		// and a list of arguments to bind to the query,
		// mapping a single result row to a result object via a RowMapper.
		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}

	private LocalDate toLocalDate(java.sql.Date sqlDate) {
		if ( sqlDate != null ) {
			return sqlDate.toLocalDate();
		}
		return null ;
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
