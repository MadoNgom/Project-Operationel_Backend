package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.repositories.CompteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte createCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public Compte getCompteById(Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @Override
    public void deleteCompte(Long id) {
        compteRepository.deleteById(id);
    }
}