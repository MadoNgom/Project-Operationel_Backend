package com.flux.transactions.controllers;

import com.flux.transactions.dtos.AuthenticationRequest;
import com.flux.transactions.dtos.AuthenticationResponse;
import com.flux.transactions.dtos.RegisterRequest;
import com.flux.transactions.entities.Utilisateur;
import com.flux.transactions.services.AuthenticationService;
import com.flux.transactions.repositories.UtilisateurRepository; // For demonstration, in real apps use a dedicated service layer for CRUD
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize; // For securing endpoints

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth") // Base path for authentication endpoints
@Tag(name = "Authentication & User Management", description = "API for user registration, login, and basic user operations")
public class UtilisateurController {

    private final AuthenticationService authenticationService;
    private final UtilisateurRepository utilisateurRepository; // Using repository directly for simplicity, ideally through a service

    public UtilisateurController(AuthenticationService authenticationService, UtilisateurRepository utilisateurRepository) {
        this.authenticationService = authenticationService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account with provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Or return a more specific error DTO
        }
    }

    @Operation(summary = "Authenticate user", description = "Logs in a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully, JWT token returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) { // Catch AuthenticationException or BadCredentialsException more specifically
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // --- Secured Endpoints (Example) ---

    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT token"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have necessary permissions")
    })
    @GetMapping("/users")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // Example: Only accessible by users with 'ADMIN' or 'USER' authority
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        List<Utilisateur> users = utilisateurRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Utilisateur.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid JWT token")
    })
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Integer id) {
        Optional<Utilisateur> user = utilisateurRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}