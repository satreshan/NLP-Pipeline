package com.sap.health.platform.pipelinemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class NLPApplication extends SpringBootServletInitializer{
	  public static void main(String[] args) {
		    SpringApplication.run(NLPApplication.class, args);
		  }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return super.configure(builder);
	}
	  
	  
}
