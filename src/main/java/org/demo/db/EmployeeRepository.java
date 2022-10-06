package org.demo.db;

import org.demo.domain.model.Employee;

public interface EmployeeRepository {
	
	void createTable();
	
	int getCount();
	
	Employee findById(Long id);
	
}
