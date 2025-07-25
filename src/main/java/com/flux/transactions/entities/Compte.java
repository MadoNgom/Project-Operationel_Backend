package com.flux.transactions.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comptes")
public class Compte {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Double solde = 0.0;

    @Column(nullable = false, unique = true)
    private String numeroCompte;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id", unique = true, nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "utilisateur_id", insertable = false, updatable = false)
    private Integer utilisateurId; // Changement ici: Integer

    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

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