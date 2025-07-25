package com.flux.transactions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for user registration")
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    @Schema(description = "First name of the user", example = "Jane")
    private String nom;

    @NotBlank(message = "Last name is required")
    @Schema(description = "Last name of the user", example = "Doe")
    private String prenom;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Schema(description = "Email of the user (must be unique)", example = "jane.doe@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Password for the user (will be hashed)", example = "myStrongP@ssw0rd")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Schema(description = "Phone number of the user (must be unique)", example = "221779876543")
    private String telephone;
}