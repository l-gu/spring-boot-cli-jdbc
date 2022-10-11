package org.demo;

import org.demo.app.MyApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
//If this class is not at the top level of the packages hierarchy use @ComponentScan
//@ComponentScan(basePackages = "org.demo") 
public class SpringBootStarter {

    public static void main(String[] args) {
    	ConfigurableApplicationContext appContext =  SpringApplication.run(SpringBootStarter.class, args);
    	
    	// get application as "root component"
    	MyApplication app = appContext.getBean(MyApplication.class);
    	// launch app with its "run" method
        app.run(args);
    }

}
