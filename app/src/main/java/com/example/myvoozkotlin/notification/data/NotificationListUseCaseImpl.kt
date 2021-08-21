package com.example.myvoozkotlin.notification.data

import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.notification.domain.NotificationListUseCase
import com.example.myvoozkotlin.notification.domain.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationListUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : NotificationListUseCase {
    override fun invoke(accessToken: String, idUser: Int, type: Int): Flow<Event<List<Notification>>> = notificationRepository.loadNotificationUser(accessToken, idUser, type)
}