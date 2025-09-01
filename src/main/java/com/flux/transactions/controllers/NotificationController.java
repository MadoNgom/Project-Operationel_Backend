package com.flux.transactions.controllers;

import com.flux.transactions.entities.Notification;
import com.flux.transactions.services.NotificationService;
import com.flux.transactions.dtos.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Gestion des notifications (nécessite authentification JWT)")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ApiResponse<Notification> create(@RequestBody Notification notification) {
        Notification created = notificationService.createNotification(notification);
        return ApiResponse.success(created, "Notification créée avec succès");
    }

    @GetMapping("/{id}")
    public ApiResponse<Notification> getById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return ApiResponse.success(notification, "Notification récupérée avec succès");
        } else {
            return ApiResponse.error("Notification non trouvée");
        }
    }

    @GetMapping
    public ApiResponse<List<Notification>> getAll() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ApiResponse.success(notifications, "Notifications récupérées avec succès");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ApiResponse.success("Notification supprimée avec succès");
    }
}
