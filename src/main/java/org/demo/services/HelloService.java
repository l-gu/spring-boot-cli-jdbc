package org.demo.services;

import org.springframework.stereotype.Component;

@Component
public class HelloService {

	public void hello(String name) {
		System.out.println("Hello " + name + " !");
	}
}
