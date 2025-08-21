package com.flux.transactions.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCompte;

    private Double solde = 0.0;

    private String devis = "CFA";

    private Boolean actif = true;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    @JsonIgnore
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "expediteur")
    private List<Transaction> transactionsEnvoyees;

    @OneToMany(mappedBy = "destinataire")
    private List<Transaction> transactionsRecues;
}
