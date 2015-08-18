package com.bergermobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.bergermobile.rest.services.SerializableResourceBundleMessageSource;

@SpringBootApplication
public class BmAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmAuthApplication.class, args);
    }
    
    @Bean
    public SerializableResourceBundleMessageSource messageBundle() {
    	SerializableResourceBundleMessageSource messageSource = new SerializableResourceBundleMessageSource();
    	messageSource.setDefaultEncoding("UTF-8");
    	messageSource.setBasename("classpath:/messages");
    	return messageSource;
    }
    
    @Bean
    public ResourceBundleMessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    	messageSource.setBasename("messages");
    	messageSource.setDefaultEncoding("UTF-8");
    	return messageSource;
    }
        
}
