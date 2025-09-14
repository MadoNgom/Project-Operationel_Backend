package com.flux.transactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurMeResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String role;
    private CompteInfo compte;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompteInfo {
        private Long id;
        private String numeroCompte;
        private Double solde;
        private String devis;
        private Boolean actif;
    }
}
