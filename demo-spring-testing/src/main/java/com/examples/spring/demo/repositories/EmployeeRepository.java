package com.examples.spring
.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examples.spring.demo.model.Employee;
/**
 * Do not use @Respository here!
 * Even if it's a repository, Spring Boot handles interface differently than
 * classes, and this one can be autowired into a concrete class because
 * extends JpaRepository.
 *   
 * There's no reason to write test for the methods inherited for this interface,
 * since the implementation is handled by Spring Boot.
 * What we can do is to make some learning tests to understand how it work.
 * 
 * @author findus
 *
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	/* These methods will be implement automatically by Spring Boot if we 
	 * respect the naming convention, still it makes sense to test them,
	 * to check if we wrote the semantics correctly.
	 */
	Employee findByName(String string);

	Employee findByNameAndSalary(String string, long i);

	List<Employee> findByNameOrSalary(String string, long l);

	@Query("SELECT e FROM Employee e WHERE e.salary < :cap")
	List<Employee> findAllEmployeesWithinSalaryCap(@Param("cap") long cap);

}
