package org.demo.service;

import static org.mockito.Mockito.when;

import org.demo.db.DepartmentRepository;
import org.demo.domain.model.Department;
import org.demo.services.CheckDepartmentService;
import org.demo.services.impl.CheckDepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheckDepartmentServiceTest {

	@Mock // Mock creation for the interface
	private DepartmentRepository departmentRepository;  // used by CheckDepartmentService

	@BeforeEach
    void beforeEach() {
		// MOCK behavior simulation
		when(departmentRepository.find(1)).thenReturn(new Department(1, "Dep A"));
		when(departmentRepository.find(2)).thenReturn(new Department(2, "Dep B"));
		//when(departmentRepository.find(9)).thenReturn(null) ; 
		// return null by default
    }	

	//--- Inject "DepartmentRepository" MOCK in "CheckDepartmentService" class
// Cannot use an interface because Mockito needs to know what subclass to instantiate
//    @Autowired
//    CheckDepartmentService checkDepartmentService ; 

    @InjectMocks  
    // Mockito needs to know what subclass to instantiate => new concrete class required
    CheckDepartmentService checkDepartmentService = new CheckDepartmentServiceImpl(); 
    
	@Test
	void testValidDepartment() {
//		when(departmentRepository.find(123)).thenReturn(new Department(123, "Dep A"));

		checkDepartmentService.checkDepartmentExistence(1);
		checkDepartmentService.checkDepartmentExistence(2);
	}

	@Test
	void testInvalidDep() {
//		when(departmentRepository.find(999)).thenReturn(null) ;

		IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> {
	        // Code under test
			checkDepartmentService.checkDepartmentExistence(999);
	    });		
	}

}
