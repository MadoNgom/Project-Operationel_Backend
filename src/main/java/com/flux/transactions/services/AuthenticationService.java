package com.flux.transactions.services;

import com.flux.transactions.dtos.AuthenticationRequest;
import com.flux.transactions.dtos.AuthenticationResponse;
import com.flux.transactions.dtos.RegisterRequest;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.repositories.UtilisateurRepository;
import com.flux.transactions.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails; // Importez UserDetails
import org.springframework.security.core.userdetails.UserDetailsService; // Importez UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService; // Injectez UserDetailsService

    public AuthenticationService(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService // Ajoutez à la construction
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService; // Initialisez
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
        if (utilisateurRepository.existsByTelephone(request.getTelephone())) {
            throw new IllegalArgumentException("Phone number already registered.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setTelephone(request.getTelephone());
        // createdAt and updatedAt are set by @PrePersist/@PreUpdate in Utilisateur entity

        utilisateurRepository.save(utilisateur);

        // Après avoir enregistré l'utilisateur, chargez-le en tant que UserDetails
        // pour générer le token. userDetailsService.loadUserByUsername va créer l'objet UserDetails.
        UserDetails userDetails = userDetailsService.loadUserByUsername(utilisateur.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails); // Passez le UserDetails
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // Si l'authentification est réussie, chargez les détails de l'utilisateur
        // et générez le token.
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String jwtToken = jwtUtil.generateToken(userDetails); // Passez le UserDetails
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}