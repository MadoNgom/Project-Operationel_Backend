package com.flux.transactions.controllers;

import com.flux.transactions.dtos.UtilisateurDto;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.services.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
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

    @GetMapping("/{id}")
    public UtilisateurDto getById(@PathVariable Long id) {
        Utilisateur u = utilisateurService.getUtilisateurById(id);
        return u != null ? entityToDto(u) : null;
    }

    @GetMapping
    public List<UtilisateurDto> getAll() {
        return utilisateurService.getAllUtilisateurs()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
    }
}
