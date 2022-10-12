package org.demo.services.impl;

import org.demo.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloServiceImpl implements org.demo.services.HelloService {

	@Autowired
	GreetingService greetingService ;
	
	public String hello(String name) {
		String word = greetingService.getGreetingWord("EN");
		if ( name != null ) {
			return word + " " + name + " !";
		}
		else {
			return word + " !";
		}
	}
}
