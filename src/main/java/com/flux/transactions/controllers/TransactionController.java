package com.flux.transactions.controllers;

import com.flux.transactions.dtos.TransactionDto;
import com.flux.transactions.entities.Transaction;
import com.flux.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 🔁 Convertir une entité Transaction en DTO
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setMontant(transaction.getMontant());
        dto.setDateTransaction(transaction.getDateTransaction());
        dto.setTypeTransaction(transaction.getTypeTransaction());

        if (transaction.getExpediteur() != null)
            dto.setExpediteurId(transaction.getExpediteur().getId());

        if (transaction.getDestinataire() != null)
            dto.setDestinataireId(transaction.getDestinataire().getId());

        return dto;
    }

    // 🔁 Convertir un DTO en entité Transaction (pour POST)
    private Transaction convertToEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setMontant(dto.getMontant());
        transaction.setTypeTransaction(dto.getTypeTransaction());

        if (dto.getExpediteurId() != null) {
            transaction.setExpediteur(new com.flux.transactions.entities.Compte());
            transaction.getExpediteur().setId(dto.getExpediteurId());
        }

        if (dto.getDestinataireId() != null) {
            transaction.setDestinataire(new com.flux.transactions.entities.Compte());
            transaction.getDestinataire().setId(dto.getDestinataireId());
        }

        return transaction;
    }

    // ✅ POST : Créer une transaction
    @PostMapping
    public TransactionDto createTransaction(@RequestBody TransactionDto dto) {
        Transaction transaction = convertToEntity(dto);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return convertToDto(savedTransaction);
    }

    // ✅ GET : Récupérer toutes les transactions
    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ✅ GET : Récupérer une transaction par ID
    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return transaction != null ? convertToDto(transaction) : null;
    }

    // ✅ DELETE : Supprimer une transaction
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
