package com.hei.project2p1.service;

import com.hei.project2p1.model.Employee;

import com.hei.project2p1.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import java.util.Collections;


@AllArgsConstructor
@Service
public class EmployeeService {

    private static final String ATTRIBUTE_NAME_FIRST_NAME = "firt";
    private final EmployeeRepository employeeRepository;



    public List<Employee> getEmployees() {

        return employeeRepository.findAll();
    }


    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * Method to process the employee's photo.
     *
     * @param employee The Employee object for which to process the photo.
     * @param photo    The MultipartFile containing the employee's photo.
     */
    public void processEmployeePhoto(Employee employee, MultipartFile photo) {
        try {
            // Vérifier si un fichier d'image est téléchargé
            if (photo != null && !photo.isEmpty()) {
                // Si un fichier d'image est téléchargé, lisez le contenu du fichier et attribuez-le à la propriété 'photo' de l'employé
                employee.setImageData(photo.getBytes());
            } else {
                // Si aucun fichier d'image n'est téléchargé, attribuez une valeur null à la propriété 'photo' de l'employé
                employee.setPhoto(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur ici, par exemple : logger.error("Erreur lors du traitement de l'image", e);
        }
    }

    public Optional<Employee> getEmployeeById(Long EmployeeId) {
        if (EmployeeId == null || EmployeeId <= 0) {
            throw new IllegalArgumentException("Employee ID must be a positive non-zero value.");
        }
        return employeeRepository.findById(EmployeeId);
    }

    public List<Employee> filterEmployeesByAttributeAndValue(String attribute, String value) {
        if (attribute == null || value == null) {
            throw new IllegalArgumentException("Attribute and value cannot be null.");
        }
        // Utilize the existing method to filter employees based on the attribute and value
        return employeeRepository.findAll((root, query, builder) -> {
            // Build the search condition based on the specified attribute and value
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }

    /**
     * Method to filter employees based on the attribute.
     *
     * @param attribute The attribute on which to filter the employees.
     * @param value     The value to use for filtering.
     * @return The list of filtered employees.
     */
    public List<Employee> filterEmployeesByAttribute(String attribute, String value) {
        // Implement the logic to filter employees based on the attribute and value
        // Use JPA CriteriaQuery to create the filtering query
        return employeeRepository.findAll((root, query, builder) -> {
            // Build the search condition based on the specified attribute and value
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }

    /**
     * Method to sort employees based on the attribute.
     *
     * @param attribute The attribute on which to sort the employees.
     * @return The list of sorted employees.
     */
    public List<Employee> sortEmployeesByAttribute(String attribute) {
        List<String> validAttributes = List.of("firstName", "lastName", "salary"); // Add more if needed
        if (validAttributes.contains(attribute)) {
            return employeeRepository.findAll(Sort.by(attribute));
        } else {
            // Handle the case when the provided attribute is invalid
            return Collections.emptyList();
        }
    }
}


