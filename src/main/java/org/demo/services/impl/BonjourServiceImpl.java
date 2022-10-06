package org.demo.services.impl;

import org.demo.services.BonjourService;
import org.springframework.stereotype.Component;

@Component
public class BonjourServiceImpl implements BonjourService {

	public void bonjour(String name) {
		System.out.println("Bonjour " + name + " !");
	}
}
