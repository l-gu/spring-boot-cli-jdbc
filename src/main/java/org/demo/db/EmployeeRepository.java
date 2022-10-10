package org.demo.db;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable();
	
	long getCount();
	
	Employee findById(Long id);
		
	void insert(Employee record) ;
	
	int update(Employee record);

	int deleteById(Long id);
}
