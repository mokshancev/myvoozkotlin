package com.example.myvoozkotlin.data.di

import com.example.myvoozkotlin.data.api.AuthApi
import com.example.myvoozkotlin.data.api.NewsApi
import com.example.myvoozkotlin.data.api.NoteApi
import com.example.myvoozkotlin.data.api.ScheduleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

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
}