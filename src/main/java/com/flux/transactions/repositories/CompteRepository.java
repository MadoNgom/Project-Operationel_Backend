package com.flux.transactions.repositories;

import com.flux.transactions.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("ALL")
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findByNumeroCompte(String numeroCompte);
}

