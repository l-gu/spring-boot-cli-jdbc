package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.demo.services.GreetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// if the Spring Boot starter class is not in a higher package level 
// => specify the class (cannot be found)
// @SpringBootTest(classes = SpringBootStarter.class) 
class GreetingServiceTest {

    @Autowired
	private GreetingService greetingService; 

	@Test
	void testWithValidCodes() {
		assertEquals("Hello", greetingService.getGreetingWord("EN"));
		assertEquals("Bonjour", greetingService.getGreetingWord("FR"));
		assertEquals("Buongiorno", greetingService.getGreetingWord("IT"));
	}

	@Test
	void testWithUnknownCode() {
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
	        // Code under test
			greetingService.getGreetingWord("ZZ");
		});
		assertTrue(ex.getMessage().contains("unknown"));
		assertTrue(ex.getMessage().contains("ZZ"));
	}

	@Test
	void testWithNull() {
		IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
	        // Code under test
			greetingService.getGreetingWord(null);
		});
		assertTrue(ex.getMessage().contains("null"));
	}

}
