package fr.insacvl.asl.bcn.bookzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BookzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookzoneApplication.class, args);
	}

}
