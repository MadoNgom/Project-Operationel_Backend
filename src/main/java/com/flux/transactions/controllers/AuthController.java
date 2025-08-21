package com.flux.transactions.controllers;

import com.flux.transactions.dtos.ApiResponse;
import com.flux.transactions.dtos.AuthRequest;
import com.flux.transactions.dtos.AuthResponse;
import com.flux.transactions.dtos.RegisterRequest;
import com.flux.transactions.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Endpoints pour l'inscription et la connexion")
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/register")
//    @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un nouveau compte utilisateur avec un compte bancaire et retourne un token JWT")
//    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
//        try {
//            AuthResponse response = authService.register(request);
//            return ResponseEntity.ok(ApiResponse.success(response, "Inscription réussie ! Votre compte a été créé avec succès."));
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
//        }
//    }

    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(ApiResponse.success(response, "Connexion réussie"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Email ou mot de passe incorrect"));
        }
    }
}
