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

    public ApiResponse(T data, boolean isSuccess, String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Méthodes statiques pour faciliter la création
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, true, message);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true, "Opération réussie");
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, false, message);
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(data, false, message);
    }
}
