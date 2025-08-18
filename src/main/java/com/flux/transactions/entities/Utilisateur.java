package com.flux.transactions.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;

    @Column(unique = true) // contrainte unique
    private String telephone;

    private String adresse;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private Compte compte;
}
