package com.flux.transactions.dtos;

import lombok.Data;

@Data
public class CompteDto {
    private Long id;
    private String numeroCompte;
    private Double solde;
    private Long utilisateurId;
    private String devise;
    private boolean actif;
}
