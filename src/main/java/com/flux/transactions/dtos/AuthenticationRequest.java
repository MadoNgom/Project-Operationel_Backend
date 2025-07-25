package com.flux.transactions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for user login")
public class AuthenticationRequest {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password for the user", example = "aStrongHashedPassword123")
    private String password;
}