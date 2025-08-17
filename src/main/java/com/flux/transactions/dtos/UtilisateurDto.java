package com.flux.transactions.dtos;

import lombok.Data;

@Data
public class UtilisateurDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
}
