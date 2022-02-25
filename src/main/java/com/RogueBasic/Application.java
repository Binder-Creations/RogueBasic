package com.RogueBasic;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RogueBasic.util.CassandraGC;

@SpringBootApplication
public class Application {
	private static final Logger log = LogManager.getLogger(Application.class);
	@Autowired
	private CassandraGC cassandraGC;
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("Application started.");
			try {
				cassandraGC.start();
				log.info("Connected to Cassandra.");
			}catch(Exception e) {
				log.error("Unable to connect to Cassandra.");
				e.printStackTrace();	
			}

		};
	}

}
