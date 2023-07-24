package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "employee")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_registration_No",
        columnNames = { "registrationNo" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //or @Column(unique=true) use on creation of db
    private String registrationNo;
    @NotBlank(message = "firstName mandatory")
    private String firstName;
    @NotBlank(message = "lastName mandatory")
    private String lastName;
    @NotNull(message = "birthDate mandatory")
    private LocalDate birthDate;
    @Lob
    @Column //(columnDefinition = "varchar(max)")?
    private String photo;

    private String sexe;

    @ElementCollection
    @CollectionTable(name = "telephones", joinColumns = @JoinColumn(name = "entreprise_id"))
    @Column(name = "telephone")
    private List<String> telephone;

    @NotNull
    private String adresseExacte;

    @NotNull
    private String emailPerso;

    @NotNull
    private String emailPro;

    @NotNull
    private String CIN;

    @NotNull
    private String fonction;

    @NotNull
    private Integer nombreEnfant;

    @NotNull
    private LocalDate dateEmbauche;

    @NotNull
    private LocalDate dateDepart;

    @NotNull
    private String categorieSocioProffessionel;

    @NotNull
    private String cnaps;
}
