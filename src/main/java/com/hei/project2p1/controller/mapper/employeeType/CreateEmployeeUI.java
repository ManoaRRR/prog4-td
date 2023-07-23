package com.hei.project2p1.controller.mapper.employeeType;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
public class CreateEmployeeUI {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private String birthDate;
    private MultipartFile photo;
}
