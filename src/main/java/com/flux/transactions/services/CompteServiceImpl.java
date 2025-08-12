package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.repositories.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    @Override
    public Compte createCompteForUtilisateur(Utilisateur utilisateur) {
        // Générer un numéro de compte unique à 4 chiffres
        String numeroCompte = generateUniqueNumeroCompte();

        // Créer le compte
        Compte compte = new Compte();
        compte.setNumeroCompte(numeroCompte);
        compte.setSolde(0.0);
        compte.setDevis("CFA");
        compte.setActif(true);
        compte.setUtilisateur(utilisateur);

        // Lier l'utilisateur au compte
        utilisateur.setCompte(compte);

        return compte;
    }

    @Override
    public Compte getCompteByNumero(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte).orElse(null);
    }

    @Override
    public Compte getCompteByUtilisateurId(Long utilisateurId) {
        return compteRepository.findByUtilisateurId(utilisateurId).orElse(null);
    }

    @Override
    public void updateSolde(Long compteId, Double nouveauSolde) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (nouveauSolde < 0) {
            throw new RuntimeException("Le solde ne peut pas être négatif");
        }

        compte.setSolde(nouveauSolde);
        compteRepository.save(compte);
    }

    @Override
    public boolean isCompteActif(Long compteId) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return compte.getActif();
    }

    /**
     * Génère un numéro de compte unique à 4 chiffres
     */
    private String generateUniqueNumeroCompte() {
        Random random = new Random();
        int tentatives = 0;
        final int MAX_TENTATIVES = 100;

        do {
            String numeroCompte = String.format("%04d", random.nextInt(10000));

            // Vérifier que le numéro n'existe pas déjà
            if (getCompteByNumero(numeroCompte) == null) {
                return numeroCompte;
            }

            tentatives++;
        } while (tentatives < MAX_TENTATIVES);

        throw new RuntimeException("Impossible de générer un numéro de compte unique après " + MAX_TENTATIVES + " tentatives");
    }
}
