package com.tondeuse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.tondeuse")
public class MowerApplication {

	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MowerApplication.class)){
			Runner moweRunner = context.getBean("runner", Runner.class);

			//moweRunner.execTondeuse();
		} // Fermeture implicite du context
		
	}

}
