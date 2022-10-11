package org.demo.db;

import java.util.List;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable();
	
	long countAll();
	
	List<Employee> findAll();

	Employee findById(Long id);
		
	int insert(Employee record) ;
	
	int update(Employee record);
	
	int[] insertBatch(List<Employee> records);

	int deleteById(Long id);
}
