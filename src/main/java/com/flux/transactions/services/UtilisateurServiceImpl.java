package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;
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
        // Générer un numéro de compte à 4 chiffres
        String numeroCompte = String.format("%04d", new Random().nextInt(10000));

        // Créer et lier le compte automatiquement
        Compte compte = new Compte();
        compte.setNumeroCompte(numeroCompte);
        compte.setSolde(0.0);
        compte.setUtilisateur(utilisateur);

        // Lier aussi dans l'autre sens
        utilisateur.setCompte(compte);

        // Sauvegarder l'utilisateur (et le compte grâce à cascade)
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
