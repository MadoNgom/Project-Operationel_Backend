package com.flux.transactions.controllers;

import com.flux.transactions.dtos.TransactionDto;
import com.flux.transactions.dtos.ApiResponse;
import com.flux.transactions.dtos.UserDto;
import com.flux.transactions.entities.Compte;
import com.flux.transactions.entities.Transaction;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.services.TransactionService;
import com.flux.transactions.services.UtilisateurService;
import com.flux.transactions.services.CompteService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Gestion des transactions (nécessite authentification JWT)")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CompteService compteService;

        // 🔁 Convertir une entité Transaction en DTO
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setMontant(transaction.getMontant());
        dto.setDateTransaction(transaction.getDateTransaction());
        dto.setTypeTransaction(transaction.getTypeTransaction());

        // Déterminer quel userId afficher et récupérer les infos de l'utilisateur
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        Long currentUserId = utilisateurService.getUtilisateurByEmail(currentUserEmail).getId();

        if (transaction.getExpediteur() != null && transaction.getExpediteur().getUtilisateur() != null) {
            Long expediteurUserId = transaction.getExpediteur().getUtilisateur().getId();
            if (transaction.getDestinataire() != null && transaction.getDestinataire().getUtilisateur() != null) {
                Long destinataireUserId = transaction.getDestinataire().getUtilisateur().getId();

                // Si l'utilisateur connecté est l'expéditeur, afficher le destinataire
                if (currentUserId.equals(expediteurUserId)) {
                    dto.setUserId(destinataireUserId);
                    dto.setUser(convertToUserDto(transaction.getDestinataire().getUtilisateur()));
                } else {
                    // Si l'utilisateur connecté est le destinataire, afficher l'expéditeur
                    dto.setUserId(expediteurUserId);
                    dto.setUser(convertToUserDto(transaction.getExpediteur().getUtilisateur()));
                }
            } else {
                // Transaction avec seulement un expéditeur (dépôt/retrait)
                dto.setUserId(expediteurUserId);
                dto.setUser(convertToUserDto(transaction.getExpediteur().getUtilisateur()));
            }
        } else if (transaction.getDestinataire() != null && transaction.getDestinataire().getUtilisateur() != null) {
            dto.setUserId(transaction.getDestinataire().getUtilisateur().getId());
            dto.setUser(convertToUserDto(transaction.getDestinataire().getUtilisateur()));
        }

        return dto;
    }

    // 🔁 Convertir une entité Utilisateur en UserDto
    private UserDto convertToUserDto(Utilisateur utilisateur) {
        UserDto userDto = new UserDto();
        userDto.setId(utilisateur.getId());
        userDto.setNom(utilisateur.getNom());
        userDto.setPrenom(utilisateur.getPrenom());
        userDto.setEmail(utilisateur.getEmail());
        userDto.setTelephone(utilisateur.getTelephone());
        userDto.setAdresse(utilisateur.getAdresse());
        userDto.setRole(utilisateur.getRole());
        return userDto;
    }

    // 🔁 Convertir un DTO en entité Transaction (pour POST)
    private Transaction convertToEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setMontant(dto.getMontant());
        transaction.setTypeTransaction(dto.getTypeTransaction());

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        Long currentUserId = utilisateurService.getUtilisateurByEmail(currentUserEmail).getId();

        // Récupérer le compte de l'utilisateur connecté
        Compte currentUserCompte = compteService.getCompteByUtilisateurId(currentUserId);

        // Configurer expéditeur et destinataire selon le type de transaction
        switch (dto.getTypeTransaction()) {
            case DEPOT:
                // L'utilisateur connecté est le destinataire (il reçoit l'argent)
                transaction.setDestinataire(currentUserCompte);
                // L'expéditeur est le compte admin ou l'utilisateur spécifié
                if (dto.getUserId() != null) {
                    Compte expediteurCompte = compteService.getCompteByUtilisateurId(dto.getUserId());
                    transaction.setExpediteur(expediteurCompte);
                }
                break;

            case RETRAIT:
                // L'utilisateur connecté est l'expéditeur (il retire l'argent)
                transaction.setExpediteur(currentUserCompte);
                // Le destinataire est le compte admin ou l'utilisateur spécifié
                if (dto.getUserId() != null) {
                    Compte destinataireCompte = compteService.getCompteByUtilisateurId(dto.getUserId());
                    transaction.setDestinataire(destinataireCompte);
                }
                break;

            case TRANSFERT:
                // L'utilisateur connecté est l'expéditeur (il envoie l'argent)
                transaction.setExpediteur(currentUserCompte);
                // Le destinataire est l'utilisateur spécifié
                if (dto.getUserId() != null) {
                    Compte destinataireCompte = compteService.getCompteByUtilisateurId(dto.getUserId());
                    transaction.setDestinataire(destinataireCompte);
                }
                break;
        }

        return transaction;
    }

    // ✅ POST : Créer une transaction
    @PostMapping
    public ApiResponse<TransactionDto> createTransaction(@RequestBody TransactionDto dto) {
        Transaction transaction = convertToEntity(dto);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return ApiResponse.success(convertToDto(savedTransaction), "Transaction créée avec succès");
    }

    // ✅ GET : Récupérer toutes les transactions
    @GetMapping
    public ApiResponse<List<TransactionDto>> getAllTransactions() {
        List<TransactionDto> transactions = transactionService.getAllTransactions()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ApiResponse.success(transactions, "Transactions récupérées avec succès");
    }

    // ✅ GET : Récupérer toutes les transactions par utilisateur
    @GetMapping("/user")
    public ApiResponse<List<TransactionDto>> getAllTransactionsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = utilisateurService.getUtilisateurByEmail(email).getId();
        List<TransactionDto> transactions = transactionService.getAllTransactionsByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ApiResponse.success(transactions, "Transactions de l'utilisateur récupérées avec succès");
    }

    // ✅ GET : Récupérer une transaction par ID
    @GetMapping("/{id}")
    public ApiResponse<TransactionDto> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) {
            return ApiResponse.success(convertToDto(transaction), "Transaction récupérée avec succès");
        } else {
            return ApiResponse.error("Transaction non trouvée");
        }
    }

    // ✅ DELETE : Supprimer une transaction
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ApiResponse.success("Transaction supprimée avec succès");
    }
}
