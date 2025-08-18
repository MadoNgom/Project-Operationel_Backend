package com.flux.transactions.repositories;

import com.flux.transactions.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Utilisateur findByEmail(String email);
    Utilisateur findByTelephone(String telephone);
    Optional<Utilisateur> findByEmail(String email);
}
