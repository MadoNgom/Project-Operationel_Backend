package com.flux.transactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDto {
    private Long id;
    private String numeroCompte;
    private Double solde;
    private String devis;
    private Boolean actif;
}
