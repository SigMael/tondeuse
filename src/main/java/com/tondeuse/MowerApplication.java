package com.tondeuse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;

/**
 * MowerApplication is the Entry point of the Mower Application.
 * It start the application using input file path in first parameter.
 *
 * @author Mael Sigaroudi
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.tondeuse")
public class MowerApplication {

	/**
	 * Application main which launch the Mower Application.
	 * @param args : Parameters given to the app. args[0] Should be input file path.
	 */
	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MowerApplication.class)){
			MowerParser parser = context.getBean("mowerParser", MowerParser.class);
			Lawn lawn = parser.parse(new File(args[0]));
			if(lawn != null) {
				System.out.println(lawn.mow());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
