package com.flux.transactions.repositories;

import com.flux.transactions.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Find a user by email, useful for login
    Optional<Utilisateur> findByEmail(String email);

    // Check if a user with the given email exists
    boolean existsByEmail(String email);

    // Check if a user with the given phone number exists
    boolean existsByTelephone(String telephone);
}