package com.flux.transactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean isSuccess;
    private String message;
    private long timestamp;
    private String status;

    // Constructeur pour succès
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, true, message, System.currentTimeMillis(), "SUCCESS");
    }

    // Constructeur pour succès sans message
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true, "Opération réussie", System.currentTimeMillis(), "SUCCESS");
    }

    // Constructeur pour erreur
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, false, message, System.currentTimeMillis(), "ERROR");
    }

    // Constructeur pour erreur avec données
    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(data, false, message, System.currentTimeMillis(), "ERROR");
    }
}
