package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "entreprise")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id ;

    private String nom;

    private String description;

    private String slogan;

    private String adresse;

    private String email;

    @ElementCollection
    @CollectionTable(name = "telephones", joinColumns = @JoinColumn(name = "entreprise_id"))
    @Column(name = "telephone")
    private List<String> telephones;

    private String nif;
    private String stat;
    private String rcs;

    @Lob
    @Column
    private String logo;

}
