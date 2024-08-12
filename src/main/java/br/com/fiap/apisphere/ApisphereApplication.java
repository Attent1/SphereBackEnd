package br.com.fiap.apisphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApisphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApisphereApplication.class, args);
	}

	@RequestMapping
	public String home() {
		return "Hello World oi";
	}

}
