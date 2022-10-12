package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.demo.services.GreetingService;
import org.demo.services.HelloService;
import org.demo.services.impl.HelloServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloServiceTest {

	@Mock
	private GreetingService greetingServiceMock;  // used by HelloServiceImpl

    @Autowired
	HelloService helloService;
	
    @InjectMocks
    HelloService helloServiceMock = new HelloServiceImpl(); // concrete class required for mock

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
		// Change behavior with mock
		when(greetingServiceMock.getGreetingWord("EN")).thenReturn("Hi");
		String result = helloServiceMock.hello("Bob");
		assertEquals("Hi Bob !", result);
	}

}
