package com.flux.transactions.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flux.transactions.enums.TypeTransaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private Double montant;
    private TypeTransaction typeTransaction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTransaction;
    private Long userId;
    private UserDto user; // Informations de l'autre utilisateur impliqué
}
