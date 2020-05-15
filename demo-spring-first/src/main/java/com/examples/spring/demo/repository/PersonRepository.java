package com.examples.spring.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examples.spring.demo.first.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	// following Spring Boot name conventions, he can implements this method
	// automatically at runtime
	List<Person> findByLastNameOrderByFirstName(String lastName);
	
}
