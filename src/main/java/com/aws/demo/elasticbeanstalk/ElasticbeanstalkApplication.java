package com.aws.demo.elasticbeanstalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ElasticbeanstalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticbeanstalkApplication.class, args);
	}

}

@RestController
class HelloController{
	@GetMapping
  	public String hello() {
		return "Application deployed successfully on AWS Elastic Beanstalk!";
	}
}
