package com.flux.transactions.services;

import com.flux.transactions.dtos.CompteDto;
import com.flux.transactions.dtos.UtilisateurProfileDto;
import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.enums.TypeRole;
// import com.flux.transactions.exceptions.DuplicateResourceException;
import com.flux.transactions.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final CompteService compteService;

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        // S'assurer que le rôle par défaut est défini si aucun rôle n'est fourni
        if (utilisateur.getRole() == null) {
            utilisateur.setRole(TypeRole.USER);
        }

        // Créer le compte automatiquement via le service dédié
        compteService.createCompteForUtilisateur(utilisateur);

        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return utilisateurRepository.findByEmail(email).isPresent();
    }

    /**
     * Récupère le profil complet d'un utilisateur par son email
     *
     * @param email L'email de l'utilisateur
     * @return Le DTO du profil utilisateur avec les informations du compte
     */
    @Override
    public UtilisateurProfileDto getUtilisateurProfileByEmail(String email) {
        Utilisateur utilisateur = getUtilisateurByEmail(email);
        if (utilisateur == null) {
            return null;
        }

        return convertUtilisateurToProfileDto(utilisateur);
    }

    public UtilisateurProfileDto convertUtilisateurToProfileDto(Utilisateur utilisateur) {
        // Créer le DTO du compte
        CompteDto compteDto = null;
        if (utilisateur.getCompte() != null) {
            Compte compte = utilisateur.getCompte();
            compteDto = new CompteDto(
                    compte.getId(),
                    compte.getNumeroCompte(),
                    compte.getSolde(),
                    compte.getDevis(),
                    compte.getActif());
        }

        // Créer et retourner le DTO du profil utilisateur
        return new UtilisateurProfileDto(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getAdresse(),
                compteDto,
                utilisateur.getRole());
    }
}
