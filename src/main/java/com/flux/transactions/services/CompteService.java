package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;

import java.util.List;

public interface CompteService {
    Compte createCompte(Compte compte);
    Compte getCompteById(Long id);
    List<Compte> getAllComptes();
    void deleteCompte(Long id);
}
