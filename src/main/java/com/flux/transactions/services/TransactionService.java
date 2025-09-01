package com.flux.transactions.services;

import com.flux.transactions.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByUserId(Long userId);
    // List<Transaction> getAllTransactionsByCompteId(Long compteId);
    void deleteTransaction(Long id);
}
