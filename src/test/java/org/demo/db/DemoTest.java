package org.demo.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoTest {

	@BeforeEach
	void beforeEach() {
		System.out.println("Before each test... ");
	}
	
	@Test
	void test() {
		assertEquals("a", "a");
		System.out.println("in test ");
	}

	@Test
	void test2() {
		assertEquals(12, 12);
		System.out.println("in test2 ");
	}

}
