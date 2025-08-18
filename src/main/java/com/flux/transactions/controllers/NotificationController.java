package com.flux.transactions.controllers;

import com.flux.transactions.entities.Notification;
import com.flux.transactions.services.NotificationService;

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
    public Notification create(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAllNotifications();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}
