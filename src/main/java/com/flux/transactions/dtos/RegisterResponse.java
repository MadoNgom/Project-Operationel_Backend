package com.flux.transactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private Long userId;
    private String email;
    private String nom;
    private String prenom;
    private String numeroCompte;
    private Double soldeInitial;
    private String devis;
}
