package com.aws.demo.elasticbeanstalk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ElasticbeanstalkApplication {

	private static final Logger log = LoggerFactory.getLogger(ElasticbeanstalkApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ElasticbeanstalkApplication.class);
		application.addListeners(new DatabaseEnvironmentLogger());
		application.run(args);
	}

	private static class DatabaseEnvironmentLogger implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
		@Override
		public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
			Environment environment = event.getEnvironment();

			log.info("RDS_HOSTNAME={}", environment.getProperty("RDS_HOSTNAME"));
			log.info("RDS_PORT={}", environment.getProperty("RDS_PORT"));
			log.info("RDS_DB_NAME={}", environment.getProperty("RDS_DB_NAME"));
			log.info("RDS_USERNAME={}", environment.getProperty("RDS_USERNAME"));
			log.info("RDS_PASSWORD configured={}", environment.getProperty("RDS_PASSWORD") != null);
		}
	}
}

@RestController
class HelloController{
	@GetMapping
  	public String hello() {
		return "Application deployed successfully on AWS Elastic Beanstalk!";
	}
}
