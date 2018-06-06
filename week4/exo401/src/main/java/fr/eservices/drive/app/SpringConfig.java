package fr.eservices.drive.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


// use this class as a configuration class for spring context
// set a scan package to get JPA DAO and Hmac password checker
@Configuration
@ComponentScan(basePackages = {"fr.eservices.drive.util","fr.eservices.drive.app","fr.eservices.drive.dao.impl"})
public class SpringConfig {

	// expose this as a bean for spring context
	// expose an entity manager for DAO using JPA

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		return Persistence.createEntityManagerFactory("myApp");
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

}
