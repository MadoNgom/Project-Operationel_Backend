package com.flux.transactions.controllers;

import com.flux.transactions.dtos.CompteDto;
import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.repositories.UtilisateurRepository;
import com.flux.transactions.services.CompteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;
    private final UtilisateurRepository utilisateurRepository;

    public CompteController(CompteService compteService, UtilisateurRepository utilisateurRepository) {
        this.compteService = compteService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping
    public CompteDto createCompte(@RequestBody CompteDto dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Compte compte = new Compte();
        compte.setNumeroCompte(dto.getNumeroCompte());
        compte.setSolde(dto.getSolde());
        compte.setDevis(dto.getDevise());
        compte.setActif(dto.isActif());
        compte.setUtilisateur(utilisateur);

        Compte saved = compteService.createCompte(compte);
        return convertToDto(saved);
    }

    @GetMapping
    public List<CompteDto> getAllComptes() {
        return compteService.getAllComptes()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompteDto getCompte(@PathVariable Long id) {
        Compte compte = compteService.getCompteById(id);
        return convertToDto(compte);
    }

    // Méthode utilitaire pour convertir un Compte en CompteDto
    private CompteDto convertToDto(Compte compte) {
        CompteDto dto = new CompteDto();
        dto.setId(compte.getId());
        dto.setNumeroCompte(compte.getNumeroCompte());
        dto.setSolde(compte.getSolde());
        dto.setDevise(compte.getDevis());
        dto.setActif(compte.getActif());
        dto.setUtilisateurId(compte.getUtilisateur() != null ? compte.getUtilisateur().getId() : null);
        return dto;
    }
}