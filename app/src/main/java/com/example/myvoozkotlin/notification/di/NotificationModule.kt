package com.example.myvoozkotlin.notification.di

import com.example.myvoozkotlin.notification.data.api.NotificationApi
import com.example.myvoozkotlin.notification.data.NotificationListUseCaseImpl
import com.example.myvoozkotlin.notification.data.NotificationRepositoryImpl
import com.example.myvoozkotlin.notification.data.UniversityNotificationListUseCaseImpl
import com.example.myvoozkotlin.notification.data.UserWithUniversityNotificationListUseCaseImpl
import com.example.myvoozkotlin.notification.domain.NotificationListUseCase
import com.example.myvoozkotlin.notification.domain.NotificationRepository
import com.example.myvoozkotlin.notification.domain.UniversityNotificationListUseCase
import com.example.myvoozkotlin.notification.domain.UserWithUniversityNotificationListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NotificationModule {

    @Provides
    fun provideNotificationListUseCase(notificationRepository: NotificationRepository): NotificationListUseCase {
        return NotificationListUseCaseImpl(notificationRepository)
    }

    @Provides
    fun provideUserWithUniversityNotificationListUseCase(notificationRepository: NotificationRepository): UserWithUniversityNotificationListUseCase {
        return UserWithUniversityNotificationListUseCaseImpl(notificationRepository)
    }

    @Provides
    fun provideUniversityNotificationListUseCase(notificationRepository: NotificationRepository): UniversityNotificationListUseCase {
        return UniversityNotificationListUseCaseImpl(notificationRepository)
    }

    @Provides
    fun provideNotificationRepository(notificationApi: NotificationApi) : NotificationRepository {
        return NotificationRepositoryImpl(notificationApi)
    }
}