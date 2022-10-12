package org.demo.services.impl;

import org.demo.services.BonjourService;
import org.springframework.stereotype.Component;

@Component
public class BonjourServiceImpl implements BonjourService {

	public String bonjour(String name) {
		return "Bonjour " + name + " !";
	}
}
