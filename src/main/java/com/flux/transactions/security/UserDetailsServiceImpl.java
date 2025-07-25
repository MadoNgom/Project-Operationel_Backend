package com.flux.transactions.security;

import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.repositories.UtilisateurRepository;
import org.springframework.security.core.userdetails.User; // Importez la classe User de Spring Security
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Pour les autorités (simplement vide si pas de rôles)

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // C'est ici que nous créons un objet UserDetails de Spring Security
        // en utilisant les informations de notre entité Utilisateur.
        // Pour la simplicité et sans gestion de rôles, nous passons une liste vide d'autorités.
        return new User(
                utilisateur.getEmail(),      // Le nom d'utilisateur (ici l'email)
                utilisateur.getPassword(),   // Le mot de passe haché
                new ArrayList<>()            // Une liste vide d'autorités (pas de rôles spécifiques)
        );
    }
}