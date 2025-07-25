package com.flux.transactions.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionTypes type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "expediteur_id", referencedColumnName = "id", nullable = false)
    private Utilisateur expediteur;

    @Column(name = "expediteur_id", insertable = false, updatable = false)
    private Integer expediteurId; // Changement ici: Integer

    @ManyToOne
    @JoinColumn(name = "destinataire_id", referencedColumnName = "id", nullable = false)
    private Utilisateur destinataire;

    @Column(name = "destinataire_id", insertable = false, updatable = false)
    private Integer destinataireId; // Changement ici: Integer

    // --- JPA Lifecycle Callbacks ---
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}