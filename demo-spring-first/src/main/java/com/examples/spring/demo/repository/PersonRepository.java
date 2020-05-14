package com.examples.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examples.spring.demo.first.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
