package com.flux.transactions.services;

import com.flux.transactions.entities.Utilisateur;
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
        // Vérifier si le téléphone existe déjà
        // if (utilisateur.getTelephone() != null) {
        //     Utilisateur existant = utilisateurRepository.findByTelephone(utilisateur.getTelephone());
        //     if (existant != null) {
        //         throw new DuplicateResourceException(
        //                 "Un utilisateur avec le numéro de téléphone '" + utilisateur.getTelephone() + "' existe déjà."
        //         );
        //     }
        // }

        // // Générer un numéro de compte à 4 chiffres
        // String numeroCompte = String.format("%04d", new Random().nextInt(10000));

        // // Créer et lier le compte automatiquement
        // Compte compte = new Compte();
        // compte.setNumeroCompte(numeroCompte);
        // compte.setSolde(0.0);
        // compte.setUtilisateur(utilisateur);

        // utilisateur.setCompte(compte);

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
}
