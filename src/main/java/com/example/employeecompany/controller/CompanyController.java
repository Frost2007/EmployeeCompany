package com.example.employeecompany.controller;


import com.example.employeecompany.entity.Company;
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
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/companies")
    public String allCompaniesPage(ModelMap map) {
        List<Company> companies = companyRepository.findAll();
        map.addAttribute("companies", companies);
        return "/companiesPage";
    }

    @GetMapping("/addCompany")
    public String addCompanyPage() {

        return "saveCompany";
    }


    @PostMapping("/addCompany")
    public String saveCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        employeeRepository.deleteEmployeesByCompanyId(id);
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }
}