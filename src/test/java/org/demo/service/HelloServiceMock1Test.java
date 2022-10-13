package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.demo.services.GreetingService;
import org.demo.services.HelloService;
import org.demo.services.impl.HelloServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Mocks with Mockito annotations (and run with SpringBoot ) 
 * Constraint : Mockito needs to know what subclass to instantiate for mock injection
 * => cannot use interfaces => cannot use Spring "Autowired" + interface 
 * 
 * @author L. Guerin
 */
@SpringBootTest
class HelloServiceMock1Test {

	//--- 1) Create mock with Mockito annotation "@Mock"
	@Mock // Mock creation for the interface
	private GreetingService greetingServiceMock;  // used by HelloServiceImpl

	//--- 2) Inject mock in the class
	// Cannot use an interface because Mockito needs to know what subclass to instantiate
	//	    @Autowired
	//	    private HelloService helloService; 
	@InjectMocks // Mockito annotation
	// ERROR : Cannot instantiate @InjectMocks field named 'helloService'! 
	//         Cause: the type 'HelloService' is an interface.
	// private HelloService helloService ; 
	// Mockito needs to know what subclass to instantiate => new concrete class required
	private HelloService helloService = new HelloServiceImpl(); 

	@BeforeEach
	void beforeEach() {
		// Define global MOCK behavior for all tests (simulation)
		when(greetingServiceMock.getGreetingWord("EN")).thenReturn("Hello");
		when(greetingServiceMock.getGreetingWord("FR")).thenReturn("Bonjour");
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
