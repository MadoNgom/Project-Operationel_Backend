package com.flux.transactions.controllers;

import com.flux.transactions.dtos.ApiResponse;
import com.flux.transactions.entities.Compte;
import com.flux.transactions.services.CompteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@Tag(name = "Comptes", description = "Gestion des comptes bancaires (nécessite authentification JWT)")
@SecurityRequirement(name = "bearerAuth")
public class CompteController {

    private final CompteService compteService;

    @GetMapping("/me")
    @Operation(summary = "Récupérer le compte de l'utilisateur connecté", description = "Retourne les informations du compte de l'utilisateur authentifié")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMyCompte() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            // Ici vous devriez récupérer l'utilisateur par email puis son compte
            // Pour simplifier, on retourne un message d'information
            Map<String, Object> data = new HashMap<>();
            data.put("user", email);
            data.put("info", "Fonctionnalité à implémenter : récupération du compte par email utilisateur");

            return ResponseEntity.ok(ApiResponse.success(data, "Informations utilisateur récupérées"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{numeroCompte}")
    @Operation(summary = "Récupérer un compte par numéro", description = "Retourne les informations d'un compte spécifique")
    public ResponseEntity<ApiResponse<Compte>> getCompteByNumero(@PathVariable String numeroCompte) {
        try {
            Compte compte = compteService.getCompteByNumero(numeroCompte);
            if (compte != null) {
                return ResponseEntity.ok(ApiResponse.success(compte, "Compte récupéré avec succès"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    @Operation(summary = "Récupérer le compte d'un utilisateur", description = "Retourne le compte d'un utilisateur spécifique")
    public ResponseEntity<ApiResponse<Compte>> getCompteByUtilisateurId(@PathVariable Long utilisateurId) {
        try {
            Compte compte = compteService.getCompteByUtilisateurId(utilisateurId);
            if (compte != null) {
                return ResponseEntity.ok(ApiResponse.success(compte, "Compte utilisateur récupéré avec succès"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{compteId}/solde")
    @Operation(summary = "Mettre à jour le solde d'un compte", description = "Met à jour le solde d'un compte spécifique")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateSolde(
            @PathVariable Long compteId,
            @RequestParam Double nouveauSolde) {
        try {
            compteService.updateSolde(compteId, nouveauSolde);

            Map<String, Object> data = new HashMap<>();
            data.put("compteId", compteId);
            data.put("nouveauSolde", nouveauSolde);

            return ResponseEntity.ok(ApiResponse.success(data, "Solde mis à jour avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{compteId}/status")
    @Operation(summary = "Vérifier le statut d'un compte", description = "Vérifie si un compte est actif")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCompteStatus(@PathVariable Long compteId) {
        try {
            boolean isActif = compteService.isCompteActif(compteId);

            Map<String, Object> data = new HashMap<>();
            data.put("compteId", compteId);
            data.put("actif", isActif);

            return ResponseEntity.ok(ApiResponse.success(data, isActif ? "Compte actif" : "Compte inactif"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}