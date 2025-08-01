package com.flux.transactions.services;

import com.flux.transactions.entities.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    Notification getNotificationById(Long id);
    List<Notification> getAllNotifications();
    void deleteNotification(Long id);
}
