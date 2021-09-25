package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.data.api.*
import com.example.myvoozkotlin.groupOfUser.api.GroupOfUserApi
import com.example.myvoozkotlin.home.api.NewsApi
import com.example.myvoozkotlin.home.api.ScheduleApi
import com.example.myvoozkotlin.notification.data.api.NotificationApi
import com.example.myvoozkotlin.user.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ApiModule {

    @Provides
    fun provideNewsApi(retrofit: Retrofit) : NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideScheduleApi(retrofit: Retrofit) : ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideNoteApi(retrofit: Retrofit) : NoteApi {
        return retrofit.create(NoteApi::class.java)
    }

    @Provides
    fun provideGroupOfUserApi(retrofit: Retrofit) : GroupOfUserApi {
        return retrofit.create(GroupOfUserApi::class.java)
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideNotificationApi(retrofit: Retrofit) : NotificationApi {
        return retrofit.create(NotificationApi::class.java)
    }
}