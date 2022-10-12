package org.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.demo.services.GreetingService;
import org.demo.services.HelloService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
// if the Spring Boot starter class is not in a higher package level 
// => specify the class (cannot be found)
// @SpringBootTest(classes = SpringBootStarter.class) 
class HelloServiceTest {

	@Mock
	private GreetingService greetingServiceMock; 

    @Autowired
    HelloService helloService;

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
		when(greetingServiceMock.getGreetingWord("ZZ")).thenReturn("Zzzzzz");
		// TODO
	}

}
