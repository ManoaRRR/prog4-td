package com.hei.project2p1.controller.mapper.employeeType;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class EmployeeUI {
    private String id;
    private String registrationNo;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String photo;
    private String telephone;

    private String sexe;

    private String adresseExacte;

    private String emailPerso;

    private String emailPro;

    private String CIN;

    private String fonction;

    private String nombreEnfant;

    private LocalDate dateEmbauche;

    private LocalDate dateDepart;

    private String categorieSocioProffessionel;

    private String cnaps;
}
