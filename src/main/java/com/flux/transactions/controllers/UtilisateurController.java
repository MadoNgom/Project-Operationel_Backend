package com.flux.transactions.controllers;

import com.flux.transactions.dtos.UtilisateurDto;
import com.flux.transactions.dtos.ApiResponse;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs (nécessite authentification JWT)")
@SecurityRequirement(name = "bearerAuth")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    private Utilisateur dtoToEntity(UtilisateurDto dto) {
        Utilisateur u = new Utilisateur();
        u.setId(dto.getId());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setEmail(dto.getEmail());
        u.setTelephone(dto.getTelephone());
        u.setAdresse(dto.getAdresse());
        return u;
    }

    private UtilisateurDto entityToDto(Utilisateur u) {
        UtilisateurDto dto = new UtilisateurDto();
        dto.setId(u.getId());
        dto.setNom(u.getNom());
        dto.setPrenom(u.getPrenom());
        dto.setEmail(u.getEmail());
        dto.setTelephone(u.getTelephone());
        dto.setAdresse(u.getAdresse());
        return dto;
    }

    @PostMapping
    public UtilisateurDto create(@RequestBody UtilisateurDto dto) {
        Utilisateur utilisateur = dtoToEntity(dto);
        Utilisateur créé = utilisateurService.createUtilisateur(utilisateur);
        return entityToDto(créé);
    }

    // @GetMapping("/{id}")
    // public UtilisateurDto getById(@PathVariable Long id) {
    //     Utilisateur u = utilisateurService.getUtilisateurById(id);
    //     return u != null ? entityToDto(u) : null;
    // }

    // @GetMapping
    // public List<UtilisateurDto> getAll() {
    //     return utilisateurService.getAllUtilisateurs()
    //             .stream()
    //             .map(this::entityToDto)
    //             .collect(Collectors.toList());
    // public ResponseEntity<ApiResponse<Utilisateur>> create(@RequestBody Utilisateur utilisateur) {
    //     try {
    //         Utilisateur created = utilisateurService.createUtilisateur(utilisateur);
    //         return ResponseEntity.ok(ApiResponse.success(created, "Utilisateur créé avec succès"));
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    //     }
    // }

    @GetMapping("/me")
    @Operation(summary = "Récupérer le profil de l'utilisateur connecté", description = "Retourne les informations de l'utilisateur authentifié")
    public ResponseEntity<ApiResponse<Utilisateur>> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(email);
            if (utilisateur != null) {
                return ResponseEntity.ok(ApiResponse.success(utilisateur, "Profil récupéré avec succès"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Utilisateur>> getById(@PathVariable Long id) {
        try {
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur != null) {
                return ResponseEntity.ok(ApiResponse.success(utilisateur, "Utilisateur récupéré avec succès"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Utilisateur>>> getAll() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            return ResponseEntity.ok(ApiResponse.success(utilisateurs, "Liste des utilisateurs récupérée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Utilisateur supprimé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
