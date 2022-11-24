package org.demo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
	
	//private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private Environment env;

	private void print(String msg) {
		print(msg, "");
	}
	private void print(String msg1, String msg2) {
		System.out.println(msg1 + msg2);
	}
	
	public void printProperties() {
		print("----- Spring environment properties :");
		print("spring.application.name : ", env.getProperty("spring.application.name"));
		print("spring.datasource : ");
		print("  driver-class-name : ", env.getProperty("spring.datasource.driver-class-name"));
		print("  url               : ", env.getProperty("spring.datasource.url")); 
		print("  username          : ", env.getProperty("spring.datasource.username")); 
		print("-----");
	}

}
