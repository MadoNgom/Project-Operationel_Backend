package com.flux.transactions.controllers;

import com.flux.transactions.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Endpoints de test pour vérifier l'authentification")
public class TestController {

    @GetMapping("/public")
    @Operation(summary = "Endpoint public", description = "Accessible sans authentification")
    public ResponseEntity<ApiResponse<Map<String, String>>> publicEndpoint() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Cet endpoint est public - accessible sans authentification");
        data.put("status", "public");

        return ResponseEntity.ok(ApiResponse.success(data, "Test public réussi"));
    }

    @GetMapping("/protected")
    @Operation(summary = "Endpoint protégé", description = "Accessible uniquement avec un token JWT valide")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Map<String, Object>>> protectedEndpoint() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Map<String, Object> data = new HashMap<>();
            data.put("message", "Cet endpoint est protégé - accessible uniquement avec un token JWT valide");
            data.put("user", authentication.getName());
            data.put("authorities", authentication.getAuthorities());
            data.put("status", "protected");

            return ResponseEntity.ok(ApiResponse.success(data, "Test protégé réussi"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
