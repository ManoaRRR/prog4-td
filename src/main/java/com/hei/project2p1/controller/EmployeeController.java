package com.hei.project2p1.controller;

import com.hei.project2p1.controller.constant.EmployeeUrl;
import com.hei.project2p1.controller.mapper.EmployeeMapper;
import com.hei.project2p1.controller.mapper.modelView.CreateEmployeeView;
import com.hei.project2p1.controller.mapper.modelView.EmployeeView;
import com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
    public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    //TODO: button to create CSV file
    @GetMapping(value = "/")
    public List<Employee> employeeList(
            @RequestParam(value = "page", defaultValue = "1") int pageNo,
            @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort_by",required = false, defaultValue = "lastName") String sortBy,
            @RequestParam(value = "sort_order",required = false, defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "last_name",required = false, defaultValue = "") String lastName,
            @RequestParam(value = "first_name",required = false, defaultValue = "") String firstName,
            @RequestParam(value = "function",required = false, defaultValue = "") String function
    ){
        return employeeService.findEmployeesByCriteria(firstName,lastName,function,pageNo,pageSize,sortBy,sortOrder);
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_LIST)
    public String index(@RequestParam(value = "page", defaultValue = "1") int pageNo,
                        @RequestParam(value = "page_size", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort_by", defaultValue = "lastName") String sortBy,
                        @RequestParam(value = "sort_order", defaultValue = "ASC") String sortOrder,
                        @RequestParam(value = "last_name",required = false, defaultValue = "") String lastName,
                        @RequestParam(value = "first_name",required = false, defaultValue = "") String firstName,
                        @RequestParam(value = "function",required = false, defaultValue = "") String function,
                        Model model) {
        //List<Employee> employees = employeeService.getEmployeesFromDB();
        //
        List<Employee> employees = employeeService.findEmployeesByCriteria(
                firstName,
                lastName,
                function,
                pageNo, pageSize, sortBy, sortOrder);
        long totalPages = employeeService.getTotalPages(pageSize);
        List<EmployeeView> employeesView = employeeMapper.toView(employees);
        List<String> genderList = Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("employees", employeesView);
        model.addAttribute("genderList", genderList);
        model.addAttribute("sortField", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("page", pageNo);
        model.addAttribute("page_size", pageSize);
        model.addAttribute("totalPages", totalPages);
        logger.info("------------ lastName: " +lastName);
        logger.info("------------ firstName: " +firstName);
        //logger.info("------------ sortBy: " +sortBy);
        //logger.info("------------ sortOrder: " +sortOrder);
        //logger.info("------------ page: " +pageNo);
        //logger.info("------------ pageSize: " +pageSize);

        return "index";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_ADD)
    public String addNewEmployee( Model model) {
        List<String> genderList= Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("genders", genderList);
        List<String> categories = Stream.of(Employee.SocioProfessionalCategory.values()).map(Enum::name).toList();
        model.addAttribute("categories", categories);
        model.addAttribute("phones", new ArrayList<>());
        model.addAttribute("newEmployee", new EmployeeView());
        return "add-employee";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_UPDATE)
    public String modifyEmployeePage( Model model, @PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        CreateEmployeeView createEmployeeView = CreateEmployeeView.builder().build();
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("regNo", employee.getRegistrationNo());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("lastName", employee.getLastName());
        model.addAttribute("birthDate", employee.getBirthDate());
        model.addAttribute("photoString", employee.getPhoto());
        model.addAttribute("gender", employee.getGender());
        List<String> genderList= Stream.of(Employee.Gender.values()).map(Enum::name).toList();
        model.addAttribute("genders", genderList);
        return "modify-employee";
    }

    @GetMapping(value = EmployeeUrl.EMPLOYEES_DETAILS)
    public String details(Model model, @PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        EmployeeView createEmployeeView = employeeMapper.toView(employee);
        model.addAttribute("employee", createEmployeeView);
        return "employee_details";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("gender") String gender,
            @RequestParam("phones") List<String> phones,
            @RequestParam("address") String address,
            @RequestParam("personalEmail") String personalEmail,
            @RequestParam("professionalEmail") String professionalEmail,
            @RequestParam("cinNumber") String cinNumber,
            @RequestParam("cinIssueDate") String cinIssueDate,
            @RequestParam("cinIssuePlace") String cinIssuePlace,
            @RequestParam("function") String function,
            @RequestParam("numberOfChildren") Integer numberOfChildren,
            @RequestParam("hiringDate") String hiringDate,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("socioProfessionalCategory") String socioProfessionalCategory,
            @RequestParam("cnapsNumber") String cnapsNumber,
            Model model
    ) {
        String photoTreated = ConvertInputTypeToDomain.multipartImageToString(photo);

        EmployeeView employee = EmployeeView.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .photo(photoTreated)
                .gender(gender)
                .phones(phones==null?new ArrayList<>():phones)
                .address(address)
                .personalEmail(personalEmail)
                .professionalEmail(professionalEmail)
                .cinNumber(cinNumber)
                .cinIssueDate(cinIssueDate)
                .cinIssuePlace(cinIssuePlace)
                .function(function)
                .numberOfChildren(numberOfChildren)
                .hiringDate(hiringDate)
                .departureDate(departureDate)
                .socioProfessionalCategory(socioProfessionalCategory)
                .cnapsNumber(cnapsNumber)
                .registrationNo(null)
                .build();
        //logger.info(employee.toString());
        employeeService.save(employeeMapper.toDomain(employee), employee.getPhones());
        return "redirect:"+ EmployeeUrl.EMPLOYEES_LIST;
    }

    @PostMapping("/modifyEmployee")
    public String modifyEmployee(
            @ModelAttribute("employeeId") String employeeId,
            @ModelAttribute("firstName") String firstName,
            @ModelAttribute("lastName") String lastName,
            @ModelAttribute("photoString") String photoString,
            @ModelAttribute("birthDate") String birthDate,
            @ModelAttribute("regNo") String regNo,
            @RequestParam("photo") MultipartFile photoFile) {

        EmployeeView createEmployeeView = EmployeeView.builder()
                .id(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .registrationNo(regNo)
                .birthDate(String.valueOf(birthDate))
                .photo(photoFile.getOriginalFilename().isEmpty() ? photoString : ConvertInputTypeToDomain.multipartImageToString(photoFile))
                .build();

        //logger.info("Photo File : " + (photoFile.getOriginalFilename()));
        employeeService.save(employeeMapper.toDomain(createEmployeeView), createEmployeeView.getPhones());
        return "redirect:"+ EmployeeUrl.EMPLOYEES_LIST;
    }
}