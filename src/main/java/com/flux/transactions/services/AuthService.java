package com.flux.transactions.services;

import com.flux.transactions.dtos.AuthRequest;
import com.flux.transactions.dtos.AuthResponse;
import com.flux.transactions.dtos.RegisterRequest;
import com.flux.transactions.entities.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UtilisateurService utilisateurService;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

//        public AuthResponse register(RegisterRequest request) {
//                // Vérifier si l'email existe déjà
//                if (utilisateurService.existsByEmail(request.getEmail())) {
//                        throw new RuntimeException("Un utilisateur avec cet email existe déjà");
//                }
//
//                // Créer le nouvel utilisateur
//                Utilisateur utilisateur = Utilisateur.builder()
//                                .nom(request.getNom())
//                                .prenom(request.getPrenom())
//                                .email(request.getEmail())
//                                .telephone(request.getTelephone())
//                                .adresse(request.getAdresse())
//                                .password(passwordEncoder.encode(request.getPassword()))
//                                .build();
//
//                Utilisateur savedUtilisateur = utilisateurService.createUtilisateur(utilisateur);
//
//                // Générer le token JWT
//                String token = jwtService.generateToken(
//                                org.springframework.security.core.userdetails.User.builder()
//                                                .username(savedUtilisateur.getEmail())
//                                                .password(savedUtilisateur.getPassword())
//                                                .authorities("USER")
//                                                .build());
//
//                // Retourner juste le token
//                return new AuthResponse(token, "Bearer");
//        }

        public AuthResponse authenticate(AuthRequest request) {
                // Authentifier l'utilisateur
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                // Récupérer l'utilisateur
                Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(request.getEmail());

                // Générer le token
                String token = jwtService.generateToken(
                                org.springframework.security.core.userdetails.User.builder()
                                                .username(utilisateur.getEmail())
                                                .password(utilisateur.getPassword())
                                                .authorities("USER")
                                                .build());

                return new AuthResponse(
                                token,
                                "Bearer");
        }
}
