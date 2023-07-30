package com.hei.project2p1.controller;

import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
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
import java.io.PrintWriter;
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
        model.addAttribute("employees", employeeService.getEmployees());
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
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @PostMapping("/saveUpdateEmployee")
    public String saveUpdateEmployee(@ModelAttribute Employee updatedEmployee) {
        Employee existingEmployee = employeeService.getEmployeeById(updatedEmployee.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + updatedEmployee.getId()));
        existingEmployee.updateEmployee(updatedEmployee);
        employeeService.save(existingEmployee);
        return "redirect:/";
    }

    @GetMapping("/filterEmployees")
    public String filterEmployeesByKeyword(@RequestParam("attribute") String attribute,
                                           @RequestParam("value") String value, Model model) {
        List<Employee> filteredEmployees = employeeService.filterEmployeesByAttributeAndValue(attribute, value);
        model.addAttribute("employees", filteredEmployees);
        model.addAttribute("newEmployee", new Employee());
        return "index";
    }

    @GetMapping("/filterEmployeesByAttribute")
    public String filterEmployeesByAttribute(@RequestParam String attribute,
                                             @RequestParam String value, Model model) {
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

    @GetMapping("/exportCSV")
    public void exportCSV(@RequestParam(value = "attribute", required = false) String attribute,
                          @RequestParam(value = "value", required = false) String value,
                          HttpServletResponse response) throws IOException {

        // Obtenez la liste d'employés filtrée si des attributs de filtrage sont fournis
        List<Employee> employees;
        if (attribute != null && value != null) {
            employees = employeeService.filterEmployeesByAttributeAndValue(attribute, value);
        } else {
            employees = employeeService.getEmployees();
        }

        // Générez le contenu CSV à partir de la liste d'employés
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,First Name,Last Name,Birth Date,Sex,Phones,Address,Personal Email,Work Email,CIN Number,CIN Date of Issue,CIN Place of Issue,Job Title,Children Count,Hire Date,Departure Date,Socio-Professional Category,CNAPS Number\n");
        for (Employee employee : employees) {
            csvContent.append(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s\n",
                    employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getBirthDate(),
                    employee.getSex(), employee.getPhones(), employee.getAddress(), employee.getPersonalEmail(),
                    employee.getWorkEmail(), employee.getCinNumber(), employee.getCinDateOfIssue(),
                    employee.getCinPlaceOfIssue(), employee.getJobTitle(), employee.getChildrenCount(),
                    employee.getHireDate(), employee.getDepartureDate(), employee.getSocioProfessionalCategory(),
                    employee.getCnapsNumber()));
        }

        // Configurez la réponse HTTP pour le téléchargement du fichier CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employee_list.csv\"");

        // Écrivez le contenu CSV dans la réponse
        try (PrintWriter writer = response.getWriter()) {
            writer.write(csvContent.toString());
        }
    }

    @GetMapping("/exportAllEmployeesCSV")
    public void exportAllEmployeesCSV(HttpServletResponse response) throws IOException {
        // Récupérer toutes les données des employés depuis la base de données
        List<Employee> employees = employeeService.getEmployees();

        // Générez le contenu CSV à partir de la liste d'employés
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,First Name,Last Name,Birth Date,Sex,Phones,Address,Personal Email,Work Email,CIN Number,CIN Date of Issue,CIN Place of Issue,Job Title,Children Count,Hire Date,Departure Date,Socio-Professional Category,CNAPS Number\n");
        for (Employee employee : employees) {
            csvContent.append(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s\n",
                    employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getBirthDate(),
                    employee.getSex(), employee.getPhones(), employee.getAddress(), employee.getPersonalEmail(),
                    employee.getWorkEmail(), employee.getCinNumber(), employee.getCinDateOfIssue(),
                    employee.getCinPlaceOfIssue(), employee.getJobTitle(), employee.getChildrenCount(),
                    employee.getHireDate(), employee.getDepartureDate(), employee.getSocioProfessionalCategory(),
                    employee.getCnapsNumber()));
        }

        // Configurez la réponse HTTP pour le téléchargement du fichier CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"all_employees.csv\"");

        // Écrivez le contenu CSV dans la réponse
        try (PrintWriter writer = response.getWriter()) {
            writer.write(csvContent.toString());
        }
    }



}



