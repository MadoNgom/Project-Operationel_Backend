package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.exceptions.DuplicateResourceException;
import com.flux.transactions.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        // Vérifier si le téléphone existe déjà
        if (utilisateur.getTelephone() != null) {
            Utilisateur existant = utilisateurRepository.findByTelephone(utilisateur.getTelephone());
            if (existant != null) {
                throw new DuplicateResourceException(
                        "Un utilisateur avec le numéro de téléphone '" + utilisateur.getTelephone() + "' existe déjà."
                );
            }
        }

        // Générer un numéro de compte à 4 chiffres
        String numeroCompte = String.format("%04d", new Random().nextInt(10000));

        // Créer et lier le compte automatiquement
        Compte compte = new Compte();
        compte.setNumeroCompte(numeroCompte);
        compte.setSolde(0.0);
        compte.setUtilisateur(utilisateur);

        utilisateur.setCompte(compte);

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
}
