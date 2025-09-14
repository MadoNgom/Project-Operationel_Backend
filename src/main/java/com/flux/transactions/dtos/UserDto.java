package com.flux.transactions.dtos;

import lombok.Data;

import com.flux.transactions.enums.TypeRole;

@Data
public class UserDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private TypeRole role;
}
