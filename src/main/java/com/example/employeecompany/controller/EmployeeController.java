package com.example.employeecompany.controller;

import com.example.employeecompany.entity.Employee;
import com.example.employeecompany.repository.CompanyRepository;
import com.example.employeecompany.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping("/employees")
    public String allEmployeesPage(ModelMap map) {
        List<Employee> employees = employeeRepository.findAll();
        map.addAttribute("employees", employees);
        return "employeesPage";
    }

    @GetMapping("/addEmployee")
    public String addEmployeePage(ModelMap map) {
        map.addAttribute("companies", companyRepository.findAll());
        return "saveEmployee";
    }

    @PostMapping("/addEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }


    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

}

