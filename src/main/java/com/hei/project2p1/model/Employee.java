package com.hei.project2p1.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "employee")
@Table(uniqueConstraints = {
        /*@UniqueConstraint(columnNames = {"registrationNo"},name = "unique_registration_no"),
        @UniqueConstraint(columnNames = {"personalEmail"},name = "unique_email"),
        @UniqueConstraint(columnNames = {"professionalEmail"},name = "unique_email"),
        @UniqueConstraint(columnNames = {"cinNumber"},name = "unique_cin_number"),
        @UniqueConstraint(columnNames = {"cnapsNumber"},name = "unique_cnaps_number"),
         */
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //TODO: unique
    private String registrationNo;
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    //@NotNull(message = "birthDate is mandatory")
    private LocalDate birthDate;

    //@NotNull(message = "Sex is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Phone> phones;

    //@NotBlank(message = "Exact address is required")
    private String address;

    //@NotBlank(message = "Personal email is required")
    @Email(message = "Invalid personal email format")
    private String personalEmail;

    //@NotBlank(message = "Professional email is required")
    @Email(message = "Invalid professional email format")
    private String professionalEmail;

    //@NotBlank(message = "CIN number is required")
    private String cinNumber;

    private LocalDate cinIssueDate;

    //@NotBlank(message = "CIN issue place is required")
    private String cinIssuePlace;

    //@NotBlank(message = "Function is required")
    private String function;

    @Min(value = 0, message = "Number of children must be non-negative")
    private Integer numberOfChildren;

    //@NotNull(message = "Hiring date is required")
    private LocalDate hiringDate;

    private LocalDate departureDate;

    //@NotNull(message = "Socio-professional category is required")
    @Enumerated(EnumType.STRING)
    private SocioProfessionalCategory socioProfessionalCategory;

    //TODO: to another DB
    //@NotBlank(message = "CNAPS number is required")
    private String cnapsNumber;

    @Lob //large object
    @Column(columnDefinition = "TEXT")
    private String photo;

    public enum Gender {
        H, F
    }
    public enum SocioProfessionalCategory {
        M1, M2, OS1, OS2, OS3, OP1A, OP1B, OP2, OP3
    }
}
