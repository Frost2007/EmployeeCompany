package com.example.employeecompany.repository;

import com.example.employeecompany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Transactional
    void deleteEmployeesByCompanyId(int id);

}
