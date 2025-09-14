package com.flux.transactions.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.flux.transactions.enums.TypeRole;

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

    @Default
    @Enumerated(EnumType.STRING)
    private TypeRole role = TypeRole.USER;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private Compte compte;
}
