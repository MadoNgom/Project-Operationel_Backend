package com.flux.transactions.repositories;

import com.flux.transactions.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByNumeroCompte(String numeroCompte);
    Optional<Compte> findByUtilisateurId(Long utilisateurId);
}
