package com.flux.transactions.controllers;

import com.flux.transactions.entities.Historique;
import com.flux.transactions.services.HistoriqueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    public HistoriqueController(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @PostMapping
    public Historique create(@RequestBody Historique historique) {
        return historiqueService.createHistorique(historique);
    }

    @GetMapping("/{id}")
    public Historique getById(@PathVariable Long id) {
        return historiqueService.getHistoriqueById(id);
    }

    @GetMapping
    public List<Historique> getAll() {
        return historiqueService.getAllHistoriques();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        historiqueService.deleteHistorique(id);
    }
}

