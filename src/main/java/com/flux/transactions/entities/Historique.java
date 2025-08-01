package com.flux.transactions.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Historique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeOperation; // Ex : "envoi", "réception", "dépôt", "retrait"

    private Double montant;

    private LocalDateTime dateOperation;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;
}
