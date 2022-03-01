package com.RogueBasic;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.RogueBasic.enums.CassandraConstant;
import com.RogueBasic.enums.SystemEnv;
import com.datastax.oss.driver.api.core.CqlSession;

@Configuration
@EnableCassandraRepositories(basePackages = {"com.RogueBasic"})
public class CassandraConfig {
    File driverConfig = new File(System.getProperty("user.dir")+"/application.conf");

	@Primary
	@Bean
	public CqlSession session() throws NoSuchAlgorithmException {
		return CqlSession.builder().
	            withConfigLoader(DriverConfigLoader.fromFile(driverConfig)).
	            withAuthCredentials(SystemEnv.USERNAME.env(), SystemEnv.PASSWORD.env()).
	            withSslContext(SSLContext.getDefault()).
	            withKeyspace(CassandraConstant.KEYSPACE.constant()).
	            build();
	}
    
  @Bean
  public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
    SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
    sessionFactory.setSession(session);
    sessionFactory.setConverter(converter);
    sessionFactory.setSchemaAction(SchemaAction.NONE);
    return sessionFactory;
  }

  @SuppressWarnings("deprecation")
  @Bean
  public CassandraMappingContext mappingContext(CqlSession cqlSession) {
    CassandraMappingContext mappingContext = new CassandraMappingContext();
    mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
    return mappingContext;
  }

  @Bean
  public CassandraConverter converter(CassandraMappingContext mappingContext) {
    return new MappingCassandraConverter(mappingContext);
  }

  @Bean
  public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
    return new CassandraTemplate(sessionFactory, converter);
  }
}
