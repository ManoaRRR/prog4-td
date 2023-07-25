package com.hei.project2p1.repository;

import com.hei.project2p1.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    List<Employee> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndGenderContainingIgnoreCaseAndFunctionContainingIgnoreCase(String lastName, String firstName, Employee.Gender gender, String function, Pageable pageable);

    List<Employee> findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndFunctionContainingIgnoreCase(String lastName, String firstName, String function, Pageable pageable);
}
