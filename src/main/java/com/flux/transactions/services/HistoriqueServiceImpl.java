package com.flux.transactions.services;

import com.flux.transactions.entities.Historique;
import com.flux.transactions.repositories.HistoriqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueServiceImpl implements HistoriqueService {

    private final HistoriqueRepository historiqueRepository;

    public HistoriqueServiceImpl(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Override
    public Historique createHistorique(Historique historique) {
        return historiqueRepository.save(historique);
    }

    @Override
    public Historique getHistoriqueById(Long id) {
        return historiqueRepository.findById(id).orElse(null);
    }

    @Override
    public List<Historique> getAllHistoriques() {
        return historiqueRepository.findAll();
    }

    @Override
    public void deleteHistorique(Long id) {
        historiqueRepository.deleteById(id);
    }
}

