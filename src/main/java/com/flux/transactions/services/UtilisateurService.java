package com.flux.transactions.services;

import com.flux.transactions.entities.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur createUtilisateur(Utilisateur utilisateur);
    Utilisateur getUtilisateurById(Long id);
    Utilisateur getUtilisateurByEmail(String email);
    List<Utilisateur> getAllUtilisateurs();
    void deleteUtilisateur(Long id);
    boolean existsByEmail(String email);
}
