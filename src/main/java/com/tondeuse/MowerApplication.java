package com.tondeuse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.tondeuse")
public class MowerApplication {

	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MowerApplication.class)){

			MowerParser parser = context.getBean("mowerParser", MowerParser.class);
			File inputFile = new File(args[0]);

			Lawn lawn = parser.parse(inputFile);
			System.out.println(lawn.mow());

		} // Fermeture implicite du context
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
