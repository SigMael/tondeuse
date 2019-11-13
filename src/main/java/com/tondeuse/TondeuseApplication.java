package com.tondeuse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.tondeuse")
public class TondeuseApplication {

	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TondeuseApplication.class)){
			Runner runnerLauncher = context.getBean("runner", Runner.class);
			
			runnerLauncher.execute();
		} // Fermeture implicite du context
		
	}

}
