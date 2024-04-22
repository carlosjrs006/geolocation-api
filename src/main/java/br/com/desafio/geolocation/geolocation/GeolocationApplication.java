package br.com.desafio.geolocation.geolocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@EnableFeignClients
@SpringBootApplication
@ComponentScan("br.com.desafio.geolocation")
public class GeolocationApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		SpringApplication.run(GeolocationApplication.class, args);
	}

}
