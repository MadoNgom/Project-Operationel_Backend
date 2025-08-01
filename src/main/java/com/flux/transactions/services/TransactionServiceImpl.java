package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Transaction;
import com.flux.transactions.enums.TypeTransaction;
import com.flux.transactions.repositories.CompteRepository;
import com.flux.transactions.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Long ADMIN_COMPTE_ID = 1000L;

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CompteRepository compteRepository) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setDateTransaction(LocalDateTime.now());

        TypeTransaction type = transaction.getTypeTransaction();
        if (type == null) {
            throw new IllegalArgumentException("Le type de transaction est obligatoire.");
        }

        switch (type) {
            case DEPOT -> handleDepot(transaction);
            case RETRAIT -> handleRetrait(transaction);
            case TRANSFERT -> handleTransfert(transaction);
        }

        return transactionRepository.save(transaction);
    }

    private void handleDepot(Transaction transaction) {
        Compte admin = getCompteById(ADMIN_COMPTE_ID, "admin");
        Compte destinataire = getCompteById(transaction.getDestinataire().getId(), "destinataire");

        if (!destinataire.getActif()) throw new RuntimeException("Compte destinataire désactivé.");

        if (admin.getSolde() < transaction.getMontant()) {
            throw new RuntimeException("Solde insuffisant dans le compte admin.");
        }

        admin.setSolde(admin.getSolde() - transaction.getMontant());
        destinataire.setSolde(destinataire.getSolde() + transaction.getMontant());

        compteRepository.save(admin);
        compteRepository.save(destinataire);
    }

    private void handleRetrait(Transaction transaction) {
        Compte admin = getCompteById(ADMIN_COMPTE_ID, "admin");
        Compte expediteur = getCompteById(transaction.getExpediteur().getId(), "expediteur");

        if (!expediteur.getActif()) throw new RuntimeException("Compte expediteur désactivé.");

        if (expediteur.getSolde() < transaction.getMontant()) {
            throw new RuntimeException("Solde insuffisant pour le retrait.");
        }

        expediteur.setSolde(expediteur.getSolde() - transaction.getMontant());
        admin.setSolde(admin.getSolde() + transaction.getMontant());

        compteRepository.save(expediteur);
        compteRepository.save(admin);
    }

    private void handleTransfert(Transaction transaction) {
        Compte expediteur = getCompteById(transaction.getExpediteur().getId(), "expediteur");
        Compte destinataire = getCompteById(transaction.getDestinataire().getId(), "destinataire");

        if (!expediteur.getActif() || !destinataire.getActif()) {
            throw new RuntimeException("Un des comptes est désactivé.");
        }

        if (expediteur.getSolde() < transaction.getMontant()) {
            throw new RuntimeException("Solde insuffisant pour le transfert.");
        }

        expediteur.setSolde(expediteur.getSolde() - transaction.getMontant());
        destinataire.setSolde(destinataire.getSolde() + transaction.getMontant());

        compteRepository.save(expediteur);
        compteRepository.save(destinataire);
    }

    private Compte getCompteById(Long id, String role) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte " + role + " introuvable (ID: " + id + ")"));
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}


