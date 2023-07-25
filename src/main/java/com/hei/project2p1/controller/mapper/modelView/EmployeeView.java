package com.hei.project2p1.controller.mapper.modelView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeView {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String photo;
    private String gender;
    private List<String> phones;
    private String address;
    private String personalEmail;
    private String professionalEmail;
    private String cinNumber;
    private String cinIssueDate;
    private String cinIssuePlace;
    private String function;
    private Integer numberOfChildren;
    private String hiringDate;
    private String departureDate;
    private String socioProfessionalCategory;
    private String cnapsNumber;
}
