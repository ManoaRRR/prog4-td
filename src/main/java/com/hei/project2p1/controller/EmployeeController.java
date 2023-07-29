package com.hei.project2p1.controller;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public String index(Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }

    @GetMapping("/UploadImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            MultipartFile photo = employee.getPhoto();
            byte[] photoData = photo.getBytes();

            if (photoData != null && photoData.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                headers.setContentLength(photoData.length);
                return new ResponseEntity<>(photoData, headers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("newEmployee") Employee employee,
                              @RequestParam("photo") MultipartFile photo,
                              @RequestParam("birthDate") Date birthDate) {
        employeeService.processEmployeePhoto(employee, photo);
        employee.setBirthDate(String.valueOf(birthDate));
        employeeService.save(employee);
        return "redirect:/";
    }

    @GetMapping("/updateEmployeeForm/{id}")
    public String showUpdateEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID d'employé invalide : " + id));

        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @PostMapping("/saveUpdateEmployee/{id}")
    public String saveUpdate(@PathVariable Long id, @ModelAttribute Employee updatedEmployee) {
        Employee existingEmployee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID d'employé invalide : " + id));

        existingEmployee.updateEmployee(updatedEmployee);
        employeeService.save(existingEmployee);
        return "redirect:/";
    }

    @GetMapping("/filterEmployees")
    public String filterEmployeesByKeyword(@RequestParam("attribute") String attribute,
                                           @RequestParam("value") String value,
                                           Model model) {
        List<Employee> filteredEmployees = employeeService.filterEmployeesByAttributeAndValue(attribute, value);
        model.addAttribute("employees", filteredEmployees);
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }

    @GetMapping("/filterEmployeesByAttribute")
    public String filterEmployeesByAttribute(@RequestParam String attribute,
                                             @RequestParam String value,
                                             Model model) {
        List<Employee> filteredEmployees = employeeService.filterEmployeesByAttribute(attribute, value);
        model.addAttribute("employees", filteredEmployees);
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }

    @GetMapping("/sortEmployeesByAttribute")
    public String sortEmployeesByAttribute(@RequestParam String attribute, Model model) {
        List<Employee> sortedEmployees = employeeService.sortEmployeesByAttribute(attribute);
        model.addAttribute("employees", sortedEmployees);
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }
}



