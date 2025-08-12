package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;

public interface CompteService {
    Compte createCompteForUtilisateur(Utilisateur utilisateur);
    Compte getCompteByNumero(String numeroCompte);
    Compte getCompteByUtilisateurId(Long utilisateurId);
    void updateSolde(Long compteId, Double nouveauSolde);
    boolean isCompteActif(Long compteId);
}
