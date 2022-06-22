package com.example.springbootwebflux.repo;

import com.example.springbootwebflux.entity.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {


}

