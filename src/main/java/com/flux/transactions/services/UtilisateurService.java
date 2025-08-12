package com.flux.transactions.services;

import com.flux.transactions.dtos.UtilisateurProfileDto;
import com.flux.transactions.entities.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur createUtilisateur(Utilisateur utilisateur);
    Utilisateur getUtilisateurById(Long id);
    Utilisateur getUtilisateurByEmail(String email);
    List<Utilisateur> getAllUtilisateurs();
    void deleteUtilisateur(Long id);
    boolean existsByEmail(String email);

    /**
     * Récupère le profil complet d'un utilisateur par son email
     * @param email L'email de l'utilisateur
     * @return Le DTO du profil utilisateur avec les informations du compte
     */
    UtilisateurProfileDto getUtilisateurProfileByEmail(String email);
}
