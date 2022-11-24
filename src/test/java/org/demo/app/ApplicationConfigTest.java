package org.demo.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationConfigTest {

    @Autowired
    ApplicationConfig applicationConfig;

	@Test
	void test() {
		applicationConfig.printProperties();
		//assertEquals("Bonjour Toto !", result);
	}

}
