package com.flux.transactions.services;

import com.flux.transactions.entities.Historique;

import java.util.List;

public interface HistoriqueService {
    Historique createHistorique(Historique historique);
    Historique getHistoriqueById(Long id);
    List<Historique> getAllHistoriques();
    void deleteHistorique(Long id);
}

