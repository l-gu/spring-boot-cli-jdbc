package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.demo.services.GreetingService;
import org.demo.services.HelloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * The simplest solution to use Mockito Mocks with SpringBoot
 * using only Spring annotations for mocks
 * Allows to work only with interfaces (no concrete classes required)
 * @author L. Guerin
 */
@SpringBootTest
class HelloServiceMock2Test {

	// Mock with Spring Test annotation @MockBean
	// creates a mock and add it as a bean to the context (replacing it if it exists)
	@MockBean 
	private GreetingService greetingServiceMock;  // used by HelloServiceImpl

	// Standard Spring Dependency Injection with @Autowired (injects the "MockBean")
	// (instead of Mockito @InjectMocks that requires a concrete class)
    @Autowired
	private HelloService helloService; // implementation uses GreetingService

    @BeforeEach
    void beforeEach() {
		// Define global MOCK behavior for all tests (simulation)
		when(greetingServiceMock.getGreetingWord("EN")).thenReturn("Hello") ;
		when(greetingServiceMock.getGreetingWord("FR")).thenReturn("Bonjour") ;
		// return null by default
    }	
	
	@Test
	void testWithBob() {
		String result = helloService.hello("Bob");
		assertEquals("Hello Bob !", result);
	}

	@Test
	void testWithNull() {
		String result = helloService.hello(null);
		assertEquals("Hello !", result);
	}

	@Test
	void testWithMock() {
		// Define a specific behavior for this test
		when(greetingServiceMock.getGreetingWord("EN")).thenReturn("Hi");
		String result = helloService.hello("Bob");
		assertEquals("Hi Bob !", result);
	}

}
