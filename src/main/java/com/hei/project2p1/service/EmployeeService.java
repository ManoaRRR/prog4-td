package com.hei.project2p1.service;

import com.hei.project2p1.exception.NotFoundException;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.model.Phone;
import com.hei.project2p1.repository.EmployeeRepository;
import com.hei.project2p1.repository.dao.EmployeeDao;
import com.hei.project2p1.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    //TODO: pagination all get
    //TODO: one query native
    private final String REGISTRATION_PREFIX = "EMP";
    private final RegistrationNoTrackerService registrationNoTrackerService;

    private final EmployeeRepository repository;
    private final EmployeeDao employeeDao;
    private final PhoneService phoneService;


    public List<Employee> getEmployeesFromDB() {
        return repository.findAll();
    }
    public Employee getEmployeeById(Integer id){
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee with id"+ id + "not found."));
    }

    public long getTotalPages(int pageSize){
        double totalCount = repository.count();
        return PaginationUtils.getTotalPages(totalCount,pageSize);
    }

    @Transactional
    public Employee save(Employee employee, List<String> phonesNo) {
        Employee toSave = autoSetRegNo(employee);
        Employee saved = repository.save(toSave);
        List<Phone> phones = phoneService.addPhonesToEmployee(saved,phonesNo);
        saved.setPhones(phones);
        return saved;
    }

    @Transactional
    public List<Employee> saveAll(List<Employee> employees) {
        List<Employee> toSave = autoSetRegNo(employees);
        return repository.saveAll(toSave);
    }

    private Employee autoSetRegNo(Employee employee){
        if (employee.getRegistrationNo()==null){
            Long last = registrationNoTrackerService.getLastRegistrationNo();
            Long updatedNo = last + 1;
            employee.setRegistrationNo(REGISTRATION_PREFIX + (updatedNo));
            registrationNoTrackerService.updateLastNo(updatedNo);
        }
        return employee;
    }

    private List<Employee> autoSetRegNo(List<Employee> employeeList){
        Long last = registrationNoTrackerService.getLastRegistrationNo();
        for (Employee e : employeeList){
            if (e.getRegistrationNo()==null){
                e.setRegistrationNo(REGISTRATION_PREFIX + last);
                last += 1L;
            }
        }
        registrationNoTrackerService.updateLastNo(last);
        return employeeList;
    }

    @Transactional
    public List<Employee> findEmployeesByCriteria(String firstName,
                                                  String lastName,
                                                  String function,
                                                  int pageNo,
                                                  int pageSize,
                                                  String sortBy,
                                                  String sortOrder) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;

        // Define sorting criteria
        Sort sort = Sort.by(direction, sortBy);
        PaginationUtils.paginationValidator(pageNo,pageSize);
        // Create a Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);

        // Perform the search using the EmployeeRepository
        return employeeDao.findByCriteria(firstName,lastName,function,pageable);
        //return repository.findByLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndFunctionContainingIgnoreCase(lastName,firstName,function,pageable);
    }
}
