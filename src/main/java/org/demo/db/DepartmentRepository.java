package org.demo.db;

import org.demo.domain.model.Department;

public interface DepartmentRepository {
	
	Department find(int id);

}
