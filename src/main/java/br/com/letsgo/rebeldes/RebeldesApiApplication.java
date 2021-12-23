package br.com.letsgo.rebeldes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class RebeldesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebeldesApiApplication.class, args);
	}

	


	 @Bean
	  public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {

	   return new OpenAPI()
	        .info(new Info()
	        .title("Rest API dos Rebeldes")
	        .version(appVersion)
	        .description(appDesciption)
	        .termsOfService("http://swagger.io/terms/"));
	 }

   
}
