package com.flux.transactions.repositories;

import com.flux.transactions.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Trouver les transactions où l'utilisateur est soit l'expediteur soit le destinataire
    List<Transaction> findByExpediteur_Utilisateur_IdOrDestinataire_Utilisateur_Id(Long expediteurUserId, Long destinataireUserId);
}
