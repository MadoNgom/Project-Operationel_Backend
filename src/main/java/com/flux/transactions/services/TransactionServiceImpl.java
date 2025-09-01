package com.flux.transactions.services;

import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Transaction;
import com.flux.transactions.enums.TypeTransaction;
import com.flux.transactions.exceptions.AccountInactiveException;
import com.flux.transactions.exceptions.InsufficientFundsException;
import com.flux.transactions.exceptions.ResourceNotFoundException;
import com.flux.transactions.repositories.CompteRepository;
import com.flux.transactions.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CompteRepository compteRepository;

    /**
     * ID du compte "admin" depuis lequel on fait les dépôts/retraits.
     * Peut être surchargé depuis application.properties avec : transaction.admin.compte.id=1000
     */
    private final Long adminCompteId;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CompteRepository compteRepository,
                                  @Value("${transaction.admin.compte.id:1000}") Long adminCompteId) {
        this.transactionRepository = transactionRepository;
        this.compteRepository = compteRepository;
        this.adminCompteId = adminCompteId;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setDateTransaction(LocalDateTime.now());

        TypeTransaction type = transaction.getTypeTransaction();
        if (type == null) {
            throw new IllegalArgumentException("Le type de transaction est obligatoire.");
        }

        switch (type) {
            case DEPOT -> processDepot(transaction);
            case RETRAIT -> processRetrait(transaction);
            case TRANSFERT -> processTransfert(transaction);
            default -> throw new IllegalArgumentException("Type de transaction non supporté : " + type);
        }

        return transactionRepository.save(transaction);
    }

    private void processDepot(Transaction transaction) {
        Compte admin = fetchCompte(adminCompteId, "admin");
        Compte destinataire = fetchCompteFromTransaction(transaction.getDestinataire(), "destinataire");

        ensureCompteActif(destinataire, "destinataire");
        ensureSufficientFunds(admin, transaction.getMontant(), "admin");

        debit(admin, transaction.getMontant());
        credit(destinataire, transaction.getMontant());

        compteRepository.save(admin);
        compteRepository.save(destinataire);
    }

    private void processRetrait(Transaction transaction) {
        Compte admin = fetchCompte(adminCompteId, "admin");
        Compte expediteur = fetchCompteFromTransaction(transaction.getExpediteur(), "expediteur");

        ensureCompteActif(expediteur, "expediteur");
        ensureSufficientFunds(expediteur, transaction.getMontant(), "expediteur");

        debit(expediteur, transaction.getMontant());
        credit(admin, transaction.getMontant());

        compteRepository.save(expediteur);
        compteRepository.save(admin);
    }

    private void processTransfert(Transaction transaction) {
        Compte expediteur = fetchCompteFromTransaction(transaction.getExpediteur(), "expediteur");
        Compte destinataire = fetchCompteFromTransaction(transaction.getDestinataire(), "destinataire");

        ensureCompteActif(expediteur, "expediteur");
        ensureCompteActif(destinataire, "destinataire");
        ensureSufficientFunds(expediteur, transaction.getMontant(), "expediteur");

        debit(expediteur, transaction.getMontant());
        credit(destinataire, transaction.getMontant());

        compteRepository.save(expediteur);
        compteRepository.save(destinataire);
    }

    private Compte fetchCompte(Long id, String role) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte " + role + " introuvable (ID: " + id + ")"));
    }

    private Compte fetchCompteFromTransaction(Compte compteRef, String role) {
        if (compteRef == null || compteRef.getId() == null) {
            throw new IllegalArgumentException("ID du compte " + role + " est requis.");
        }
        return fetchCompte(compteRef.getId(), role);
    }

    private void ensureCompteActif(Compte compte, String role) {
        if (Boolean.FALSE.equals(compte.getActif())) {
            throw new AccountInactiveException("Compte " + role + " désactivé (ID: " + compte.getId() + ").");
        }
    }

    private void ensureSufficientFunds(Compte compte, Double montant, String role) {
        if (montant == null || montant <= 0) {
            throw new IllegalArgumentException("Montant invalide pour la transaction.");
        }
        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant sur le compte " + role + " (ID: " + compte.getId() + ").");
        }
    }

    private void debit(Compte compte, Double montant) {
        compte.setSolde(compte.getSolde() - montant);
    }

    private void credit(Compte compte, Double montant) {
        compte.setSolde(compte.getSolde() + montant);
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

    @Override
    public List<Transaction> getAllTransactionsByUserId(Long userId) {
        return transactionRepository.findByExpediteur_Utilisateur_IdOrDestinataire_Utilisateur_Id(userId, userId);
    }

    // @Override
    // public List<Transaction> getAllTransactionsByCompteId(Long compteId) {
    //     return transactionRepository.findByCompteId(compteId);
    // }
}

