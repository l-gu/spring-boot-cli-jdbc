package org.demo.db;

import java.util.List;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable();
	
	long countAll();
	
	List<Employee> findAll();

	Employee findById(Long id);
		
	void insert(Employee record) ;
	
	int update(Employee record);

	int deleteById(Long id);
}
