package com.hei.project2p1.controller.mapper;

import com.hei.project2p1.controller.mapper.modelView.CreateEmployeeView;
import com.hei.project2p1.controller.mapper.modelView.EmployeeView;
import com.hei.project2p1.model.Employee;
import com.hei.project2p1.model.Phone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.multipartImageToString;
import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.stringInputOfString;
import static com.hei.project2p1.controller.mapper.utils.ConvertInputTypeToDomain.stringInputValueToLocalDate;
import static com.hei.project2p1.controller.mapper.utils.ConvertNullValueToView.valueToView;

@Component
public class EmployeeMapper {
    public Employee toDomain(CreateEmployeeView createEmployeeView) {
        return Employee.builder()
                .id(createEmployeeView.getId()==null?null:Integer.parseInt(createEmployeeView.getId()))
                .lastName(createEmployeeView.getLastName())
                .firstName(createEmployeeView.getFirstName())
                .registrationNo(createEmployeeView.getRegistrationNo())
                .birthDate(stringInputValueToLocalDate(createEmployeeView.getBirthDate()))
                .photo(multipartImageToString(createEmployeeView.getPhoto()))
                .gender(createEmployeeView.getGender().length()>0?
                        Employee.Gender.valueOf(createEmployeeView.getGender())
                        :null)
                .address(createEmployeeView.getAddress())
                .cinIssueDate(stringInputValueToLocalDate(createEmployeeView.getCinIssueDate()))
                .cinIssuePlace(createEmployeeView.getCinIssuePlace())
                .cinNumber(stringInputOfString(createEmployeeView.getCinNumber()))
                .cnapsNumber(stringInputOfString(createEmployeeView.getCnapsNumber()))
                .function(createEmployeeView.getFunction())
                .numberOfChildren(createEmployeeView.getNumberOfChildren())
                .socioProfessionalCategory(createEmployeeView.getSocioProfessionalCategory().length()>0?
                        Employee.SocioProfessionalCategory.valueOf(createEmployeeView.getSocioProfessionalCategory())
                        :null)
                .hiringDate(stringInputValueToLocalDate(createEmployeeView.getHiringDate()))
                .departureDate(stringInputValueToLocalDate(createEmployeeView.getDepartureDate()))
                //.phones(createEmployeeView.getPhones()!=null?createEmployeeView.getPhones():List.of())
                .phones(List.of())
                .personalEmail(createEmployeeView.getPersonalEmail())
                .professionalEmail(createEmployeeView.getProfessionalEmail())
                .build();
    }

    public Employee toDomain(EmployeeView createEmployeeView) {

        return Employee.builder()
                .id(createEmployeeView.getId()!=null?
                        Integer.parseInt(createEmployeeView.getId())
                        :null)
                .lastName(stringInputOfString(createEmployeeView.getLastName()))
                .firstName(createEmployeeView.getFirstName())
                .registrationNo(createEmployeeView.getRegistrationNo())
                .birthDate(stringInputValueToLocalDate(createEmployeeView.getBirthDate()))
                .photo(createEmployeeView.getPhoto())
                .gender(createEmployeeView.getGender().length()>0?
                        Employee.Gender.valueOf(createEmployeeView.getGender())
                        :null)
                .address(stringInputOfString(createEmployeeView.getAddress()))
                .cinIssueDate(stringInputValueToLocalDate(createEmployeeView.getCinIssueDate()))
                .cinIssuePlace(stringInputOfString(createEmployeeView.getCinIssuePlace()))
                .cinNumber(stringInputOfString(createEmployeeView.getCinNumber()))
                .cnapsNumber(stringInputOfString(createEmployeeView.getCnapsNumber()))
                .function(stringInputOfString(createEmployeeView.getFunction()))
                .numberOfChildren(createEmployeeView.getNumberOfChildren())
                .socioProfessionalCategory(createEmployeeView.getSocioProfessionalCategory().length()>0?
                        Employee.SocioProfessionalCategory.valueOf(createEmployeeView.getSocioProfessionalCategory())
                        :null)
                .hiringDate(stringInputValueToLocalDate(createEmployeeView.getHiringDate()))
                .departureDate(stringInputValueToLocalDate(createEmployeeView.getDepartureDate()))
                .phones(List.of())
                .personalEmail(stringInputOfString(createEmployeeView.getPersonalEmail()))
                .professionalEmail(stringInputOfString(createEmployeeView.getProfessionalEmail()))
                .build();
    }

    public List<Employee> toDomain(List<CreateEmployeeView> createEmployeeViews) {
        List<Employee> employees = new ArrayList<>();
        for (CreateEmployeeView createEmployeeView : createEmployeeViews){
            employees.add(toDomain(createEmployeeView));
        }
        return employees;
    }

    public EmployeeView toView(Employee employee){

        return EmployeeView.builder()
                .id(String.valueOf(employee.getId()))
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .birthDate(valueToView(employee.getBirthDate()))
                .registrationNo(employee.getRegistrationNo())
                .photo(employee.getPhoto())
                .gender(String.valueOf(employee.getGender()))
                .address(employee.getAddress())
                .cinIssueDate(valueToView(employee.getCinIssueDate()))
                .cinIssuePlace(employee.getCinIssuePlace())
                .cinNumber(employee.getCinNumber())
                .cnapsNumber(employee.getCnapsNumber())
                .function(employee.getFunction())
                .numberOfChildren(employee.getNumberOfChildren())
                .socioProfessionalCategory(String.valueOf(employee.getSocioProfessionalCategory()))
                .hiringDate(valueToView(employee.getHiringDate()))
                .departureDate(valueToView(employee.getDepartureDate()))
                .phones(employee.getPhones()==null?List.of():employee.getPhones().stream().map(Phone::getNumber).toList())
                .personalEmail(employee.getPersonalEmail())
                .professionalEmail(employee.getProfessionalEmail())
                .build();
    }

    public List<EmployeeView> toView(List<Employee> employees){
        List<EmployeeView> employeeViews = new ArrayList<>();
        for (Employee employee: employees){
            employeeViews.add(toView(employee));
        }
        return employeeViews;
    }

}

