package org.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "org.demo") 
public class SpringBootStarter {

//    public static void main(String[] args) {
//    	ConfigurableApplicationContext appContext =  SpringApplication.run(SpringBootStarter.class, args);
//        SpringBootStarter app = appContext.getBean(SpringBootStarter.class);
//        app.run(args);
//    }
//
//    private void run(String[] args) {
//    	System.out.println("Hello world!");
//    	System.out.println("args.length = " + args.length);
//    }
    
    public static void main(String[] args) {
    	ConfigurableApplicationContext appContext =  SpringApplication.run(SpringBootStarter.class, args);
    	
    	// get application as "root component"
    	MyApplication app = appContext.getBean(MyApplication.class);
    	// launch app with its "run" method
        app.run(args);
    }

}
