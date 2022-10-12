package org.demo.services.impl;

import org.demo.services.GreetingService;
import org.springframework.stereotype.Component;

@Component
public class GreetingServiceImpl implements GreetingService {

	@Override
	public String getGreetingWord(String languageCode) {
		if ( languageCode != null) {
			switch(languageCode) {
			case "EN" : return "Hello" ;
			case "FR" : return "Bonjour" ;
			case "IT" : return "Buongiorno" ;
			default : throw new IllegalArgumentException("unknown language code " + languageCode ); 
			}
		}
		else {
			throw new IllegalArgumentException("language code is null");
		}
	}

}
