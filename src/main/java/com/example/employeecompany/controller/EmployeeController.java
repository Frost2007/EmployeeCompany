package com.example.employeecompany.controller;

import com.example.employeecompany.entity.Employee;
import com.example.employeecompany.repository.CompanyRepository;
import com.example.employeecompany.repository.EmployeeRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${CompanyEmployee.images.path}")
    private String imagePath;


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
    public String saveEmployee(@ModelAttribute Employee employee,
                               @RequestParam("picture") MultipartFile uploadFile) throws IOException {
        if (!uploadFile.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            File file = new File(imagePath + filename);
            uploadFile.transferTo(file);
            employee.setPicUrl(filename);
        }
        employeeRepository.save(employee);

        return "redirect:/employees";
    }


    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") int id, ModelMap map) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            map.addAttribute("employee", byId.get());
        }
        return "viewEmployee";

    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);

        return IOUtils.toByteArray(inputStream);
    }

}

