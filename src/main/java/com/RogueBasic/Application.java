package com.RogueBasic;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraGC;
import com.datastax.oss.driver.api.core.CqlSession;


@SpringBootApplication
public class Application {
	private static final Logger log = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("Application started.");
			try {
				CqlSession service = CassandraConnector.connect();
//				CassandraGC gc = new CassandraGC();
//				gc.start();
				CassandraTemplate template = new CassandraTemplate(CassandraConnector.getSession());
				System.out.println("Connected to Cassandra.");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Unable to connect to Cassandra.");
			}

		};
	}

}
