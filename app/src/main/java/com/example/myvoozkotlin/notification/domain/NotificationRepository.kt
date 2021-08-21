package com.example.myvoozkotlin.notification.domain

import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.helpers.Event
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun loadNotificationUser(accessToken: String, isUser: Int, type: Int): Flow<Event<List<Notification>>>
    fun loadUserWithUniversityNotificationUser(accessToken: String, isUser: Int, type: Int, idUniversity: Int): Flow<Event<List<Notification>>>
    fun loadUniversityNotification(idUniversity: Int): Flow<Event<List<Notification>>>
}