package com.arlei.fipe;

import com.arlei.fipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
         //https://deividfortuna.github.io/fipe/
		//https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos
		//https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos/7132/anos

		Principal principal = new Principal();
		principal.exibeMenu();


	}
}
