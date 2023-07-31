package com.hei.project2p1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;


    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "slogan", nullable = false)
    private String slogan;

    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Column(name = "email", nullable = false)
    private String email;


    @ElementCollection
    @CollectionTable(name = "telephones", joinColumns = @JoinColumn(name = "entreprise_id"))
    @Column(name = "telephone")
    private List<String>telephone;


    @Column(name = "nif", nullable = false)
    private String nif;


    @Column(name = "stat" , nullable = false)
    private String stat;


    @Column(name = "rcs", nullable = false)
    private String rcs;


    @Lob
    @Column(name = "logo", nullable = false)
    private byte[] logo;
    public String getLogoUrl() {
        if (logo != null && logo.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(logo);
        } else {
            return "";
        }
    }
}
