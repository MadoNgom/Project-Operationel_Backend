package com.flux.transactions.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
