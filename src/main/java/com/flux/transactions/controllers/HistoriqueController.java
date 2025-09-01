package com.flux.transactions.controllers;

import com.flux.transactions.entities.Historique;
import com.flux.transactions.services.HistoriqueService;
import com.flux.transactions.dtos.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
@Tag(name = "Historiques", description = "Gestion des historiques (nécessite authentification JWT)")
@SecurityRequirement(name = "bearerAuth")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    public HistoriqueController(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @PostMapping
    public ApiResponse<Historique> create(@RequestBody Historique historique) {
        Historique created = historiqueService.createHistorique(historique);
        return ApiResponse.success(created, "Historique créé avec succès");
    }

    @GetMapping("/{id}")
    public ApiResponse<Historique> getById(@PathVariable Long id) {
        Historique historique = historiqueService.getHistoriqueById(id);
        if (historique != null) {
            return ApiResponse.success(historique, "Historique récupéré avec succès");
        } else {
            return ApiResponse.error("Historique non trouvé");
        }
    }

    @GetMapping
    public ApiResponse<List<Historique>> getAll() {
        List<Historique> historiques = historiqueService.getAllHistoriques();
        return ApiResponse.success(historiques, "Historiques récupérés avec succès");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        historiqueService.deleteHistorique(id);
        return ApiResponse.success("Historique supprimé avec succès");
    }
}

