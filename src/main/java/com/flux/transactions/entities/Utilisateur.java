package com.flux.transactions.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Schema(description = "Details about a user of the Flux Transactions application")
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer id;

    @Column()
    @Schema(description = "First name of the user", example = "John")
    private String nom;

    @Column()
    @Schema(description = "Last name of the user", example = "Doe")
    private String prenom;

    @Column(unique = true, nullable = false)
    @Schema(description = "Email address of the user, must be unique", example = "john.doe@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Hashed password of the user", example = "aStrongHashedPassword123")
    private String password;

    @Column(nullable = false, unique = true)
    @Schema(description = "Phone number of the user, must be unique", example = "221771234567")
    private String telephone;

    @Column(nullable = false)
    @Schema(description = "Timestamp when the user account was created", example = "2023-10-26T10:00:00Z")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(description = "Timestamp when the user account was last updated", example = "2023-10-26T11:30:00Z")
    private LocalDateTime updatedAt;

    /**
     * Cette méthode est appelée avant que l'entité ne soit persistée (enregistrée pour la première fois) dans la base de données.
     * Elle définit automatiquement la date de création et la date de mise à jour.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now(); // Initialise updatedAt aussi lors de la création
    }

    /**
     * Cette méthode est appelée avant que l'entité ne soit mise à jour dans la base de données.
     * Elle met à jour automatiquement la date de dernière modification.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}