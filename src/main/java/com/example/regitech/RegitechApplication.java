package com.example.regitech;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exemple.service.FilesStorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages= {"com.example.controller" , "com.exemple.service"})
public class RegitechApplication implements CommandLineRunner , WebMvcConfigurer{

	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(RegitechApplication.class, args);
	}
	
	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {

	      // Register resource handler for images
	      registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
	              .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	  }

	  @Override
	  public void run(String... arg) throws Exception {
	    storageService.init();
	  }
	
//	@Bean
//	FirebaseMessaging firebaseMessaging() throws IOException {
//	    GoogleCredentials googleCredentials = GoogleCredentials
//	            .fromStream(new ClassPathResource("trackingtool-e2610-firebase-adminsdk-c2g7x-b0a7a5483a.json").getInputStream());
//	    FirebaseOptions firebaseOptions = FirebaseOptions
//	            .builder()
//	            .setCredentials(googleCredentials)
//	            .build();
//	    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
//	    return FirebaseMessaging.getInstance(app);
//	}
//	
//	@Bean
//	FirebaseMessaging firebaseMessagingAdmin() throws IOException {
//	    GoogleCredentials googleCredentials = GoogleCredentials
//	            .fromStream(new ClassPathResource("trackingtooladmin-firebase-adminsdk-xi62m-9fe865a0a8.json").getInputStream());
//	    FirebaseOptions firebaseOptions = FirebaseOptions
//	            .builder()
//	            .setCredentials(googleCredentials)
//	            .build();
//	    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
//	    return FirebaseMessaging.getInstance(app);
//	}
	
	
	
	
}



