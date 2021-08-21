package com.example.myvoozkotlin.notification.data

import com.example.homelibrary.model.Notification
import com.example.myvoozkotlin.helpers.Event
import com.example.myvoozkotlin.notification.domain.NotificationListUseCase
import com.example.myvoozkotlin.notification.domain.NotificationRepository
import com.example.myvoozkotlin.notification.domain.UserWithUniversityNotificationListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserWithUniversityNotificationListUseCaseImpl @Inject constructor(
    private val notificationRepository: NotificationRepository
) : UserWithUniversityNotificationListUseCase {
    override fun invoke(
        accessToken: String,
        idUser: Int,
        type: Int,
        idUniversity: Int
    ): Flow<Event<List<Notification>>> = notificationRepository.loadUserWithUniversityNotificationUser(accessToken, idUser, type, idUniversity)
}