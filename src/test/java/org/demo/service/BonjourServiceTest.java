package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.demo.services.BonjourService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// if the Spring Boot starter class is not in a higher package level 
// => specify the class (cannot be found)
//@SpringBootTest(classes = SpringBootStarter.class) 
class BonjourServiceTest {

    @Autowired
    BonjourService bonjour;

	@Test
	void test() {
		String result = bonjour.bonjour("Toto");
		assertEquals("Bonjour Toto !", result);
	}

}
