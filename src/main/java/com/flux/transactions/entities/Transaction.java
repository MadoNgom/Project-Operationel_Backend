package com.flux.transactions.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flux.transactions.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "compte_expediteur_id")
    private Compte expediteur;

    @ManyToOne
    @JoinColumn(name = "compte_destinataire_id")
    private Compte destinataire;
}
