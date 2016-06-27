package gov.ca.emsa.pulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"gov.ca.emsa.pulse.**"})
public class ServiceApplication   {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
