package group10.GastroAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GastroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastroApiApplication.class, args);

        System.out.println("Backend is running on port 8080");
	}

}

